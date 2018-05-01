package com.revature.gambit.services;

import static com.revature.gambit.util.MessagingUtil.TOPIC_DELETE_USER;
import static com.revature.gambit.util.MessagingUtil.TOPIC_REGISTER_USER;
import static com.revature.gambit.util.MessagingUtil.TOPIC_UPDATE_USER;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.gambit.entities.User;
import com.revature.gambit.entities.UserRole;
import com.revature.gambit.messaging.Sender;
import com.revature.gambit.repositories.UserRepository;
import com.revature.gambit.repositories.UserRoleRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Autowired
	private Sender sender; // Use this to send messages to other services

	public User makeUser(User user) {
		if(findUserByEmail(user.getEmail())==null){
			User savedUser = userRepository.save(user);
			if(savedUser != null) {
				sender.publish(TOPIC_REGISTER_USER, savedUser);
			}
			return savedUser;
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
		User updatedUser = userRepository.save(updatingUser);
		if(updatedUser != null) {
			sender.publish(TOPIC_UPDATE_USER, updatedUser);
		}
		return updatedUser;
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
		User user = userRepository.findByUserId(id);
		user.setRole(findUserRoleByName("INACTIVE"));
		User inactivatedUser = userRepository.save(user);
		if(inactivatedUser != null) {
			sender.publish(TOPIC_DELETE_USER, inactivatedUser);
		}
		return inactivatedUser;
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
