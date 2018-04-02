package com.revature.hydra.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.hydra.entities.TrainerUser;
import com.revature.hydra.services.TrainerService;
import com.revature.hydra.services.UserService;

/**
 * Controller to retrieve Trainer information.
 *
 */

@RestController
@CrossOrigin
@RequestMapping(value = "trainers", produces = MediaType.APPLICATION_JSON_VALUE)
public class TrainerController {

	private static final Logger log = Logger.getLogger(TrainerController.class);

	@Autowired
	private TrainerService trainerService;

	@Autowired
	private UserService userService;

	@GetMapping("roles")
	public ResponseEntity<List<String>> getAllUserRoles() {
		log.info("Trainer Controller received request: Fetching all user roles");
		List<String> roles = userService.getAllRoles();
		return new ResponseEntity<>(roles, HttpStatus.OK);

	}

	/**
	 * Creates a new Trainer
	 * 
	 * @param tu
	 * @return
	 */
	@PostMapping
	public ResponseEntity<TrainerUser> makeTrainer(@RequestBody TrainerUser tu) {
		log.info("Trainer Controller received request: create Trainer");
		TrainerUser t = trainerService.newTrainer(tu);
		return new ResponseEntity<>(t, HttpStatus.OK);
	}

	/**
	 * Promotes User to Trainer.
	 * 
	 * @param tu
	 * @return
	 */
	@PostMapping(value = "promote")
	public ResponseEntity<TrainerUser> promote(@RequestBody TrainerUser tu) {
		log.info("Trainer Controller received request: promote to Trainer");
		TrainerUser t = trainerService.promoteToTrainer(tu);
		return new ResponseEntity<>(t, HttpStatus.OK);

	}

	/**
	 * Update Trainer information.
	 * 
	 * @param tu
	 * @return
	 */
	@PutMapping
	public ResponseEntity<TrainerUser> updateTrainer(@RequestBody TrainerUser tu) {
		log.info("Trainer Controller received request: update Trainer");
		TrainerUser t = trainerService.update(tu);
		return new ResponseEntity<>(t, HttpStatus.OK);
	}

	/**
	 * Finds Trainer by email.
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping(value = "/email/{email:.+}/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainerUser> findTrainerByEmail(@PathVariable String email) {
		log.info("Trainer Controller received request: Finding trainer by email of " + email);
		TrainerUser user = trainerService.findTrainerByEmail(email);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * Retrieve Trainer by Id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TrainerUser> findTrainerById(@PathVariable("id") Integer id) {
		log.info("Trainer Controller received request: findTrainerById");
		return new ResponseEntity<TrainerUser>(trainerService.findById(id), HttpStatus.OK);
	}

	/**
	 * Retrieve all titles.
	 * 
	 * @return
	 */
	@GetMapping(value = "/titles")
	public ResponseEntity<List<String>> getTitles() {
		log.info("Trainer Controller received request: getTitles");
		List<String> titles = trainerService.allTitles();
		return new ResponseEntity<List<String>>(titles, HttpStatus.OK);
	}

	/**
	 * Deactivates the User account associated with the given TrainerId.
	 */
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteByTrainerId(@PathVariable("id") Integer id) {
		log.info("Trainer Controller received request: deleteByTrainerId");
		trainerService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * Retrieve all trainers.
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<TrainerUser>> getAll() {
		log.info("Trainer Controller received request: getAll");
		List<TrainerUser> allTrainers = trainerService.getAll();
		return new ResponseEntity<List<TrainerUser>>(allTrainers, HttpStatus.OK);
	}

	/**
	 * Finds a user by unique firstname/lastname combination. This needs further
	 * thought.
	 */
	@GetMapping("name/{firstName}/{lastName}")
	public ResponseEntity<TrainerUser> findByName(@PathVariable("firstName") String firstName,
			@PathVariable("lastName") String lastName) {
		log.info("Trainer Controller received request: findByName");
		TrainerUser trainer = trainerService.findByName(firstName, lastName);
		return new ResponseEntity<TrainerUser>(trainer, HttpStatus.OK);
	}

	// This has yet to be implemented. Required RabbitMQ.
	// /**
	// * Returns all trainers titles from the database `
	// *
	// * @return
	// */
	// @RequestMapping(value = "trainers/roles", method = RequestMethod.GET,
	// produces = MediaType.APPLICATION_JSON_VALUE)
	// // @PreAuthorize("hasAnyRole('VP', 'TRAINER', 'STAGING', 'QC', 'PANEL')")
	// public ResponseEntity<List<TrainerRole>> getAllTrainersRoles() {
	// log.info("Fetching all trainers roles");
	// List<TrainerRole> trainers =
	// trainerService.trainerRepository.findAllTrainerRoles();
	// return new ResponseEntity<>(trainers, HttpStatus.OK);
	// }

}
