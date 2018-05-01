package com.revature.gambit.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.revature.gambit.entities.User;
import com.revature.gambit.entities.UserRole;

public interface UserController {

	/**
	 * Creates a new User.
	 * 
	 * @param user
	 * @return ResponseEntity for the new User,
	 * 			and HTTP Status 201 "CREATED" or HTTP Status 401 "UNAUTHORIZED".
	 */
	ResponseEntity<User> createUser(User user);

	/**
	 * Update User information.
	 * 
	 * @param user
	 * @return ResponseEntity for the updated User,
	 * 		   and HTTP Status 204 "NO CONTENT" or HTTP Status 401 "UNAUTHORIZED".
	 */
	ResponseEntity<User> updateUser(User user);

	/**
	 * Finds the user by matching email address.
	 * 
	 * @param email
	 * @return ResponseEntity<User> associated with the email,
	 * 		   and HTTP Status 200 "OK".
	 */
	ResponseEntity<User> findUserByEmail(String email);

	/**
	 * Change User role to inactive.
	 * 
	 * @param user to deactivate
	 * @return empty ResponseEntity signifying an HTTP Status 204 "NO CONTENT".
	 */
	ResponseEntity<Void> makeInactive(User user);

	/**
	 * Get all User roles.
	 * 
	 * @return List<String> of all the roles a User can be,
	 * 		   and HTTP Status 200 "OK".
	 */
	ResponseEntity<List<UserRole>> getAllUserRoles();

	/**
	 * Retrieves all users from the User table.
	 * 
	 * @return List<user> of all users,
	 * 		   and HTTP Status 200 "OK".
	 */
	ResponseEntity<List<User>> getAllUsers();

	/**
	 * Finds an User by Id.
	 * 
	 * @param id
	 * @return ResponseEntity<User> with the corresponding userId,
	 * 		   and HTTP Status 200 "OK".
	 */
	ResponseEntity<User> findUserById(Integer id);

	/**
	 * Find User by full name.
	 * 
	 * @param firstName, lastName
	 * @return ResponseEntity<User> of the given User,
	 * 		   and HTTP Status 200 "OK".
	 */
	ResponseEntity<User> findByName(String firstName, String lastName);
	
}