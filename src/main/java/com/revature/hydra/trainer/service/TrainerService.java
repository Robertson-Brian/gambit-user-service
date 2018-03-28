package com.revature.hydra.trainer.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entities.BatchTrainer;
import com.revature.entities.TrainerUser;
import com.revature.entities.User;
import com.revature.hydra.trainer.data.TrainerRepository;
import com.revature.hydra.trainer.data.UserRepository;
import com.revature.util.ClassUtil;

@RestController
@RequestMapping(value = "/trainer")
public class TrainerService {

	@Autowired
	AmqpTemplate rabbitTemplate;

	@Autowired
	public TrainerRepository trainerRepository;

	@Autowired
	private TrainerMessagingService trainerMessagingService;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserService userService;

	private static final Logger log = Logger.getLogger(TrainerService.class);

	/**
	 * Delete a single Trainer
	 *
	 * @param id
	 *
	 * @return
	 */
	public void delete(Integer id) {
		BatchTrainer bt = trainerRepository.findByTrainerId(id);
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
		BatchTrainer bt = trainerRepository.findByTrainerId(trainerId);
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
		BatchTrainer bt = new BatchTrainer();
		bt.setTitle(tu.getTitle());
		log.info("Setting that user to be a trainer with title: " + bt.getTitle());
		User persisted = userRepo.save(u);
		bt.setUserId(persisted.getUserId());
		bt.setTrainerId(0);
		BatchTrainer saved = trainerRepository.save(bt);
		TrainerUser result = ClassUtil.merge(persisted, saved);
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
		BatchTrainer bt = new BatchTrainer();
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
		BatchTrainer bt = trainerRepository.findByTrainerId(tu.getTrainerId());
		User u = userService.findUserById((bt.getUserId()));
		BeanUtils.copyProperties(tu, u, "userId");
		User persisted = userRepo.save(u);
		bt.setTitle(tu.getTitle());
		BatchTrainer ret = trainerRepository.save(bt);
		TrainerUser result = ClassUtil.merge(persisted, ret);
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
		BatchTrainer bt = trainerRepository.findByUserId(u.getUserId());
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
		List<BatchTrainer> allTrainers = trainerRepository.findAll();
		List<TrainerUser> result = new ArrayList<TrainerUser>();
		for (BatchTrainer b : allTrainers) {
			result.add(ClassUtil.merge(userRepo.findByUserId(b.getUserId()), b));
		}
		return result;
	}

	public TrainerUser findByName(String firstName, String lastName) {
		User u = userService.findByName(firstName, lastName);
		BatchTrainer bt = trainerRepository.findByUserId(u.getUserId());
		return ClassUtil.merge(u, bt);
	}

}