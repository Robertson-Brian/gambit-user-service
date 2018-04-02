package com.revature.hydra.controllers;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.revature.hydra.entities.TrainerUser;

import io.restassured.RestAssured;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrainerControllerTest {
	Logger log = Logger.getRootLogger();
	
	@LocalServerPort
	private int port = 8909;

	@Before
	public void setUp() throws Exception {
		RestAssured.port = port;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAllUserRoles() {
		log.warn("Testing getAllUserRoles");
		get("trainers/roles")
		.then()
		.assertThat()
		.body("[0]", equalTo("Panel"))
		.body("[1]", equalTo("Staging"))
		.body("[2]", equalTo("VP"))
		.body("[3]", equalTo("INACTIVE"))
		.body("[4]", equalTo("Trainer"));
	}
	
	@Test
	public void testMakeTrainer() {
		log.warn("Testing makeTrainer");
		TrainerUser tu = new TrainerUser();
		tu.setUserId(-1);
		tu.setFirstName("fName");
		tu.setMiddleName("mName");
		tu.setLastName("lName");
		tu.setEmail("nota3@real.email");
		tu.setPassword("password");
		tu.setBackupPassword("badpassword");
		tu.setRole("VP");
		tu.setHomePhone("999-867-5309");
		tu.setMobilePhone("111-111-1111");
		tu.setToken("token");
		tu.setTitle("Lord Commander");
		tu.setTrainerId(-1);
		
		ObjectMapper om = new ObjectMapper();
		ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
		String json = "";
		
		try {
			json = ow.writeValueAsString(tu);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			fail("Failed to convert to JSON");
			e.printStackTrace();
		}
		
		given().body(json).header("Content-Type", "application/json").
		post("trainers").
		then().
		statusCode(200).
		body("userId", not(-1)).
		body("trainerId", not(-1));
		
		//fail("Not yet implemented");
	}
	
	@Test
	public void testPromote() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testUpdateTrainer() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindTrainerByEmail() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindTrainerById() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetTitles() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testDeleteByTrainerId() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetAllTrainers() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindByName() {
		fail("Not yet implemented");
	}

}
