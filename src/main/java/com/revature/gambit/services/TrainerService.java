package com.revature.gambit.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.gambit.entities.Trainer;
import com.revature.gambit.entities.TrainerUser;
import com.revature.gambit.entities.User;
import com.revature.gambit.messaging.UserSender;
import com.revature.gambit.repo.TrainerRepository;
import com.revature.gambit.repo.UserRepository;
import com.revature.gambit.util.ClassUtil;

@Service
public class TrainerService {

	@Autowired
	public TrainerRepository trainerRepository;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private UserSender us;

	private static final Logger log = Logger.getLogger(TrainerService.class);

	/**
	 * Delete a single Trainer
	 *
	 * @param id
	 *            - to find by
	 */
	public void delete(Integer id) {
		log.trace("Method called to delete a trainer.");
		Trainer bt = trainerRepository.findByTrainerId(id);
		userService.delete(bt.getUserId());
	}

	/**
	 * Find a single Trainer by trainerId
	 *
	 * @param trainerId
	 *            - to find by
	 * @return TrainerUser - that was found
	 */
	public TrainerUser findById(Integer trainerId) {
		log.trace("Method called to find Trainer by ID with id: " + trainerId);
		Trainer bt = trainerRepository.findByTrainerId(trainerId);
		User u = userRepo.findByUserId(bt.getUserId());
		return new TrainerUser(u, bt);
	}

	/**
	 * Creates a new User in the User database and a new Trainer in the trainer
	 * database associated with that User.
	 * 
	 * @param -
	 *            trainerUser which contains the user and trainer data
	 * @return new TrainerUser - that was stored in the database.
	 */
	public TrainerUser newTrainer(TrainerUser tu) {
		log.trace("Method called to create a new trainer and user from TrainerUser object.");
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
			us.sendTrainer(result, "POST");
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
	 *            - which contains the pre-existing user information
	 * @return new TrainerUser - that was created from given user and new trainer
	 */
	public TrainerUser promoteToTrainer(TrainerUser tu) {
		log.trace("Method called to promote a user to a trainer.");
		User u = userRepo.findByUserId(tu.getUserId());
		Trainer bt = new Trainer();
		bt.setUserId(u.getUserId());
		bt.setTitle(tu.getTitle());
		bt.setTrainerId(0);
		return new TrainerUser(u, bt);
	}

	/**
	 * 
	 * Updates both the User and Trainer components of a trainer's credentials
	 * 
	 * @param TrainerUser
	 *            - which contains user and trainer information
	 * @return TrainerUser - which contains updated user and trainer information
	 */
	public TrainerUser update(TrainerUser tu) {
		log.trace("Method called to update a trainer and associated user.");
		log.info(("The trainer id passed in is " + tu.getTrainerId()));
		Trainer bt = trainerRepository.findByTrainerId(tu.getTrainerId());
		User u = userService.findUserById((bt.getUserId()));
		BeanUtils.copyProperties(tu, u, "userId");
		User persisted = userRepo.save(u);
		bt.setTitle(tu.getTitle());
		Trainer ret = trainerRepository.save(bt);
		TrainerUser result = ClassUtil.merge(persisted, ret);
		try {
			us.sendTrainer(result, "PUT");
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
	 * Searches Users by email, then searches trainers by the userId that was found.
	 *
	 * @param String
	 *            email - to search by
	 * @return TrainerUser - combination of the user and trainer that were found
	 */
	public TrainerUser findTrainerByEmail(String email) {
		log.trace("Method called to findTrainerByEmail with email: " + email);
		User u = userRepo.findByEmail(email);
		Trainer bt = trainerRepository.findByUserId(u.getUserId());
		return ClassUtil.merge(u, bt);
	}

	public List<String> allTitles() {
		log.trace("Method called to list all titles.");
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
		log.trace("Method called to get all trainers.");
		List<Trainer> allTrainers = trainerRepository.findAll();
		List<TrainerUser> result = new ArrayList<TrainerUser>();
		for (Trainer b : allTrainers) {
			result.add(ClassUtil.merge(userRepo.findByUserId(b.getUserId()), b));
		}
		return result;
	}

	public TrainerUser findByName(String firstName, String lastName) {
		log.trace("Method called to get findByName.");
		User u = userService.findByName(firstName, lastName);
		Trainer bt = trainerRepository.findByUserId(u.getUserId());
		return ClassUtil.merge(u, bt);
	}

}