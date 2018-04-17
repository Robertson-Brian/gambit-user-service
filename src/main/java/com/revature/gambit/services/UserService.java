package com.revature.gambit.services;

import java.util.List;

import com.revature.gambit.entities.User;

public interface UserService {

	User makeUser(User user);

	List<User> getAllUsers();

	User update(User user);

	User findUserByEmail(String email);

	List<String> getAllRoles();

	User findUserById(Integer id);

	User findByName(String firstName, String lastName);

	/**
	 * Users are not deleted, they are simply set to inactive.
	 */
	void delete(Integer id);

}