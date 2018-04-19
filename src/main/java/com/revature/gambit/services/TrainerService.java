package com.revature.gambit.services;

import java.util.List;

import com.revature.gambit.entities.Trainer;

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
	Trainer findById(Integer userId);

	/**
	 * Creates a new User in the User database and a new Trainer in the trainer
	 * database associated with that User.
	 * 
	 * @param -
	 *            trainerUser which contains the user and trainer data
	 * @return new TrainerUser - that was stored in the database.
	 */
	Trainer newTrainer(Trainer trainer);

	/**
	 * 
	 * Creates a new trainer object to associate with a pre-existing User object
	 * 
	 * @param trainerUser
	 *            - which contains the pre-existing user information
	 * @return new TrainerUser - that was created from given user and new trainer
	 */
	Trainer promoteToTrainer(Trainer trainer);

	/**
	 * 
	 * Updates both the User and Trainer components of a trainer's credentials
	 * 
	 * @param TrainerUser
	 *            - which contains user and trainer information
	 * @return TrainerUser - which contains updated user and trainer information
	 */
	Trainer update(Trainer tu);

	/**
	 * Find a single Trainer by email
	 * 
	 * Searches Users by email, then searches trainers by the userId that was found.
	 *
	 * @param String
	 *            email - to search by
	 * @return TrainerUser - combination of the user and trainer that were found
	 */
	Trainer findTrainerByEmail(String email);
    
	/**
	 * This method is used to get all trainer titles.
	 * 
	 * @return  a list of titles
	 */
	List<String> getAllTitles();

	/**
	 * This method is used to get all trainers.
	 * 
	 * @return a list of trainers
	 */
	List<Trainer> getAll();

	Trainer findByName(String firstName, String lastName);

}