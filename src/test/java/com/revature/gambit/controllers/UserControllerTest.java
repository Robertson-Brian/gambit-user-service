package com.revature.gambit.controllers;

//import org.apache.log4j.Logger;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import com.revature.gambit.GambitTest;
import com.revature.gambit.entities.Trainee;
import com.revature.gambit.entities.Trainer;
import com.revature.gambit.entities.User;
import com.revature.gambit.entities.UserRole;
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
    private static final String DELETE_USER_URI = BASE_URI+"/inactive";
    private static final String FIND_ALL_USERS_URI = BASE_URI;
    private static final String FIND_ALL_USER_ROLES = BASE_URI+ "/roles";
    private static final String FIND_USER_BY_ID_URI = BASE_URI + "/{id}";
    private static final String FIND_USER_BY_NAME_URI = BASE_URI + "/name/{firstName}/{lastName}";
    private static final String FIND_ALL_TRAINERS_URI = BASE_URI;
    private static final String FIND_ALL_ASSOCIATES_URI = BASE_URI;
    
    
    /**
     * 
     * @author Nikhil
     * 
     */

	@Test 
	public void testCreateUser(){
		log.debug("Testing createUser");
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
		log.debug("Testing update");
		User targetUser = new User("James","Steve","jmsteve@hotmail.com");
		targetUser.setUserId(3989);
		given()
			   .contentType(ContentType.JSON)
			   .body(targetUser)
		       .when()
			   .port(port)
			   .put(UPDATE_USER_URI)
		       .then()
			   .assertThat()
			   .statusCode(HttpStatus.UNAUTHORIZED_401);
		
		targetUser=userService.findUserByEmail("patrick.walsh@revature.com");
		targetUser.setEmail("patrick@revature.com");
		given()
		       .contentType(ContentType.JSON)
		       .body(targetUser)
		       .when()
		       .port(port)
		       .put(UPDATE_USER_URI)
		       .then()
		       .assertThat()
		       .statusCode(HttpStatus.OK_200)
		       .and()
		       .contentType(ContentType.JSON)
		       .and()
		       .body("firstName",is(notNullValue()))
		       .and()
		       .body("email", equalTo("patrick@revature.com"));


	}

	@Test 
	public void testFindUserByEmail(){
		log.debug("Testing findUserByEmail");
    	String email = "howard.johnson@hotmail.com";
    	given()
    	       .when()
    	       .port(port)
    	       .get(FIND_USER_BY_EMAIL_URI, email)
    	       .then()
    	       .assertThat()
    		   .statusCode(HttpStatus.OK_200)
    		   .body("email", equalTo(email));

    }
		

	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllUserRoles(){
		log.debug("Testing gerAllUserRoles");
    	List<UserRole> userRoles = new ArrayList<>();
    	userRoles = given()
    			.port(port)
    			.basePath(FIND_ALL_USER_ROLES)
    			.when()
    			.get()
    			.then()
    			.assertThat()
    			.statusCode(HttpStatus.OK_200)
    			.extract()
    			.body()
    			.as(userRoles.getClass());

    	assertTrue(!userRoles.isEmpty());
		
	}

	@Test 
	public void testFindUserById(){
		log.debug("Testing findUserById ");
	       int userId = userService.findUserByEmail("pjw6193@hotmail.com").getUserId();
	        given()
	               .port(port)
	               .contentType(ContentType.JSON)
	               .when()
	               .get(FIND_USER_BY_ID_URI,userId)
	               .then()
	               .assertThat()
	               .statusCode(HttpStatus.OK_200)
	               .body("firstName", equalTo("Dan"),
	                     "lastName" ,equalTo("Pickles"),
	                     "email", equalTo("pjw6193@hotmail.com"),
	                     "role.role",equalTo("ROLE_VP"),
	                     "title", equalTo("Lead Trainer"));
		
	}

	@Test
	public void testFindUserByName(){
		 log.debug("Testing findUserrByName with valid user name");
		   String firstName = "Steven";
		   String lastName = "Kelsey";

		   User expected = userService.findByName(firstName, lastName);

		   given()
		   		.when()
				.port(port)
				.get(FIND_USER_BY_NAME_URI, firstName, lastName)
				.then()
				.assertThat()
				.statusCode(HttpStatus.OK_200)
				.body("firstName", equalTo(expected.getFirstName()))
				.body("lastName", equalTo(expected.getLastName()));
		
	}

	@Test
	public void testmakeUserInactive(){
		log.debug("Testng delete to make a user inactive");
		given()
		   .contentType(ContentType.JSON)
		   .body(userService.findUserByEmail("osher.y.cohen@gmail.com"))
	       .when()
		   .port(port)
		   .put(DELETE_USER_URI)
	       .then()
		   .assertThat()
		   .statusCode(HttpStatus.NO_CONTENT_204);
		
	}

	
	@SuppressWarnings("unchecked")
	@Test
	public void testAllUsers(){
		log.debug("Testng findAllusers");
    	List<User> users = new ArrayList<>();
    	users = given()
    			          .port(port)
    			          .basePath(FIND_ALL_USERS_URI)
    			          .when()
    			          .get()
    			          .then()
    			          .assertThat()
    			          .statusCode(HttpStatus.OK_200)
    			          .extract()
    			          .body()
    			          .as(users.getClass());

    	assertTrue(!users.isEmpty());
		
	}
    
	@SuppressWarnings("unchecked")
	@Test
	public void testAllTrainers(){
		log.debug("Tessting findAllTrainers");
    	List<Trainer> trainers = new ArrayList<>();
    	trainers = given()
    			          .port(port)
    			          .basePath(FIND_ALL_TRAINERS_URI)
    			          .when()
    			          .get()
    			          .then()
    			          .assertThat()
    			          .statusCode(HttpStatus.OK_200)
    			          .extract()
    			          .body()
    			          .as(trainers.getClass());

    	assertTrue(!trainers.isEmpty());
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAllAssociates(){
		log.debug("Testing findAllAssociates");
    	List<Trainee> trainees = new ArrayList<>();
    	trainees = given()
    			          .port(port)
    			          .basePath(FIND_ALL_ASSOCIATES_URI)
    			          .when()
    			          .get()
    			          .then()
    			          .assertThat()
    			          .statusCode(HttpStatus.OK_200)
    			          .extract()
    			          .body()
    			          .as(trainees.getClass());

    	assertTrue(!trainees.isEmpty());
		
	}    

}
