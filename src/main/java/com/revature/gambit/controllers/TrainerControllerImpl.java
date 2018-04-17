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

import com.revature.gambit.entities.TrainerUser;
import com.revature.gambit.services.TrainerService;

/**
 * Controller to retrieve Trainer information.
 *
 */
@RestController
@RequestMapping(value = "trainers", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrainerControllerImpl implements TrainerController {

	private static final Logger log = Logger.getLogger(TrainerControllerImpl.class);

	@Autowired
	private TrainerService trainerService;

	@PostMapping
	public ResponseEntity<TrainerUser> makeTrainer(@RequestBody TrainerUser tu) {
		log.info("Trainer Controller received request: create Trainer");
		TrainerUser t = trainerService.newTrainer(tu);
		return new ResponseEntity<>(t, HttpStatus.OK);
	}

	@PostMapping(value = "promote")
	public ResponseEntity<TrainerUser> promote(@RequestBody TrainerUser tu) {
		log.info("Trainer Controller received request: promote to Trainer");
		TrainerUser t = trainerService.promoteToTrainer(tu);
		return new ResponseEntity<>(t, HttpStatus.OK);

	}

	@PutMapping
	public ResponseEntity<TrainerUser> updateTrainer(@RequestBody TrainerUser tu) {
		log.info("Trainer Controller received request: update Trainer");
		TrainerUser t = trainerService.update(tu);
		return new ResponseEntity<>(t, HttpStatus.OK);
	}

	@GetMapping(value = "/email/{email:.+}/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainerUser> findTrainerByEmail(@PathVariable String email) {
		log.info("Trainer Controller received request: Finding trainer by email of " + email);
		TrainerUser tUser = trainerService.findTrainerByEmail(email);
		return new ResponseEntity<>(tUser, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainerUser> findTrainerById(@PathVariable("id") Integer id) {
		log.info("Trainer Controller received request: findTrainerById");
		return new ResponseEntity<TrainerUser>(trainerService.findById(id), HttpStatus.OK);
	}

	@GetMapping(value = "/titles")
	public ResponseEntity<List<String>> getTitles() {
		log.info("Trainer Controller received request: getTitles");
		List<String> titles = trainerService.allTitles();
		return new ResponseEntity<List<String>>(titles, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<TrainerUser>> getAll() {
		log.info("Trainer Controller received request: getAll");
		List<TrainerUser> allTrainers = trainerService.getAll();
		return new ResponseEntity<List<TrainerUser>>(allTrainers, HttpStatus.OK);
	}

	@GetMapping("name/{firstName}/{lastName}")
	public ResponseEntity<TrainerUser> findByName(@PathVariable("firstName") String firstName,
			@PathVariable("lastName") String lastName) {
		log.info("Trainer Controller received request: findByName");
		TrainerUser trainer = trainerService.findByName(firstName, lastName);
		return new ResponseEntity<TrainerUser>(trainer, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteByTrainerId(@PathVariable("id") Integer id) {
		log.info("Trainer Controller received request: deleteByTrainerId");
		trainerService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
