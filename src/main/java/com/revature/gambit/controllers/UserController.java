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
import com.revature.gambit.services.UserService;

/**
 * Controller to retrieve User information.
 *
 */
@RestController
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	private static final Logger log = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	/**
	 * Creates a new User.
	 * 
	 * @param user object to create
	 * @return created user and http status CREATED.
	 */
	@PostMapping
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		log.info("User Controller received request: create User: " + user);
		User persisted = userService.makeUser(user);
		return new ResponseEntity<>(persisted, HttpStatus.CREATED);
	}

	/**
	 * Update User information.
	 * 
	 * @param User to update
	 * @return http status success but no content
	 */
	@PutMapping
	public ResponseEntity<Void> updateUser(@Valid @RequestBody User user) {
		log.info("User Controller received request: Update user " + user);
		userService.update(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	/**
	 * Finds the user by matching email address.
	 * 
	 * @param email to search by
	 * @return user with requested email and status OK
	 */
	@GetMapping(value = "/email/{email:.+}/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> findUserByEmail(@PathVariable String email) {
		log.info(email);
		log.info("User Controller received request: Find user by email: " + email);
		User user = userService.findUserByEmail(email);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * Change User role to inactive.
	 * 
	 * @param user to deactivate
	 * @return http status NO_CONTENT
	 */
	@DeleteMapping
	public ResponseEntity<Void> makeInactive(@RequestBody User user) {
		log.info("User Controller received request: Updating user: " + user);
		user.setRole("INACTIVE");
		userService.update(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Get all User roles.
	 * 
	 * @return List<String> of all the roles a User can be.
	 */
	@GetMapping("roles")
	public ResponseEntity<List<String>> getAllUserRoles() {
		log.info("User Controller received request: Fetching all user roles");
		List<String> roles = userService.getAllRoles();
		return new ResponseEntity<>(roles, HttpStatus.OK);

	}

	/**
	 * Retrieves all users from the User table.
	 * 
	 * @return List<user> of all users
	 */
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		log.info("User Controller received request: get all users");
		List<User> userList = userService.getAllUsers();
		return new ResponseEntity<>(userList, HttpStatus.OK);

	}

	/**
	 * Finds an User by Id.
	 * 
	 * @param id of user to find
	 * @return user with the given id
	 */
	@GetMapping("id/{id}")
	public ResponseEntity<User> findUserById(@PathVariable Integer id) {
		log.info("User Controller received request: find user by id.");
		User user = userService.findUserById(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * Find User by full name.
	 * 
	 * @param firstName
	 * @param lastName
	 * @return user with the associated first/last name
	 */
	@GetMapping("name/{firstName}/{lastName}")
	public ResponseEntity<User> findByName(@PathVariable("firstName") String firstName,
			@PathVariable("lastName") String lastName) {
		log.info("User Controller received request: find user by name");
		User user = userService.findByName(firstName, lastName);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}
