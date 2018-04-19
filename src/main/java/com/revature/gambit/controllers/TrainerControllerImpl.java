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

import com.revature.gambit.entities.Trainer;
import com.revature.gambit.services.TrainerService;

/**
 * Controller to retrieve Trainer information.
 *
 */
@RestController("trainerController")
@RequestMapping(value = "trainers", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrainerControllerImpl implements TrainerController {

	private static final Logger log = Logger.getLogger(TrainerControllerImpl.class);

	@Autowired
	private TrainerService trainerService;

	@PostMapping
	public ResponseEntity<Trainer> registerTrainer(@RequestBody Trainer trainer) {
		log.info("Trainer Controller received request: create Trainer");
		Trainer registeredTrainer = trainerService.newTrainer(trainer);
		return new ResponseEntity<>(registeredTrainer, HttpStatus.OK);
	}

	@PostMapping(value = "promote")
	public ResponseEntity<Trainer> promote(@RequestBody Trainer tu) {
		log.info("Trainer Controller received request: promote to Trainer");
		Trainer t = trainerService.promoteToTrainer(tu);
		return new ResponseEntity<>(t, HttpStatus.OK);

	}

	@PutMapping
	public ResponseEntity<Trainer> updateTrainer(@RequestBody Trainer tu) {
		log.info("Trainer Controller received request: update Trainer");
		Trainer t = trainerService.update(tu);
		return new ResponseEntity<>(t, HttpStatus.OK);
	}

	@GetMapping(value = "/email/{email:.+}/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Trainer> findTrainerByEmail(@PathVariable String email) {
		log.info("Trainer Controller received request: Finding trainer by email of " + email);
		Trainer tUser = trainerService.findTrainerByEmail(email);
		return new ResponseEntity<>(tUser, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Trainer> findTrainerById(@PathVariable("id") Integer id) {
		log.info("Trainer Controller received request: findTrainerById");
		return new ResponseEntity<Trainer>(trainerService.findById(id), HttpStatus.OK);
	}

	@GetMapping(value = "/titles")
	public ResponseEntity<List<String>> getAllTitles() {
		log.info("Trainer Controller received request: getTitles");
		return new ResponseEntity<List<String>>(trainerService.getAllTitles(), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Trainer>> getAll() {
		log.info("Trainer Controller received request: getAll");
		return new ResponseEntity<List<Trainer>>(trainerService.getAll(), HttpStatus.OK);
	}

	@GetMapping("name/{firstName}/{lastName}")
	public ResponseEntity<Trainer> findByName(@PathVariable("firstName") String firstName,
			@PathVariable("lastName") String lastName) {
		log.info("Trainer Controller received request: findByName");
		Trainer trainer = trainerService.findByName(firstName, lastName);
		return new ResponseEntity<Trainer>(trainer, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteByTrainerId(@PathVariable("id") Integer id) {
		log.info("Trainer Controller received request: deleteByTrainerId");
		trainerService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
