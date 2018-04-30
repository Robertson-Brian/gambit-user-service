package com.revature.gambit.services;

import java.util.List;

import com.revature.gambit.entities.Trainer;
import com.revature.gambit.entities.User;

	/**
	 * 
	 * Performs insert, retrieval, update, deletion methods for Trainer users.
	 *
	 */
public interface TrainerService {

	/**
	 * Deletes a single Trainer from the database.
	 * 
	 * @author Raymond Xia
	 * @param id
	 * @return void
	 */
	void delete(Integer id);

	/**
	 * Retrieves a single Trainer by trainerId.
	 *
	 * @author Junyu Chen 
	 * @param trainerId     
	 * @return trainer
	 */
	Trainer findById(Integer trainerId);

	/**
	 * Inserts a new Trainer into the User database.
	 * 
	 * @author Mark Fleres
	 * @param trainer
	 * @return trainer
	 */
	Trainer newTrainer(Trainer trainer);

	/**
	 * Converts a User to a Trainer. The promotion is performed by removing the User
	 * from the database, then registering it as a new Trainer. As a result, the User's
	 * userId will NOT be preserved.
	 * 
	 * @author Mark Fleres
	 * @param user
	 *            - which contains the pre-existing user information
	 * @param title
	 * 			  - The title of the new Trainer
	 * @return new Trainer - that was created from given user and new trainer
	 */
	Trainer promoteToTrainer(User user, String title);

	/**
	 * Updates Trainer information
	 * 
	 * @author Nikhil Pious
	 * @param Trainer
	 * @return trainer
	 */
	Trainer update(Trainer trainer);

	/**
	 * Returns a single Trainer from the email.
	 * 
	 * @author Jeffrey Reyes
	 * @param String
	 * @return trainer
	 */
	Trainer findTrainerByEmail(String email);
    
	/**
	 * Retrieves all trainer titles.
	 * 
	 * @author Jing Yu
	 * @return List<String> of titles
	 */
	List<String> getAllTitles();

	/**
	 * Retrieves all trainers.
	 * 
	 * @author Jing Yu
	 * @return List<Trainer> of trainers
	 */
	List<Trainer> getAll();

	/**
	 * Retrieves a trainer by their full name.
	 * 
	 * @author Jeffrey Reyes
	 * @param String firstName, lastName
	 * @return trainer
	 */
	Trainer findByName(String firstName, String lastName);

}