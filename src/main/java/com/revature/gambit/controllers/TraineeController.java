package com.revature.gambit.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.revature.gambit.entities.Trainee;

public interface TraineeController {

	/**
	 * Creates a new trainee. The old endpoint url was: "/all/trainee/create" In
	 * Caliber the old url was: "${context}all/trainee/create".
	 * 
	 * @author Shaleen Anwar
	 * @param trainee
	 * @return ResponseEntity for the new Trainee,
	 * 			and HTTP Status 201 "CREATED" or HTTP Status 400 "Bad Request".
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
	 * @return ResponseEntity for the updated Trainee,
	 * 		   and HTTP Status 204 "NO CONTENT".
	 */
	ResponseEntity<Trainee> updateTrainee(Trainee trainee);

	/**
	 * Deletes the given trainee.
	 * 
	 * The old endpoint url was: "/all/trainee/delete/{id}" In Caliber the old URL
	 * was: "${context}all/trainee/delete/${traineeId}"
	 *
	 * @author Joseph Arbelaez
	 * @param trainee
	 * @return empty ResponseEntity signifying an HTTP Status 204 "NO CONTENT".
	 */
	ResponseEntity<?> deleteTrainee(Trainee trainee);

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
	 * @return List<Trainee> of trainees within that batch with the given batchId,
	 * 		   and HTTP Status 200 "OK" or HTTP Status 400 "BAD REQUEST".
	 */
	ResponseEntity<List<Trainee>> findAllByBatchAndStatus(Integer id, String status);

	/**
	 * Returns all trainees from a batch that has the input batch id.
	 * 
	 * Creating this mapping from scratch. 
	 * 
	 * @author Alejandro Iparraguirre
	 * @param batchId
	 * @return List<Trainee> of trainees within that batch with the given batchId,
	 * 		   and HTTP Status 200 "OK" or HTTP Status 404 "NO CONTENT".
	 */
	ResponseEntity<List<Trainee>> findAllByBatch(Integer id);

	/**
	 * Find all of the trainees without restriction.
	 * 
	 * @author Alejandro Iparraguirre
	 * @return List<Trainee> of all trainees,
	 * 		   and HTTP Status 200 "OK".
	 */
	ResponseEntity<List<Trainee>> getAll();

	/**
	 * Find a trainee by userId
	 * 
	 * @author James Kempf
	 * @param userId
	 * @return ResponseEntity<Trainee> with the corresponding userId,
	 * 		   and HTTP Status 200 "OK".
	 */
	ResponseEntity<Trainee> findByUserId(int userId);

	/**
	 * Finds a trainee given the trainee's email. If the email is null or does not exist 
	 * a 404 error code is returned.
	 * The old endpoint url was: "/{email}".
	 * 
	 * @author Joel DeJesus
	 * @param email
	 * @return ResponseEntity<Trainee> associated with the email,
	 * 		   and HTTP Status 200 "OK" or HTTP Status 404 "NO CONTENT".
	 */
	ResponseEntity<Trainee> findByEmail(String email);

}