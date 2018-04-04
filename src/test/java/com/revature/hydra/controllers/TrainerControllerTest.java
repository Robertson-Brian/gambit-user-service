package com.revature.hydra.controllers;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.revature.hydra.entities.TrainerUser;

import io.restassured.RestAssured;
import io.restassured.response.Response;
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrainerControllerTest {
	Logger log = Logger.getRootLogger();
	static TrainerUser tu = new TrainerUser();
	static String json = "";
	static ObjectMapper om = new ObjectMapper();
	static ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
	static String email = "nota20@real.email";//must be constantly changed to obey unique constraint
	
	
	@LocalServerPort
	private static int port = 8909;

	//making a new traineruser prior to testing
	@BeforeClass
	public static void prepare() throws Exception {
		RestAssured.port = port;
		
		tu.setUserId(-1);
		tu.setFirstName("verynewfName");
		tu.setMiddleName("verynewmName");
		tu.setLastName("verynewlName");
		tu.setEmail(email);
		tu.setPassword("password");
		tu.setBackupPassword("badpassword");
		tu.setRole("VP");
		tu.setHomePhone("999-867-5309");
		tu.setMobilePhone("111-111-1111");
		tu.setToken("token");
		tu.setTitle("Senior Technical Manager");
		tu.setTrainerId(-1);
		
		//save traineruser object as json
		try {
			json = ow.writeValueAsString(tu);
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
		post("trainers").
		then().
		statusCode(200).
		contentType(equalTo("application/json;charset=UTF-8")).
		body("userId", not(-1)).
		body("trainerId", not(-1)).
		extract().
		response();
		
		//save modified objects (spring data will change the userId and trainerId)
		try {
			tu = om.readValue(res.asString(), TrainerUser.class);
			json = ow.writeValueAsString(tu);
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

	@Test
	public void testGetAllUserRoles() {
		log.warn("Testing getAllUserRoles");
		get("trainers/roles").
		then().
		statusCode(200).
		contentType(equalTo("application/json;charset=UTF-8")).
		body("[0]", equalTo("Panel")).
		body("[1]", equalTo("Staging")).
		body("[2]", equalTo("VP")).
		body("[3]", equalTo("INACTIVE")).
		body("[4]", equalTo("Trainer"));
	}

	@Test
	public void testMakeTrainer() {
		log.warn("Testing makeTrainer");
		
		TrainerUser tu2 = new TrainerUser();
		tu2.setUserId(-1);
		tu2.setFirstName("testFName");
		tu2.setMiddleName("testMName");
		tu2.setLastName("testlName");
		tu2.setEmail("test@test.test");
		tu2.setPassword("testpass");
		tu2.setBackupPassword("testbackuppass");
		tu2.setRole("VP");
		tu2.setHomePhone("999-867-5309");
		tu2.setMobilePhone("111-111-1111");
		tu2.setToken("token");
		tu2.setTitle("Senior Technical Manager");
		tu2.setTrainerId(-1);
		
		String thisJson = "";
		try {
			thisJson = ow.writeValueAsString(tu2);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			fail("Failed to convert to JSON");
			e.printStackTrace();
		}
		
		given().
		body(thisJson).
		header("Content-Type", "application/json").
		post("trainers").
		then().
		statusCode(200).
		contentType(equalTo("application/json;charset=UTF-8")).
		body("userId", not(-1)).
		body("trainerId", not(-1));
	}
	
	//left untested due to a lack of need, but kept for future batches
//	@Test
//	public void testPromote() {
//		try {
//			json = ow.writeValueAsString(tu);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			fail("Failed to convert to JSON");
//			e.printStackTrace();
//		}
//		Response res = given().body(json).header("Content-Type", "application/json").
//				post("trainers/promote").
//				then().
//				statusCode(200).
//				.extract().response();
//				
//				try {
//					tu = om.readValue(res.asString(), TrainerUser.class);
//				} catch (JsonParseException e) {
//					fail("Could not parse JSON");
//					e.printStackTrace();
//				} catch (JsonMappingException e) {
//					fail("Could not map JSON to object");
//					e.printStackTrace();
//				} catch (IOException e) {
//					fail("IO Exception");
//					e.printStackTrace();
//				}
//	}
	
	@Test
	public void testUpdateTrainer() {
		log.warn("Testing updateTrainer");
		tu.setTitle("Trainer");
		
		try {
			json = ow.writeValueAsString(tu);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			fail("Failed to convert to JSON");
			e.printStackTrace();
		}
		
		given().
		body(json).
		header("Content-Type", "application/json").
		put("trainers").
		then().
		statusCode(200).
		contentType(equalTo("application/json;charset=UTF-8")).
		body("title", equalTo("Trainer"));
	}
	
	@Test
	public void testFindTrainerByEmail() {
		log.warn("Testing findTrainerByEmail");
		
		given().
		get("trainers/email/" + email + "/").
		then().
		statusCode(200).
		contentType(equalTo("application/json;charset=UTF-8")).
		body("email", equalTo(email));
	}
	
	@Test
	public void testFindTrainerById() {
		log.warn("Testing findTrainerById");

		given().
		get("trainers/" + tu.getTrainerId()).
		then().
		statusCode(200).
		contentType(equalTo("application/json;charset=UTF-8")).
		body("trainerId", equalTo(tu.getTrainerId()));
	}
	
	@Test
	public void testGetTitles() {
		log.warn("Testing getTitles");
		
		given().
		get("trainers/titles").
		then().
		statusCode(200).
		contentType(equalTo("application/json;charset=UTF-8")).
		body("[0]", equalTo("Senior Technical Manager")).
		body("[1]", equalTo("Trainer"));
	}
	
	@Test
	public void testGetAllTrainers() {
		log.warn("Testing getAllTrainers");
		
		given().
		get("trainers").
		then().
		statusCode(200).
		contentType(equalTo("application/json;charset=UTF-8"));
	}
	
	@Test
	public void testFindByName() {
		log.warn("Testing findByName");
		
		given().
		get("trainers/name/" + tu.getFirstName() + "/" + tu.getLastName()).
		then().
		statusCode(200).
		contentType(equalTo("application/json;charset=UTF-8")).
		body("firstName", equalTo(tu.getFirstName())).
		body("lastName", equalTo(tu.getLastName()));
	}
	
	@Test
	public void testDeleteByTrainerId() {
		log.warn("Testing deleteByTrainerId");
		
		given().
		body(json).
		header("Content-Type", "application/json").
		delete("trainers/" + tu.getTrainerId()).
		then().
		statusCode(200);
	}

}
