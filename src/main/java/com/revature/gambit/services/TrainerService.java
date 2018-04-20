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
	 * @return Trainer - that was found
	 */
	Trainer findById(Integer trainerId);

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
	 * @param trainer
	 *            - which contains the pre-existing user information
	 * @return new Trainer - that was created from given user and new trainer
	 */
	Trainer promoteToTrainer(Trainer trainer);

	/**
	 * 
	 * Updates both the User and Trainer components of a trainer's credentials
	 * 
	 * @param Trainer
	 *            - which contains user and trainer information
	 * @return Trainer - which contains updated user and trainer information
	 */
	Trainer update(Trainer trainer);

	/**
	 * Find a single Trainer by email
	 * 
	 * Searches Users by email, then searches trainers by the userId that was found.
	 *
	 * @param String
	 *            email - to search by
	 * @return Trainer - combination of the user and trainer that were found
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

	/**
	 * The method is used to get a trainer with full name
	 * @param firstName
	 * @param lastName
	 * @return a trainer
	 */
	Trainer findByName(String firstName, String lastName);

}