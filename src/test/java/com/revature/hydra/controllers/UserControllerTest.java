package com.revature.hydra.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.context.embedded.LocalServerPort;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.revature.gambit.entities.User;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserControllerTest {
	Logger log = Logger.getRootLogger();
	static User u = new User();
	static String json = "";
	static ObjectMapper om = new ObjectMapper();
	static ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
	static String email = "nota@real.email";
	
	@LocalServerPort
	private static int port = 8090;
	
	@BeforeClass
	public static void prepare() throws Exception {
		RestAssured.port = port;
		u.setUserId(-1);
		u.setFirstName("fname");
		u.setMiddleName("mname");
		u.setLastName("lname");
		u.setEmail(email);
		u.setPassword("password");
		u.setBackupPassword("badpassword");
		u.setRole("VP");
		u.setHomePhone("999-867-5309");
		u.setMobilePhone("999-999-9999");
		u.setToken("newtoken");
		
		//save traineruser object as json
		try {
			json = ow.writeValueAsString(u);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			fail("Failed to convert to JSON");
			e.printStackTrace();
		}
		
		//doing post request before testing
		//if fail, no tests will run
		Response res = given().
		body(json).
		header("Content-Type", "application/json").
		post("users").
		then().
		statusCode(201).
		contentType(equalTo("application/json;charset=UTF-8")).
		body("userId", not(-1)).
		extract().
		response();
		
		//save modified objects (spring data will change the userId and trainerId)
		try {
			u = om.readValue(res.asString(), User.class);
			json = ow.writeValueAsString(u);
			}
		catch (JsonParseException e) {
			fail("Could not parse JSON");
			e.printStackTrace();
			}
		catch (JsonMappingException e) {
			fail("Could not map JSON to object");
			e.printStackTrace();
			}
		catch (IOException e) {
			fail("IO Exception");
			e.printStackTrace();
			}
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public void testCreateUser() {
//		fail("Not yet implemented");
//	}
	
	@Test
	public void testUpdateUser() {
		log.warn("Testing updateUser");
		u.setFirstName("newFirstName");
		try {
			json = ow.writeValueAsString(u);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			fail("Failed to convert to JSON");
			e.printStackTrace();
		}
		
		given().
		body(json).
		header("Content-Type", "application/json").
		put("users").
		then().
		statusCode(204);
	}
	
	@Test
	public void testFindUserByEmail() {
		log.warn("Testing findUserByEmail");
		
		given().
		get("users/email/" + email + "/").
		then().
		statusCode(200).
		contentType(equalTo("application/json;charset=UTF-8")).
		body("email", equalTo(email));
	}
	
	@Test
	public void testMakeInactive() {
		log.warn("Testing makeInactive");
		
		try {
			json = ow.writeValueAsString(u);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			fail("Failed to convert to JSON");
			e.printStackTrace();
		}
		
		given().
		body(json).
		header("Content-Type", "application/json").
		delete("users").
		then().
		statusCode(204);
	}
	
	@Test
	public void testGetAllUserRoles() {
		log.warn("Testing getAllUserRoles");
		
		given().
		get("users/roles").
		then().
		statusCode(200).
		contentType(equalTo("application/json;charset=UTF-8"));
	}
	
	@Test
	public void testGetAllUsers() {
		log.warn("Testing getAllUsers");
		
		given().
		get("users").
		then().
		statusCode(200).
		contentType(equalTo("application/json;charset=UTF-8"));
	}
	
	@Test
	public void testFindUserById() {
		log.warn("Testing getUserById");
		
		given().
		get("users/id/" + u.getUserId()).
		then().
		statusCode(200).
		contentType(equalTo("application/json;charset=UTF-8")).
		body("userId", equalTo(u.getUserId()));
	}
	
	@Test
	public void testFindByName() {
		log.warn("Testing findByName");
		
		given().
		get("users/name/" + u.getFirstName() + "/" + u.getLastName()).
		then().
		statusCode(200).
		contentType(equalTo("application/json;charset=UTF-8")).
		body("userId", equalTo(u.getUserId()));
	}

}
