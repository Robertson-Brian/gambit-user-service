package com.revature.gambit.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.revature.gambit.entities.Trainer;

	/**
	 * 
	 * Accepts front-end requests and contacts the appropriate service method
	 * to insert, update, delete, and retrieve trainer information.
	 *
	 */
public interface TrainerController {

	/**
	 * Inserts a new Trainer.
	 * 
	 * @author Mark Fleres
	 * @param trainer
	 * @return ResponseEntity for the new Trainer,
	 * 		   and HTTP Status 200 "OK" or HTTP Status 400 "Bad Request".
	 */
	ResponseEntity<Trainer> registerTrainer(Trainer trainer);

	/**
	 * Converts a trainee user to a trainer user.
	 * 
	 * @author Mark Fleres
	 * @param trainer
	 * @return ResponseEntity for the promoted Trainer,
	 * 		   and HTTP Status 200 "OK" or HTTP Status 400 "Bad Request".
	 */
	ResponseEntity<Trainer> promote(Trainer trainer);

	/**
	 * Update Trainer information.
	 * 
	 * @author Nikhil Pious
	 * @param trainer
	 * @return ResponseEntity for the updated Trainer,
	 * 		   and HTTP Status 200 "OK".
	 */
	ResponseEntity<Trainer> updateTrainer(Trainer trainer);

	/**
	 * Retrieves Trainer by email.
	 * 
	 * @author Jeffrey Reyes
	 * @param email
	 * @return ResponseEntity<Trainer> associated with the email,
	 * 		   and HTTP Status 200 "OK".
	 */
	ResponseEntity<Trainer> findTrainerByEmail(String email);

	/**
	 * Retrieve Trainer by Id
	 * 
	 * @author Junyu Chen
	 * @param id
	 * @return ResponseEntity<Trainer> associated with the Id,
	 * 		   and HTTP Status 200 "OK".
	 */
	ResponseEntity<Trainer> findTrainerById(Integer id);

	/**
	 * Retrieves a list of all titles.
	 * 
	 * @author Jing Yu
	 * @return List<String> of all titles,
	 * 		   and HTTP Status 200 "OK".
	 */
	ResponseEntity<List<String>> getAllTitles();

	/**
	 * Retrieves a list of all trainers.
	 * 
	 * @author Jing Yu
	 * @return List<Trainer> of all Trainers,
	 * 		   and HTTP Status 200 "OK".
	 */
	ResponseEntity<List<Trainer>> getAll();

	/**
	 * Retrieves a Trainer by first and last name.
	 * 
	 * @author Jeffrey Reyes
	 * @param firstName, lastName
	 * @return ResponseEntity<Trainer> of the given Trainer,
	 * 		   and HTTP Status 200 "OK".
	 */
	ResponseEntity<Trainer> findByName(String firstName, String lastName);

	/**
	 * Delete trainer by the given ID.
	 * 
	 * @author Raymond Xia
	 * @param id
	 * @return empty ResponseEntity signifying a HTTP Status 200 "OK".
	 */
	ResponseEntity<?> deleteByTrainerId(Integer id);

}