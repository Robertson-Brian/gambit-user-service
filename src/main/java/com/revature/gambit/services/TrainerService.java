package com.revature.gambit.services;

import java.util.List;

import com.revature.gambit.entities.TrainerUser;

public interface TrainerService {

	/**
	 * Delete a single Trainer
	 *
	 * @param id
	 *            - to find by
	 */
	void delete(Integer id);

	/**
	 * Find a single Trainer by trainerId
	 *
	 * @param trainerId
	 *            - to find by
	 * @return TrainerUser - that was found
	 */
	TrainerUser findById(Integer trainerId);

	/**
	 * Creates a new User in the User database and a new Trainer in the trainer
	 * database associated with that User.
	 * 
	 * @param -
	 *            trainerUser which contains the user and trainer data
	 * @return new TrainerUser - that was stored in the database.
	 */
	TrainerUser newTrainer(TrainerUser tu);

	/**
	 * 
	 * Creates a new trainer object to associate with a pre-existing User object
	 * 
	 * @param trainerUser
	 *            - which contains the pre-existing user information
	 * @return new TrainerUser - that was created from given user and new trainer
	 */
	TrainerUser promoteToTrainer(TrainerUser tu);

	/**
	 * 
	 * Updates both the User and Trainer components of a trainer's credentials
	 * 
	 * @param TrainerUser
	 *            - which contains user and trainer information
	 * @return TrainerUser - which contains updated user and trainer information
	 */
	TrainerUser update(TrainerUser tu);

	/**
	 * Find a single Trainer by email
	 * 
	 * Searches Users by email, then searches trainers by the userId that was found.
	 *
	 * @param String
	 *            email - to search by
	 * @return TrainerUser - combination of the user and trainer that were found
	 */
	TrainerUser findTrainerByEmail(String email);

	List<String> allTitles();

	/**
	 * Implementation should be improved. This many individual DB calls could take a
	 * very long time to resolve.
	 * 
	 * @return
	 */
	List<TrainerUser> getAll();

	TrainerUser findByName(String firstName, String lastName);

}