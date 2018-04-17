package com.revature.gambit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.gambit.entities.User;
import com.revature.gambit.repositories.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;

	public User makeUser(User user) {
		return userRepo.save(user);
	}

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	public User update(User user) {
		return userRepo.save(user);
	}

	public User findUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	public List<String> getAllRoles() {
		return userRepo.findAllUserRoles();
	}

	public User findUserById(Integer id) {
		return userRepo.findByUserId(id);
	}

	public User findByName(String firstName, String lastName) {
		return userRepo.findUserByFirstNameAndLastName(firstName, lastName);
	}

	public void delete(Integer id) {
		User u = userRepo.getOne(id);
		u.setRole("INACTIVE");
		userRepo.save(u);
	}

}
