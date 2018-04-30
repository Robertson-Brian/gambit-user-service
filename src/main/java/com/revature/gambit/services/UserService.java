package com.revature.gambit.services;

import java.util.List;

import com.revature.gambit.entities.User;
import com.revature.gambit.entities.UserRole;

public interface UserService {

	/**
	 * Creates a user within the database.
	 * 
	 * @author Mark Fleres
	 * @param User user
	 * @return User
	 */
	User makeUser(User user);

	
	/**
	 * Retrieves a list of all users within the Database.
	 * 
	 * @author Mark Fleres
	 * @return List<User>
	 */
	List<User> getAllUsers();

	/**
	 * Updates a user within the database.
	 * 
	 * @author Mark Fleres and Nikhil Pious
	 * @param User user
	 * @return User
	 */
	User update(User user);

	/**
	 * Retrieves a user from the database from their email.
	 * 
	 * @author Mark Fleres
	 * @param String email
	 * @return User
	 */
	User findUserByEmail(String email);

	/**
	 * Retrieves all roles a user can have from the database.
	 * 
	 * @author Mark Fleres
	 * @return List<UserRole>
	 */
	List<UserRole> getAllRoles();
	
	/**
	 * Retrieves role information using the name.
	 * 
	 * @author Mark Fleres
	 * @param String roleName
	 * @return UserRole
	 */
	UserRole findUserRoleByName(String roleName);

	/**
	 * Retrieves a user from the database from their ID.
	 * 
	 * @author Mark Fleres
	 * @param Integer id
	 * @return User
	 */
	User findUserById(Integer id);

	/**
	 * Retrieves a user from the database from their first and last name.
	 * 
	 * @author Mark Fleres
	 * @param firstName, lastName
	 * @return user
	 */
	User findByName(String firstName, String lastName);
	
	/**
	 * Gets all Users of a particular role
	 * 
	 * @author Devin Dellamano
	 * * Gambit Integrators:
	 * * @author Mark Fleres
	 * @param the UserRole we want to filter the search by
	 * @return List of Users containing all entries related to a specific role
	 */
	List<User> findByRole(UserRole role);

	/**
	 * Users are not deleted, they are simply set to inactive.
	 * 
	 * @author Mark Fleres
	 * @author Nikhil Pious
	 * @param ID of the User to set inactive.
	 * @return The deactivated user.
	 */
	User delete(Integer id);

}