package com.revature.gambit.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.revature.gambit.entities.User;

public interface UserController {

	/**
	 * Creates a new User.
	 * 
	 * @param user object to create
	 * @return created user and http status CREATED.
	 */
	ResponseEntity<User> createUser(User user);

	/**
	 * Update User information.
	 * 
	 * @param User to update
	 * @return http status success but no content
	 */
	ResponseEntity<Void> updateUser(User user);

	/**
	 * Finds the user by matching email address.
	 * 
	 * @param email to search by
	 * @return user with requested email and status OK
	 */
	ResponseEntity<User> findUserByEmail(String email);

	/**
	 * Change User role to inactive.
	 * 
	 * @param user to deactivate
	 * @return http status NO_CONTENT
	 */
	ResponseEntity<Void> makeInactive(User user);

	/**
	 * Get all User roles.
	 * 
	 * @return List<String> of all the roles a User can be.
	 */
	ResponseEntity<List<String>> getAllUserRoles();

	/**
	 * Retrieves all users from the User table.
	 * 
	 * @return List<user> of all users
	 */
	ResponseEntity<List<User>> getAllUsers();

	/**
	 * Finds an User by Id.
	 * 
	 * @param id of user to find
	 * @return user with the given id
	 */
	ResponseEntity<User> findUserById(Integer id);

	/**
	 * Find User by full name.
	 * 
	 * @param firstName
	 * @param lastName
	 * @return user with the associated first/last name
	 */
	ResponseEntity<User> findByName(String firstName, String lastName);

}