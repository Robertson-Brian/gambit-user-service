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
public class UserControllerImpl implements UserController {

	private static final Logger log = Logger.getLogger(UserControllerImpl.class);

	@Autowired
	private UserService userService;

	/* (non-Javadoc)
	 * @see com.revature.gambit.controllers.UserController#createUser(com.revature.gambit.entities.User)
	 */
	@Override
	@PostMapping
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		log.info("User Controller received request: create User: " + user);
		User persisted = userService.makeUser(user);
		return new ResponseEntity<>(persisted, HttpStatus.CREATED);
	}

	/* (non-Javadoc)
	 * @see com.revature.gambit.controllers.UserController#updateUser(com.revature.gambit.entities.User)
	 */
	@Override
	@PutMapping
	public ResponseEntity<Void> updateUser(@Valid @RequestBody User user) {
		log.info("User Controller received request: Update user " + user);
		userService.update(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	/* (non-Javadoc)
	 * @see com.revature.gambit.controllers.UserController#findUserByEmail(java.lang.String)
	 */
	@Override
	@GetMapping(value = "/email/{email:.+}/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> findUserByEmail(@PathVariable String email) {
		log.info(email);
		log.info("User Controller received request: Find user by email: " + email);
		User user = userService.findUserByEmail(email);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/* (non-Javadoc)
	 * @see com.revature.gambit.controllers.UserController#makeInactive(com.revature.gambit.entities.User)
	 */
	@Override
	@DeleteMapping
	public ResponseEntity<Void> makeInactive(@RequestBody User user) {
		log.info("User Controller received request: Updating user: " + user);
		user.setRole("INACTIVE");
		userService.update(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/* (non-Javadoc)
	 * @see com.revature.gambit.controllers.UserController#getAllUserRoles()
	 */
	@Override
	@GetMapping("roles")
	public ResponseEntity<List<String>> getAllUserRoles() {
		log.info("User Controller received request: Fetching all user roles");
		List<String> roles = userService.getAllRoles();
		return new ResponseEntity<>(roles, HttpStatus.OK);

	}

	/* (non-Javadoc)
	 * @see com.revature.gambit.controllers.UserController#getAllUsers()
	 */
	@Override
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		log.info("User Controller received request: get all users");
		List<User> userList = userService.getAllUsers();
		return new ResponseEntity<>(userList, HttpStatus.OK);

	}

	/* (non-Javadoc)
	 * @see com.revature.gambit.controllers.UserController#findUserById(java.lang.Integer)
	 */
	@Override
	@GetMapping("id/{id}")
	public ResponseEntity<User> findUserById(@PathVariable Integer id) {
		log.info("User Controller received request: find user by id.");
		User user = userService.findUserById(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/* (non-Javadoc)
	 * @see com.revature.gambit.controllers.UserController#findByName(java.lang.String, java.lang.String)
	 */
	@Override
	@GetMapping("name/{firstName}/{lastName}")
	public ResponseEntity<User> findByName(@PathVariable("firstName") String firstName,
			@PathVariable("lastName") String lastName) {
		log.info("User Controller received request: find user by name");
		User user = userService.findByName(firstName, lastName);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}
