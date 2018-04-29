package com.revature.gambit.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.revature.gambit.entities.Trainee;

public interface TraineeController {

	/**
	 * Returns all trainees from a batch that has the input batch id and input
	 * status. Merged two old endpoints into this one. The old endpoint urls were:
	 * "/all/trainee" and "/all/trainee/dropped".
	 * 
	 * In Caliber the old urls were: "${context}all/trainee?batch=${batchId}" and
	 * "${context}all/trainee/dropped?batch=${batchId}".
	 * 
	 * @author Brian Ethier
	 * @param batchId, status
	 * @return The list of trainees within that batch with the given batchId.
	 */
	ResponseEntity<List<Trainee>> findAllByBatchAndStatus(Integer id, String status);
	
	/**
	 * Returns all trainees from a batch that has the input batch id.
	 * 
	 * Creating this mapping from scratch. 
	 * 
	 * @param batchId
	 * @author Alejandro Iparraguirre
	 * @return The list of trainees within that batch with the given batchId.
	 */
	ResponseEntity<List<Trainee>> findAllByBatch(Integer id);
	
	/**
	 * Find all of the trainees without restriction.
	 * 
	 * @author Alejandro Iparraguirre
	 * @return a List of all trainees
	 */
	ResponseEntity<List<Trainee>> getAll();
	
	/**
	 * Find a trainee by userId
	 * 
	 * @return trainee with the corresponding userId
	 */
	ResponseEntity<Trainee> findByUserId(int userId);

	/**
	 * Creates a new trainee. The old endpoint url was: "/all/trainee/create" In
	 * Caliber the old url was: "${context}all/trainee/create".
	 * 
	 * @author Shaleen Anwar
	 * @param trainee
	 * @return The newly created trainee.
	 */
	ResponseEntity<Trainee> createTrainee(Trainee trainee);

	/**
	 * Updates the given trainee.
	 * 
	 * The old endpoint url was: "/all/trainee/update" in Caliber the old url was:
	 * "${context}all/trainee/update".
	 * 
	 * @author Ismael Khalil
	 * @param trainee
	 * @return A response entity signifying a successful update.
	 */
	ResponseEntity<Trainee> updateTrainee(Trainee trainee);

	/**
	 * Deletes the given trainee.
	 * 
	 * The old endpoint url was: "/all/trainee/delete/{id}" In Caliber the old url
	 * was: "${context}all/trainee/delete/${traineeId}"
	 *
	 * @author Joseph Arbelaez
	 * @param trainee
	 * @return A 204 status code: "No Content"
	 */
	ResponseEntity<?> deleteTrainee(Trainee trainee);
 
	/**
	 * Finds a trainee given the trainee's email. If the email is null or does not exist 
	 * a 404 error code is returned.
	 * The old endpoint url was: "/{email}".
	 * 
	 * @author Joel DeJesus
	 * @param email - Receives the email to be searched.
	 * @return The trainee associated with the email or a 404.
	 */
	ResponseEntity<Trainee> findByEmail(String email);

}