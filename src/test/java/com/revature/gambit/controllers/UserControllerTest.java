package com.revature.gambit.controllers;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;

import com.revature.gambit.GambitTest;
import com.revature.gambit.services.UserService;

public class UserControllerTest extends GambitTest {
	

    private static final Logger log = Logger.getLogger(TrainerControllerTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;

    private static final String BASE_URI = "/users";

   
    
	@Test
	public void testCreateUser(){
		
	}
	
	@Test
	public void testUpdateUser(){
		
	}
	

	@Test
	public void testFindUserByEmail(){
		
	}

	@Test
	public void testGetAllUserRoles(){
		
	}

	@Test
	public void testFindUserById(){
		
	}

	@Test
	public void testFindUserByName(){
		
	}

	@Test
	public void testmakeUserInactive(){
		
	}

	@Test
	public void testFindUserByRole(){
		
	}
	
	@Test
	public void testAllUsers(){
		
	}
    
	@Test
	public void testAllTrainers(){
		
	}
	
	@Test
	public void testAllAssociates(){
		
	}    

}
