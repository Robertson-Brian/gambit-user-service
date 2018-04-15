package com.revature.gambit.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.gambit.entities.Trainee;
import com.revature.gambit.services.TraineeService;

/**
 * Handles all Janus requests for Trainee resources.
 *
 */
@RestController
@RequestMapping(value = "trainees", produces = MediaType.APPLICATION_JSON_VALUE)
public class TraineeController {
	private static final Logger log = Logger.getLogger(TraineeController.class);

	@Autowired
	private TraineeService traineeService;

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
	@GetMapping("batch/{id}/status/{status}")
	// @PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'STAGING', 'PANEL')")
	public ResponseEntity<List<Trainee>> findAllByBatchAndStatus(@PathVariable Integer id,
			@PathVariable String status) {
		log.info("Trainee Controller received request: Finding trainees for batch: " + id + " with status: " + status);
		return new ResponseEntity<>(traineeService.findAllByBatchAndStatus(id, status), HttpStatus.OK);
	}

	/**
	 * 
	 * @return a List of all trainees
	 */
	@GetMapping
	public ResponseEntity<List<Trainee>> getAll() {
		log.info("Trainee Controller received request: getAll trainees");
		return new ResponseEntity<List<Trainee>>(traineeService.getAll(), HttpStatus.OK);
	}

	/**
	 * Creates a new trainee. The old endpoint url was: "/all/trainee/create" In
	 * Caliber the old url was: "${context}all/trainee/create".
	 * 
	 * @param trainee
	 *            - the trainee to be created.
	 * @return The newly created trainee.
	 */
	@PostMapping
	// @PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER', 'PANEL')")
	public ResponseEntity<Trainee> createTrainee(@RequestBody Trainee trainee) {
		log.info("Trainee Controller received request: Creating trainee: " + trainee);
		return new ResponseEntity<>(traineeService.save(trainee), HttpStatus.CREATED);
	}

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
	@PutMapping
	// @PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER','PANEL')")
	public ResponseEntity<Trainee> updateTrainee(@RequestBody Trainee trainee) {
		log.info("Trainee Controller received request: Updating trainee: " + trainee);
		return new ResponseEntity<>(traineeService.save(trainee), HttpStatus.NO_CONTENT);
	}

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
	@DeleteMapping("/{traineeId}")
	// @PreAuthorize("hasAnyRole('VP', 'QC', 'TRAINER','PANEL')")
	public ResponseEntity<Void> deleteTrainee(@PathVariable Integer traineeId) {
		log.info("Trainee Controller received request: Deleting trainee: " + traineeId);		
		traineeService.delete(traineeId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}