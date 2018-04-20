package com.revature.gambit.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.gambit.entities.Trainer;
import com.revature.gambit.entities.User;
import com.revature.gambit.repositories.TrainerRepository;
import com.revature.gambit.repositories.UserRepository;


@Service("trainerService")
public class TrainerServiceImpl implements TrainerService {

	@Autowired
	public TrainerRepository trainerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	private static final Logger log = Logger.getLogger(TrainerServiceImpl.class);

	public void delete(Integer id) {
		log.debug("Method called to delete a trainer.");
		trainerRepository.delete(id);
	}

	public Trainer findById(Integer trainerId) {
		log.debug("Method called to find Trainer by ID with id: " + trainerId);
		return trainerRepository.findByUserId(trainerId);
	}

	public Trainer newTrainer(Trainer trainer) {
		log.debug("Method called to create a new trainer from Trainer object.");
		if(trainer == null ||
				trainer.getEmail() == null || trainer.getFirstName() == null || trainer.getLastName() == null || trainer.getTitle() == null ||
				trainer.getEmail() == "" || trainer.getFirstName() == "" || trainer.getLastName() == "" || trainer.getTitle() == "" ||
				findTrainerByEmail(trainer.getEmail()) != null) {
			return null;
		}
		return trainerRepository.save(trainer);
	}

	public Trainer promoteToTrainer(Trainer trainer) {
		log.debug("Method called to promote a user to a trainer.");
		User user = userRepository.findByUserId(trainer.getUserId());
		Trainer bt = new Trainer();
		bt.setUserId(user.getUserId());
		bt.setTitle(trainer.getTitle());
		bt.setUserId(0);
		return bt;
	}

	public Trainer update(Trainer trainer) {
		log.debug("Method called to update a trainer first name, last name, email, and title");
		Trainer updatingTrainer = trainerRepository.findByUserId(trainer.getUserId());
		BeanUtils.copyProperties(trainer, updatingTrainer,"userId");
		User user = userService.findUserById(updatingTrainer.getUserId());
		userRepository.save(user);
		return trainerRepository.save(updatingTrainer);
	}
	

	public Trainer findTrainerByEmail(String email) {
		log.debug("Method called to findTrainerByEmail with email: " + email);
		return trainerRepository.findByEmail(email);

	}

	public List<String> getAllTitles() {
		log.debug("Method called to list all titles.");
		return trainerRepository.findDistinctTitle();
	}

	public List<Trainer> getAll() {
		log.debug("Method called to get all trainers.");
		return trainerRepository.findAll();
	}

	public Trainer findByName(String firstName, String lastName) {
		log.debug("Method called to get findByName.");
		User u = userService.findByName(firstName, lastName);
		Trainer bt = trainerRepository.findByUserId(u.getUserId());
		return bt;
	}

}