package com.revature.gambit.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
	 * @param batchId
	 *            - id of the batch desired. status - status of trainees desired.
	 * @return The list of trainees within that batch with the given batchId.
	 */

	// @PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'STAGING', 'PANEL')")
	ResponseEntity<List<Trainee>> findAllByBatchAndStatus(Integer id, String status);

	/**
	 * 
	 * @return a List of all trainees
	 */
	ResponseEntity<List<Trainee>> getAll();

	/**
	 * Creates a new trainee. The old endpoint url was: "/all/trainee/create" In
	 * Caliber the old url was: "${context}all/trainee/create".
	 * 
	 * @param trainee
	 *            - the trainee to be created.
	 * @return The newly created trainee.
	 */

	// @PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'PANEL')")
	ResponseEntity<Trainee> createTrainee(Trainee trainee);

	/**
	 * Updates the given trainee.
	 * 
	 * The old endpoint url was: "/all/trainee/update" in Caliber the old url was:
	 * "${context}all/trainee/update".
	 * 
	 * @param trainee
	 *            - the trainee to be updated.
	 * @return A response entity signifying a successful update.
	 */

	// @PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER','PANEL')")
	ResponseEntity<Trainee> updateTrainee(Trainee trainee);

	/**
	 * Deletes the given trainee.
	 * 
	 * The old endpoint url was: "/all/trainee/delete/{id}" In Caliber the old url
	 * was: "${context}all/trainee/delete/${traineeId}"
	 *
	 * @param traineeId
	 *            - the id of the trainee to delete
	 * @return A response entity signifying a successful deletion
	 */

	// @PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER','PANEL')")
	ResponseEntity<Void> deleteTrainee(Integer traineeId);

	// @PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'STAGING', 'PANEL')")
	ResponseEntity<Trainee> findByEmail(String email);

}