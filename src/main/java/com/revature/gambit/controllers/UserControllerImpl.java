package com.revature.gambit.controllers;

import java.util.List;

import javax.validation.Valid;

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

import com.revature.gambit.entities.User;
import com.revature.gambit.entities.UserRole;
import com.revature.gambit.exceptions.AuthUserException;
import com.revature.gambit.services.UserService;

/**
 * Controller to retrieve User information.
 *
 */
@RestController("userController")
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserControllerImpl implements UserController {

	private static final Logger log = Logger.getLogger(UserControllerImpl.class);
	
	private static final String TRAINER_ROLE = "ROLE_TRAINER";
	private static final String ASSOCIATE_ROLE = "";

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		log.info("User Controller received request: create User: " + user);
		User persisted;
		if((persisted = userService.makeUser(user)) != null){
			return new ResponseEntity<>(persisted, HttpStatus.CREATED);
		} else {
			throw new AuthUserException("User not added", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping
	public ResponseEntity<Void> updateUser(@Valid @RequestBody User user) {
		log.info("User Controller received request: Update user " + user);
		userService.update(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	@GetMapping(value = "/email/{email:.+}/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> findUserByEmail(@PathVariable String email) {
		log.info(email);
		log.info("User Controller received request: Find user by email: " + email);
		User user = userService.findUserByEmail(email);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<Void> makeInactive(@RequestBody User user) {
		log.info("User Controller received request: Updating user: " + user);
		//Logic here should be getting the role from the DB.
		//user.setRole("INACTIVE");
		userService.update(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("roles")
	public ResponseEntity<List<String>> getAllUserRoles() {
		log.info("User Controller received request: Fetching all user roles");
		List<String> roles = userService.getAllRoles();
		return new ResponseEntity<>(roles, HttpStatus.OK);

	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		log.info("User Controller received request: get all users");
		List<User> userList = userService.getAllUsers();
		return new ResponseEntity<>(userList, HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<User> findUserById(@PathVariable Integer id) {
		log.info("User Controller received request: find user by id.");
		User user = userService.findUserById(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("name/{firstName}/{lastName}")
	public ResponseEntity<User> findByName(@PathVariable("firstName") String firstName,
			@PathVariable("lastName") String lastName) {
		log.info("User Controller received request: find user by name");
		User user = userService.findByName(firstName, lastName);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<User>> getAllTrainers() {
		UserRole role = userService.findUserRoleByName(TRAINER_ROLE);
		List<User> trainersList = userService.findByRole(role);
		return new ResponseEntity<>(trainersList, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<User>> getAllAssociates() {
		UserRole role = userService.findUserRoleByName(ASSOCIATE_ROLE);
		List<User> associatesList = userService.findByRole(role);
		return new ResponseEntity<>(associatesList, HttpStatus.OK);
	}

}
