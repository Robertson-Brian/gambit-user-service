package com.revature.gambit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.gambit.entities.User;
import com.revature.gambit.repositories.UserRepository;

@Service
public class UserServiceAlpha implements UserService {

	@Autowired
	UserRepository userRepo;

	@Override
	public User makeUser(User user) {
		return userRepo.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User update(User user) {
		return userRepo.save(user);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public List<String> getAllRoles() {
		return userRepo.findAllUserRoles();
	}

	@Override
	public User findUserById(Integer id) {
		return userRepo.findByUserId(id);
	}

	@Override
	public User findByName(String firstName, String lastName) {
		return userRepo.findUserByFirstNameAndLastName(firstName, lastName);
	}

	@Override
	public void delete(Integer id) {
		User u = userRepo.getOne(id);
		u.setRole("INACTIVE");
		userRepo.save(u);
	}

}
