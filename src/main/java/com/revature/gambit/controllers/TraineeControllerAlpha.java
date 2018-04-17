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

@RestController
@RequestMapping(value = "trainees", produces = MediaType.APPLICATION_JSON_VALUE)
public class TraineeControllerAlpha implements TraineeController {
	private static final Logger log = Logger.getLogger(TraineeControllerAlpha.class);

	@Autowired
	private TraineeService traineeService;

	@GetMapping("batch/{id}/status/{status}")
	public ResponseEntity<List<Trainee>> findAllByBatchAndStatus(@PathVariable Integer id,
			@PathVariable String status) {
		log.info("Trainee Controller received request: Finding trainees for batch: " + id + " with status: " + status);
		return new ResponseEntity<>(traineeService.findAllByBatchAndStatus(id, status), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Trainee>> getAll() {
		log.info("Trainee Controller received request: getAll trainees");
		return new ResponseEntity<List<Trainee>>(traineeService.getAll(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Trainee> createTrainee(@RequestBody Trainee trainee) {
		log.info("Trainee Controller received request: Creating trainee: " + trainee);
		return new ResponseEntity<>(traineeService.save(trainee), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Trainee> updateTrainee(@RequestBody Trainee trainee) {
		log.info("Trainee Controller received request: Updating trainee: " + trainee);
		return new ResponseEntity<>(traineeService.save(trainee), HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{traineeId}")
	public ResponseEntity<Void> deleteTrainee(@PathVariable Integer traineeId) {
		log.info("Trainee Controller received request: Deleting trainee: " + traineeId);		
		traineeService.delete(traineeId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}