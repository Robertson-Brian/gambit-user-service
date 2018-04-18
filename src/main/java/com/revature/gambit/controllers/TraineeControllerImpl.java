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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.gambit.entities.Trainee;
import com.revature.gambit.services.TraineeService;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Handles all Janus requests for Trainee resources.
 *
 */
@RestController("traineeController")
@RequestMapping(value = "trainees", produces = MediaType.APPLICATION_JSON_VALUE)
public class TraineeControllerImpl implements TraineeController {
	private static final Logger log = Logger.getLogger(TraineeControllerImpl.class);
	
	@Autowired
	private TraineeService traineeService;
	
	@GetMapping("batch/{id}/status/{status}")
	public ResponseEntity<List<Trainee>> findAllByBatchAndStatus(@PathVariable Integer id,
			@PathVariable String status) {
		log.debug("Trainee Controller received request: Finding trainees for batch: " + id + " with status: " + status);
		return new ResponseEntity<>(traineeService.findAllByBatchAndStatus(id, status), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Trainee>> getAll() {
		log.debug("Trainee Controller received request: getAll trainees");
		return new ResponseEntity<List<Trainee>>(traineeService.getAll(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Trainee> createTrainee(@RequestBody Trainee trainee) {
		log.debug("Trainee Controller received request: Creating trainee: " + trainee);
		return new ResponseEntity<>(traineeService.save(trainee), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Trainee> updateTrainee(@RequestBody Trainee trainee) {
		log.debug("Trainee Controller received request: Updating trainee: " + trainee);
		return new ResponseEntity<>(traineeService.save(trainee), HttpStatus.NO_CONTENT);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteTrainee(@RequestBody Trainee trainee) {
		log.debug("TraineeControllerImpl.deleteTrainee: " + trainee);		
		traineeService.delete(trainee);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{email}")
	public ResponseEntity<Trainee> findByEmail(@PathVariable String email) {
		log.debug("Finding trainees by email: " + email);
		return new ResponseEntity<>(traineeService.findByEmail(email), HttpStatus.OK);
	}

}