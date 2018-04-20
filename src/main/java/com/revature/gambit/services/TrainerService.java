package com.revature.gambit.services;

import java.util.List;

import com.revature.gambit.entities.Trainer;

	/**
	 * 
	 * Performs insert, retrieval, update, deletion methods for Trainer users.
	 *
	 */
public interface TrainerService {

	/**
	 * Deletes a single Trainer from the database.
	 * 
	 * @param id
	 * @author Raymond Xia
	 * @return void
	 */
	void delete(Integer id);

	/**
	 * Retrieves a single Trainer by trainerId.
	 *
	 * @param trainerId
	 * @author Junyu Chen      
	 * @return trainer
	 */
	Trainer findById(Integer trainerId);

	/**
	 * Inserts a new Trainer into the User database.
	 * 
	 * @param trainer
	 * @author Mark Fleres
	 * @return trainer
	 */
	Trainer newTrainer(Trainer trainer);

	/**
	 * Converts a trainee user to a trainer user.
	 * 
	 * @param trainer
	 * @author Mark Fleres
	 * @return trainer
	 */
	Trainer promoteToTrainer(Trainer trainer);

	/**
	 * Updates Trainer information
	 * 
	 * @param Trainer
	 * @author Nikhil Pious
	 * @return trainer
	 */
	Trainer update(Trainer trainer);

	/**
	 * Returns a single Trainer from the email.
	 * 
	 * @param String
	 * @author Jeffrey Reyes
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
	 * @param String firstName, lastName
	 * @author Jeffrey Reyes
	 * @return trainer
	 */
	Trainer findByName(String firstName, String lastName);

}