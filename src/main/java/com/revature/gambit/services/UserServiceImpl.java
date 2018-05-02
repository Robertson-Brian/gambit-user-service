package com.revature.gambit.services;

import static com.revature.gambit.util.MessagingUtil.TOPIC_DELETE_USER;
import static com.revature.gambit.util.MessagingUtil.TOPIC_REGISTER_USER;
import static com.revature.gambit.util.MessagingUtil.TOPIC_UPDATE_USER;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.revature.gambit.entities.User;
import com.revature.gambit.entities.UserRole;
import com.revature.gambit.messaging.Sender;
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
	List<UserRole> userRoleList;
	public void init() {
		userList= userRepository.findAll();
        userRoleList=userRoleRepository.findAll();        
        log.trace("All Users:" +userList);        
        log.trace("All UserRole:"+userRoleList);
	}
	
	@Autowired
	private Sender sender; 
  
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
  
	@HystrixCommand(fallbackMethod="getAllUsersFallBack")
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
  
	@HystrixCommand(fallbackMethod="findUserByEmailFallBack")
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	@HystrixCommand(fallbackMethod="getAllRolesFallBack")
	public List<UserRole> getAllRoles() {
		return userRoleRepository.findAll();
	}
	
	@HystrixCommand(fallbackMethod="findUserByIdFallBack")
	public User findUserById(Integer id) {
		return userRepository.findByUserId(id);
	}
	
	@HystrixCommand(fallbackMethod="findByNameFallBack")
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
	
	@HystrixCommand(fallbackMethod="findByRoleFallBack")
	public List<User> findByRole(UserRole role) {
		return userRepository.findByRole(role);
	}
	
	@HystrixCommand(fallbackMethod="findUserRoleByNameFallBack")
	public UserRole findUserRoleByName(String roleName) {
		if(roleName != null) {
			return userRoleRepository.findByRole(roleName);
		} else {
			return null;
		}
	}
	
	public List<User> getAllUsersFallBack(){
		log.info("Executing  getAllUsersFallBack");
		return userList;
	}
	
	public User findUserByEmailFallBack(String email) {
		log.info("Executing Find User By Email  Fall Back");
		return userList.stream()
				.filter(user -> email.equals(user.getEmail()))
				.findAny()
				.orElse(null);
	}

	public List<UserRole> getAllRolesFallBack() {
		log.info("Executing getAllRolesFallBack " );
		return userRoleList;
	}
	
	public User findUserByIdFallBack(Integer id){
		log.info("Executing findUserByIdFallBack " );

		return  userList.stream()
				.filter(user -> id==user.getUserId())
				.findAny()
				.orElse(null);
	}
	
	public User findByNameFallBack(String firstName, String lastName){
		log.info("Executing findByNameFallBack " );

		return userList.stream()
				.filter(user -> firstName.equals(user.getFirstName())
						&&lastName.equals(user.getLastName()))
				.findAny()
				.orElse(null);
	}
	
	public List<User> findByRoleFallBack(UserRole role){
		log.info("Executing findByRoleFallBack " );

		return userList.stream()
				.filter(user -> role.getRole()
						.equals(user.getRole().getRole()))
				.collect(Collectors.toList());
	}
	
	public UserRole findUserRoleByNameFallBack(String roleName){
		log.info("Executing findUserRoleByNameFallBack " );
           for(User users : userList){
        	  if(users.getRole().getRole().equals(roleName)){
        		  return users.getRole();
        	  }
           }
           return null;
	}
}
