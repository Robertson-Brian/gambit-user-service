package com.revature.gambit.services;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.revature.gambit.entities.User;
import com.revature.gambit.entities.UserRole;
import com.revature.gambit.repositories.UserRepository;
import com.revature.gambit.repositories.UserRoleRepository;

@Service("userService")
public class UserServiceImpl implements UserService {
    
	private static Logger log= Logger.getLogger(UserServiceImpl.class);
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	List<User> userList;
	public void init(){
		userList= userRepository.findAll();
		log.info("All Users:" +userList);
		
	}

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
	@HystrixCommand(fallbackMethod="findUserByEmailFallBack")
	public User findUserByEmail(String email) {
//		throw new RuntimeException();
		return userRepository.findByEmail(email);
	}
	@HystrixCommand(fallbackMethod="getAllRolesFallBack")
	public List<UserRole> getAllRoles() {
		return userRoleRepository.findAll();
	}
	@HystrixCommand(fallbackMethod="findUserByIdFallBack")
	public User findUserById(Integer id) {
//		throw new RuntimeException();
		return userRepository.findByUserId(id);
	}
	
	@HystrixCommand(fallbackMethod="findByNameFallBack")
	public User findByName(String firstName, String lastName) {
//		throw new RuntimeException();

		return userRepository.findUserByFirstNameAndLastName(firstName, lastName);
	}
	
	public User delete(Integer id) {
		User user = userRepository.findOne(id);
		user.setRole(findUserRoleByName("INACTIVE"));
		return userRepository.save(user);
	}
	
//	@HystrixCommand(fallbackMethod="findByRoleFallBack")
	public List<User> findByRole(UserRole role) {
		return userRepository.findByRole(role);
	}
	
//	@HystrixCommand(fallbackMethod="findUserRoleByNameFallBack")
	public UserRole findUserRoleByName(String roleName) {
		if(roleName != null) {
			return userRoleRepository.findByRole(roleName);
		} else {
			return null;
		}
	}
	
	
	public User findUserByEmailFallBack(String email) {
		return userList.stream()
				.filter(user -> email.equals(user.getEmail()))
				.findAny()
				.orElse(null);
	}

	public List<UserRole> getAllRolesFallBack() {
		return  userRoleRepository.findAll();
	}
	
	public User findUserByIdFallBack(Integer id){
		return  userList.stream()
				.filter(user -> id==user.getUserId())
				.findAny()
				.orElse(null);
	}
	
	public User findByNameFallBack(String firstName, String lastName){
		return userList.stream()
				.filter(user -> firstName.equals(user.getFirstName())
						&&lastName.equals(user.getLastName()))
				.findAny()
				.orElse(null);
	}
	
/*	public List<User> findByRoleFallBack(UserRole role){
		return userList.stream()
				.filter(user -> role.getRole()
						.equals(user.getRole().getRole()))
				.collect(Collectors.toList());
	}
	
	public UserRole findUserRoleByNameFallBack(String roleName){
		return null;
		
	}*/
}
