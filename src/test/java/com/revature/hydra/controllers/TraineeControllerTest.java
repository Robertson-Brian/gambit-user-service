/**
 * TODO implement testing
 */
package com.revature.hydra.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.context.embedded.LocalServerPort;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.revature.gambit.entities.Trainee;
import com.revature.gambit.entities.TraineeBatch;
import com.revature.gambit.entities.User;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TraineeControllerTest {
	Logger log = Logger.getRootLogger();
	static Trainee tr = new Trainee();
	static User u = new User();
	static String json = "";
	static ObjectMapper om = new ObjectMapper();
	static ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
	static List<TraineeBatch> batchList;
	static String email = "nota20@real.email";
	@LocalServerPort
	private static int port = 8090;

	@BeforeClass
	public static void prepare() throws Exception {
		RestAssured.port = port;

		tr.setTraineeId(-1);
		batchList= new ArrayList<TraineeBatch>();
		batchList.add(new TraineeBatch(0,0));
		tr.setBatches(batchList);
		tr.setResourceId(0);
		tr.setTrainingStatus("Dropped");
		tr.setPhoneNumber("999-867-5309");
		tr.setSkypeId("lord_commander");
		tr.setProfileUrl("notareal.website.io");
		tr.setRecruiterName("Hugh");
		tr.setCollege("Fake College");
		tr.setDegree("Fake Degree");
		tr.setMajor("Colonial European Arts");
		tr.setTechScreenerName("Jane");
		tr.setProjectCompletion("Incomplete");
		tr.setFlagStatus("Red");
		tr.setFlagNotes("Really bad, I sweah");
		tr.setGrades("grades");
		tr.setNotes("so bad");
		tr.setPanelInterviews("no really is this guy fired yet");
		tr.setMarketingStatus("Unmarketable");
		tr.setClient("No one");
		tr.setEndClient("Someone take him off our hands");
		u.setUserId(-1);
		u.setFirstName("ooofname");
		u.setMiddleName("ooomname");
		u.setLastName("ooolname");
		u.setEmail(email);
		u.setPassword("password");
		u.setBackupPassword("badpassword");
		u.setRole("VP");
		u.setHomePhone("999-867-5309");
		u.setMobilePhone("999-999-9999");
		u.setToken("newtoken");
		tr.setTraineeUserInfo(u);

		// save traineruser object as json
		try {
			json = ow.writeValueAsString(tr);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			fail("Failed to convert to JSON");
			e.printStackTrace();
		}

		// doing post request before testing
		// if fail, no tests will run
		Response res = given().body(json).header("Content-Type", "application/json").post("trainees").then()
				.statusCode(201).contentType(equalTo("application/json;charset=UTF-8")).body("traineeId", not(-1))
				.extract().response();

		// save modified objects (spring data will change the userId and trainerId)
		try {
			tr = om.readValue(res.asString(), Trainee.class);
			json = ow.writeValueAsString(tr);
		} catch (JsonParseException e) {
			fail("Could not parse JSON");
			e.printStackTrace();
		} catch (JsonMappingException e) {
			fail("Could not map JSON to object");
			e.printStackTrace();
		} catch (IOException e) {
			fail("IO Exception");
			e.printStackTrace();
		}
	}

	// @Before
	// public void setUp() throws Exception {
	// }
	//
	// @After
	// public void tearDown() throws Exception {
	// }

	// Returns a specific Trainee given a specific Batch and Status
	@Test
	public void testFindAllByBatchAndStatus() {
		log.warn("Testing findAllByBatchAndStatus");

		given().body(json).header("Content-Type", "application/json")
				.get("trainees/batch/" + tr.getBatches().get(0).getBatch_id() + "/status/" + tr.getTrainingStatus())
				.then().statusCode(200).body("[0]", notNullValue());
	}

	// Returns a List of all Trainees
	@Test
	public void testGetAll() {
		log.warn("Testing getAll");

		given().body(json).header("Content-Type", "application/json").get("trainees").then().statusCode(200).body("[0]", notNullValue());
	}

	// Creates a new trainee
	@Test
	public void testCreateTrainee() {
		log.warn("Testing createTrainee");

		given().body(json).header("Content-Type", "application/json").post("trainees").then().statusCode(201).body("userId", not(-1));
	}

	// Updates Trainee information
	@Test
	public void testUpdateTrainee() {
		log.warn("Testing updateTrainee");

		given().body(json).header("Content-Type", "application/json").put("trainees").then().statusCode(204);

	}

	// Deletes Trainee information
	@Test
	public void testDeleteTrainee() {
		log.warn("Testing deleteTrainee");

		given().body(json).header("Content-Type", "application/json").delete("trainees/" + tr.getTraineeId()).then().statusCode(204);

	}

}
