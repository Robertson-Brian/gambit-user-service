package com.revature.gambit.services;

import static com.revature.gambit.util.MessagingUtil.TOPIC_DELETE_USER;
import static com.revature.gambit.util.MessagingUtil.TOPIC_REGISTER_USER;
import static com.revature.gambit.util.MessagingUtil.TOPIC_UPDATE_USER;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.revature.gambit.entities.User;
import com.revature.gambit.entities.UserRole;
import com.revature.gambit.messaging.KafkaTest;

public class UserServiceTest extends KafkaTest {
	
	private static Logger log = Logger.getLogger(UserServiceTest.class);
	
	@Autowired
	private UserService userService;
	private User newUser = new User("John", "Steve", "jsteve@gmail.com");
	
	
	/**
     * Test to create a user successfully.
	 *@author Nikhil	
     */
	@Test
	public void testMakeUser(){
		log.debug("Testing UserService makeUser()");
    	User savedUser = userService.makeUser(newUser);
    	log.trace("savedTrainer = " + savedUser);
    	
    	//Kafka Test
    	User kafkaUser = (User) receive(TOPIC_REGISTER_USER, User.class);
    	assertNotNull(kafkaUser);
    	assertEquals(savedUser.getUserId(),kafkaUser.getUserId());
    	
    	assertNotEquals(0, savedUser.getUserId());
    	assertEquals(newUser.getFirstName(), savedUser.getFirstName());
    	assertNull(userService.makeUser(newUser));
	}
	
	/**
     * Test to get all users
	 *@author Nikhil
     */
	@Test
	public void testGetAllUsers(){
		log.debug("Testing UserService getAllUsers()");
    	List<User> listTrainer = userService.getAllUsers();
    	assertTrue(listTrainer.size()>0);
		for(User user : listTrainer){
			assertEquals(user, userService.findUserByEmail(user.getEmail()));
		}
	}
	
	/**
     * Test to update a trainer
	 *@author Nikhil
     */
	@Test
	public void testUpdate(){
		log.debug("Testing UserService update()");
    	User savedUser = userService.makeUser(newUser);
    	log.trace("savedUser = " + savedUser);
    	assertNotEquals(0, savedUser.getUserId());
    	User targetUser = userService.findUserById(userService.findByName("John", "Steve").getUserId());
		log.trace("targetUser ="+targetUser);
		assertEquals("John",targetUser.getFirstName());
		targetUser.setEmail("jj42@hotmail.com");
		targetUser.setFirstName("Mathew");
		User updateTargetUser =userService.update(targetUser);
		
		//Kafka Test
    	User kafkaUser = (User) receive(TOPIC_UPDATE_USER, User.class);
    	assertNotNull(kafkaUser);
    	assertEquals(updateTargetUser.getUserId(),kafkaUser.getUserId());
		
		List<String> updatedList = Arrays.asList("Mathew","Steve","jj42@hotmail.com");
		assertThat(updatedList,CoreMatchers.hasItems(updateTargetUser.getFirstName(),updateTargetUser.getLastName()));	
	}
		
  	/**
     *Test to find a user by his/her email.
	 *@author Nikhil
     */
	@Test
	public void testFindUserByEmail(){
		
		log.debug("Testing UserService findUserByEmail()");
		String expected = "wingz101@icloud.com";
		User user = userService.findUserByEmail("wingz101@icloud.com");
		assertEquals(user.getEmail(), expected);
		String notExpected ="xinguang.huang1@gmail.com";
		assertThat(notExpected,is(not(user.getEmail())));
	}

	/**
     * Test to retrieve all user roles
	 *@author Nikhil
     */
	@Test
	public void testGetAllRoles(){
		log.debug("Testing UserService getAllRoles()");
    	List<UserRole> listRole = userService.getAllRoles();
    	assertTrue(!listRole.isEmpty());
	}
	
	/**
     * Test to find a user by his/her user id.
	 *@author Nikhil
     */
	@Test
	public void testFindUserById(){
		 log.debug("Testing UserService findUserById()");
	        User findUserById = userService.findUserById(userService.makeUser(newUser).getUserId());
	        assertEquals(newUser.getFirstName(), findUserById.getFirstName());
	        assertEquals(newUser.getEmail(), findUserById.getEmail());
	}
	
	 /**
     * Test to find a user by his/her username.
	 *@author Nikhil
     */
	@Test
	public void testFindByName(){
		log.debug("Testing UserService findUserByName");
		String expected = "Patrick";
		User user = userService.findByName("Patrick", "Walsh");
		assertEquals(expected,user.getFirstName());
	}
	
	/**
     * Test to delete(make inactive) a user.
	 *@author Nikhil
	 *@author Mark Fleres
     */
	@Test
	public void testDelete(){
    	log.debug("Testing UserService delete()");
    	
    	//Make a new User in the DB (Not a subclass of User)
    	User user = userService.makeUser(newUser);
    	assertNotNull(user);
    	
    	//Delete the user
    	User inactiveUser = userService.delete(user.getUserId());
    	
    	//Kafka Test
    	User kafkaUser = (User) receive(TOPIC_DELETE_USER, User.class);
    	assertNotNull(kafkaUser);
    	assertEquals(inactiveUser.getUserId(),kafkaUser.getUserId());
    	
    	assertEquals("INACTIVE",inactiveUser.getRole().getRole());
		
	}
	
 	/**
     * Test to find user role by name.
	 *@author Nikhil
     */
	@Test
	public void testFindByRoleName(){
	log.debug("Testing UserService findByRoleName()");
	Integer expected =2;
	UserRole userRole = userService.findUserRoleByName("ROLE_QC");
	assertNotNull(userRole);
	assertEquals(expected,userRole .getRoleId());
	assertNull(userService.findUserRoleByName("NOT_AN_EXISITING_ROLE"));
	}
	
	/**
     * Test to retrieve a user list with a specific user role.
	 *@author Nikhil
     */
	@Test
	public void findByRole(){
	log.debug("Testing UserService findByRole()");
    List<User> listUser = userService.findByRole(userService.findUserRoleByName("ROLE_TRAINER"));
    assertTrue(listUser.size()>0);
    assertTrue(listUser.get(5).getEmail().equals("emily.higgins@revature.com"));
    for(User user: listUser){
    assertEquals("ROLE_TRAINER",user.getRole().getRole());
   	  } 
	}
}
