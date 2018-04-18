package com.revature.gambit.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.revature.gambit.entities.Trainer;

public interface TrainerController {

	/**
	 * Creates a new Trainer
	 * 
	 * @param TrainerUser
	 *            - the TrainerUser used to
	 *             create the trainer and corresponding
	 *            user
	 * @return the trainerUser object and http status 200
	 */
	ResponseEntity<Trainer> makeTrainer(Trainer tu);

	/**
	 * Promotes User to Trainer.
	 * 
	 * Users can be trainees or trainers. This function is used to create a
	 * TrainerUser object from a trainer
	 * 
	 * @param TrainerUser
	 *            - contains the user information
	 * @return new TrainerUser created
	 */
	ResponseEntity<Trainer> promote(Trainer tu);

	/**
	 * Update Trainer information.
	 * 
	 * @param TrainerUser
	 *            to be updated
	 * @return the updated TrainerUser
	 */
	ResponseEntity<Trainer> updateTrainer(Trainer tu);

	/**
	 * Finds Trainer by email.
	 * 
	 * @param email
	 *            to find by
	 * @return requested TrainerUser
	 */
	ResponseEntity<Trainer> findTrainerByEmail(String email);

	/**
	 * Retrieve Trainer by Id
	 * 
	 * @param id
	 *            of trainer
	 * @return TrainerUser of requested trainer
	 */
	ResponseEntity<Trainer> findTrainerById(Integer id);

	/**
	 * Retrieve all titles.
	 * 
	 * @return List<String> of the titles
	 */
	ResponseEntity<List<String>> getTitles();

	/**
	 * Retrieve all trainers.
	 * 
	 * @return List<TrainerUser> of all trainers
	 */
	ResponseEntity<List<Trainer>> getAll();

	/**
	 * Finds a user by unique firstname/lastname combination. This needs further
	 * thought.
	 * 
	 * @param first
	 *            name and last name to search by
	 * @return TrainerUser with the requested name
	 */
	ResponseEntity<Trainer> findByName(String firstName, String lastName);

	/**
	 * Deactivates the User account associated with the given TrainerId.
	 * 
	 * @return Http status 200
	 */
	ResponseEntity<?> deleteByTrainerId(Integer id);

}