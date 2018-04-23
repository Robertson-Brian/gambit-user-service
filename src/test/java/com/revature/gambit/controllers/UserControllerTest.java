package com.revature.gambit.controllers;

//import org.apache.log4j.Logger;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.gambit.GambitTest;
import com.revature.gambit.entities.Trainer;
import com.revature.gambit.entities.User;
import com.revature.gambit.services.TrainerService;
import com.revature.gambit.services.UserService;

import io.restassured.http.ContentType;


public class UserControllerTest extends GambitTest {
	

    private static final Logger log = Logger.getLogger(UserControllerTest.class);
    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;
    
	private User newUser = new User("John", "Steve", "jsteve@gmail.com");


    private static final String BASE_URI = "/users";
    private static final String REGISTER_USER_URI = BASE_URI;
    private static final String UPDATE_USER_URI = BASE_URI;
    private static final String FIND_USER_BY_EMAIL_URI = BASE_URI + "/email/{email:.+}/";
    private static final String DELETE_USER_URI = BASE_URI+"/{inactive}";
    private static final String FIND_ALL_USERS_URI = BASE_URI;
    private static final String FIND_ALL_USER_ROLES = BASE_URI+"/{roles}";
    private static final String FIND_USER_BY_ID_URI = BASE_URI + "/{id}";
    private static final String FIND_USER_BY_NAME_URI = BASE_URI + "/name/{firstName}/{lastName}";
    private static final String FIND_ALL_TRAINERS_URI = BASE_URI;
    private static final String FIND_ALL_ASSOCIATES_URI = BASE_URI;
    

	@Test
	public void testCreateUser(){
		given()
	       .contentType(ContentType.JSON)
	       .body(newUser)
	       .when()
	       .port(port)
	       .post(REGISTER_USER_URI)
	       .then()
		   .assertThat()
		   .statusCode(HttpStatus.CREATED_201)
		   .and()
		   .contentType(ContentType.JSON)
		   .and()
		   .body("firstName", equalTo("John"));
		
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
