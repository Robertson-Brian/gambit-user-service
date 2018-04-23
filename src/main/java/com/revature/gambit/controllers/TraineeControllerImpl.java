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
		log.debug("Trainee Controller received request: Finding trainees for batch: " 
			+ id + " with status: " + status);
		List<Trainee> trainees = traineeService.findAllByBatchAndStatus(id, status);
		if(trainees != null) {
			return new ResponseEntity<>(trainees, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(trainees, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping
	public ResponseEntity<List<Trainee>> getAll() {
		log.debug("Trainee Controller received request: getAll trainees");
		return new ResponseEntity<List<Trainee>>(traineeService.getAll(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Trainee> createTrainee(@RequestBody Trainee trainee) {
		log.debug("Trainee Controller received request: Creating trainee: " + trainee);
		Trainee newTrainee = traineeService.save(trainee);
		if (newTrainee != null) {
			return new ResponseEntity<>(newTrainee, HttpStatus.CREATED); 
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
		}
	}

	@PutMapping
	public ResponseEntity<Trainee> updateTrainee(@RequestBody Trainee trainee) {
		log.debug("Trainee Controller received request: Updating trainee: " + trainee);
		return new ResponseEntity<>(traineeService.update(trainee), HttpStatus.NO_CONTENT);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteTrainee(@RequestBody Trainee trainee) {
		log.debug("TraineeControllerImpl.deleteTrainee: " + trainee);		
		traineeService.delete(trainee);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/email{email}")
	public ResponseEntity<Trainee> findByEmail(@RequestParam(value="email") String email) {
		log.debug("Finding trainees by email: " + email);
		Trainee trainee = traineeService.findByEmail(email);
		if (trainee != null) {
			return new ResponseEntity<>(trainee,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}