package com.revature.hydra.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.hydra.entities.Trainer;
import com.revature.hydra.entities.TrainerUser;
import com.revature.hydra.entities.User;
import com.revature.hydra.messaging.UserReceiver;
import com.revature.hydra.messaging.UserSender;
import com.revature.hydra.repo.TrainerRepository;
import com.revature.hydra.repo.UserRepository;
import com.revature.hydra.util.ClassUtil;

@RestController
@RequestMapping(value = "/trainer")
public class TrainerService {

	@Autowired
	AmqpTemplate rabbitTemplate;

	@Autowired
	public TrainerRepository trainerRepository;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSender us;

	@Autowired
	private UserReceiver ur;

	private static final Logger log = Logger.getLogger(TrainerService.class);

	/**
	 * Delete a single Trainer
	 *
	 * @param id
	 *
	 * @return
	 */
	public void delete(Integer id) {
		Trainer bt = trainerRepository.findByTrainerId(id);
		userService.delete(bt.getUserId());
	}

	/**
	 * Find a single Trainer by trainerId
	 *
	 * @param trainerId
	 *
	 * @return Trainer
	 */
	public TrainerUser findById(Integer trainerId) {
		log.info("Trainer Id: " + trainerId);
		Trainer bt = trainerRepository.findByTrainerId(trainerId);
		User u = userRepo.findByUserId(bt.getUserId());
		TrainerUser result = ClassUtil.merge(u, bt);
		return result;
	}

	/**
	 * 
	 * Creates a new User in the User database and a new Trainer in the trainer
	 * database associated with that User.
	 * 
	 * @param trainerUser
	 * @return TrainerUser
	 */
	public TrainerUser newTrainer(TrainerUser tu) {
		User u = new User();
		BeanUtils.copyProperties(tu, u);
		u.setRole(tu.getRole());
		log.info("Persisting user with the following credentials: " + u.toString());
		Trainer bt = new Trainer();
		bt.setTitle(tu.getTitle());
		log.info("Setting that user to be a trainer with title: " + bt.getTitle());
		User persisted = userRepo.save(u);
		bt.setUserId(persisted.getUserId());
		bt.setTrainerId(0);
		Trainer saved = trainerRepository.save(bt);
		TrainerUser result = ClassUtil.merge(persisted, saved);
		try {
			us.sendNewTrainer(result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * Creates a new trainer object to associate with a pre-existing User object
	 * 
	 * @param trainerUser
	 * @return TrainerUser
	 */
	public TrainerUser promoteToTrainer(TrainerUser tu) {
		User u = userRepo.findByUserId(tu.getUserId());
		Trainer bt = new Trainer();
		bt.setUserId(u.getUserId());
		bt.setTitle(tu.getTitle());
		bt.setTrainerId(0);
		return ClassUtil.merge(u, bt);
	}

	/**
	 * 
	 * Updates both the User and Trainer components of a trainer's credentials
	 * 
	 * @param TrainerUser
	 * @return TrainerUser
	 */
	public TrainerUser update(TrainerUser tu) {
		System.out.println(("The trainer id passed in is " + tu.getTrainerId()));
		Trainer bt = trainerRepository.findByTrainerId(tu.getTrainerId());
		User u = userService.findUserById((bt.getUserId()));
		BeanUtils.copyProperties(tu, u, "userId");
		User persisted = userRepo.save(u);
		bt.setTitle(tu.getTitle());
		Trainer ret = trainerRepository.save(bt);
		TrainerUser result = ClassUtil.merge(persisted, ret);
		try {
			us.sendUpdateTrainer(result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Find a single Trainer by email
	 *
	 * @param trainerId
	 *
	 * @return TrainerUser
	 */
	public TrainerUser findTrainerByEmail(String email) {
		User u = userRepo.findByEmail(email);
		Trainer bt = trainerRepository.findByUserId(u.getUserId());
		return ClassUtil.merge(u, bt);
	}

	public List<String> allTitles() {
		List<String> titles = trainerRepository.findTitles();
		return titles;
	}

	/**
	 * Implementation should be improved. This many individual DB calls could take a
	 * very long time to resolve.
	 * 
	 * @return
	 */
	public List<TrainerUser> getAll() {
		List<Trainer> allTrainers = trainerRepository.findAll();
		List<TrainerUser> result = new ArrayList<TrainerUser>();
		for (Trainer b : allTrainers) {
			result.add(ClassUtil.merge(userRepo.findByUserId(b.getUserId()), b));
		}
		return result;
	}

	public TrainerUser findByName(String firstName, String lastName) {
		User u = userService.findByName(firstName, lastName);
		Trainer bt = trainerRepository.findByUserId(u.getUserId());
		return ClassUtil.merge(u, bt);
	}

}