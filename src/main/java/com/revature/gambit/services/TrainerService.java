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
	 * @return Trainer
	 */
	Trainer findById(Integer trainerId);

	/**
	 * Inserts a new Trainer into the User database.
	 * 
	 * @author Mark Fleres
	 * @param trainer
	 * @return new Trainer
	 */
	Trainer newTrainer(Trainer trainer);

	/**
	 * Converts a User to a Trainer. The promotion is performed by removing the User
	 * from the database, then registering it as a new Trainer. As a result, the User's
	 * userId will NOT be preserved.
	 * 
	 * @author Mark Fleres
	 * @param user - which contains the pre-existing user information
	 * @param title - the title of the new Trainer
	 * @return new Trainer - that was created from given user and new trainer
	 */
	Trainer promoteToTrainer(User user, String title);

	/**
	 * Updates Trainer information
	 * 
	 * @author Nikhil Pious
	 * @param trainer
	 * @return updated Trainer
	 */
	Trainer update(Trainer trainer);

	/**
	 * Returns a single Trainer with the given email.
	 * 
	 * @author Jeffrey Reyes
	 * @param email
	 * @return Trainer
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
	 * @param firstName, lastName
	 * @return Trainer
	 */
	Trainer findByName(String firstName, String lastName);

}