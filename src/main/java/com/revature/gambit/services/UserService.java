package com.revature.gambit.services;

import java.util.List;

import com.revature.gambit.entities.User;
import com.revature.gambit.entities.UserRole;

public interface UserService {

	User makeUser(User user);

	List<User> getAllUsers();

	User update(User user);

	User findUserByEmail(String email);

	List<String> getAllRoles();
	
	UserRole findUserRoleByName(String roleName);

	User findUserById(Integer id);

	User findByName(String firstName, String lastName);
	
	/**
	 * Gets all Users of a particular role
	 * 
	 * @author Devin Dellamano
	 * * Gambit Integrators:
	 * * @author Mark Fleres
	 * 
	 * @return List of Users containing all entries related to a specific role
	 * @param the UserRole we want to filter the search by
	 */
	List<User> findByRole(UserRole role);

	/**
	 * Users are not deleted, they are simply set to inactive.
	 */
	void delete(Integer id);

}