package com.revature.gambit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.gambit.entities.User;
import com.revature.gambit.entities.UserRole;
import com.revature.gambit.repositories.UserRepository;
import com.revature.gambit.repositories.UserRoleRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;

	public User makeUser(User user) {
		return userRepository.save(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User update(User user) {
		return userRepository.save(user);
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public List<UserRole> getAllRoles() {
		return userRepository.findAllUserRoles();
	}

	public User findUserById(Integer id) {
		return userRepository.findByUserId(id);
	}

	public User findByName(String firstName, String lastName) {
		return userRepository.findUserByFirstNameAndLastName(firstName, lastName);
	}

	public void delete(Integer id) {
		User u = userRepository.getOne(id);
		//Logic here should be getting the role from the DB.
		//u.setRole("INACTIVE");		
		userRepository.save(u);
	}

	public List<User> findByRole(UserRole role) {
		return userRepository.findByRole(role);
	}
	
	public UserRole findUserRoleByName(String roleName) {
		if(roleName != null) {
			return userRoleRepository.findByRole(roleName);
		} else {
			return null;
		}
	}

}
