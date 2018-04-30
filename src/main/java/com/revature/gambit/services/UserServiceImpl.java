package com.revature.gambit.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
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
		if(findUserByEmail(user.getEmail())==null){
		return userRepository.save(user);
		}
		else{
			return null;
		}
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User update(User user) {
	if(findUserById(user.getUserId())==null){
			return null;
		}
		User updatingUser = userRepository.findByUserId(user.getUserId());
		BeanUtils.copyProperties(user, updatingUser,"userId");
		return userRepository.save(updatingUser);
	}
	
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public List<UserRole> getAllRoles() {
		return userRoleRepository.findAll();
	}

	public User findUserById(Integer id) {
		return userRepository.findByUserId(id);
	}

	public User findByName(String firstName, String lastName) {
		return userRepository.findUserByFirstNameAndLastName(firstName, lastName);
	}
	
	public User delete(Integer id) {
		User user = userRepository.findOne(id);
		user.setRole(findUserRoleByName("INACTIVE"));
		return userRepository.save(user);
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
