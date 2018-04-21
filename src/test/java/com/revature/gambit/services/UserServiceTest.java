package com.revature.gambit.services;

import static org.hamcrest.CoreMatchers.hasItems;
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
import com.revature.gambit.GambitTest;
import com.revature.gambit.entities.User;
import com.revature.gambit.entities.UserRole;

public class UserServiceTest extends GambitTest {
	
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
    	assertNotEquals(0, savedUser.getUserId());
    	assertEquals(newUser.getFirstName(), savedUser.getFirstName());
    	//*************** delete below statement if you think everything is fine**********//
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
		String expected = "patrick.walsh@revature.com";
		User user = userService.findUserByEmail("patrick.walsh@revature.com");
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
		int expected=3;
    	List<UserRole> listRole = userService.getAllRoles();
    	assertEquals(expected, listRole.size());
    	assertThat(Arrays.asList("ROLE_VP","ROLE_QC","ROLE_TRAINER"),hasItems(listRole.get(0).getRole(),listRole.get(1).getRole(),listRole.get(2).getRole()));;

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
     */
	@Test
	public void testDelete(){
    	log.debug("Testing UserService delete()");
    	assertNotNull(userService.findUserByEmail("wingz101@icloud.com"));
    	//User user= userService.findUserByEmail("wingz101@icloud.com");
    	User user2 = userService.makeUser(newUser);
    	//userService.delete(user2.getUserId());
    	userService.delete(user2.getUserId());
    	//userService.delete(1);
    	assertNull(userService.findUserByEmail("wingz101@icloud.com"));
		
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
