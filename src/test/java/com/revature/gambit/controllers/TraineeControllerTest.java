package com.revature.gambit.controllers;

import static io.restassured.RestAssured.given;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.gambit.entities.Trainee;
import com.revature.gambit.entities.TrainingStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TraineeControllerTest {

	private static final Logger log = Logger.getLogger(TraineeControllerTest.class);

	@Autowired
	private TraineeController traineeController;

	@Test
	public void empty() {
	}

	/**
	 * This method tests updating a trainee.
	 * It asserts that a 204 request will be received, as a PUT method.
	 * @Author: Ismael Khalil
	 */
	@Test
	public void update() {
		String dummy = "{\"userId\":1900,\"firstName\":\"Johnny\",\"middleName\":null,\"lastName\":\"Chapman\",\"email\":\"chandradatgir@yahoo.com\",\"password\":null,\"backupPassword\":null,\"role\":null,\"homePhone\":null,\"mobilePhone\":null,\"token\":null,\"resourceId\":null,\"trainingStatus\":\"Dropped\",\"profileUrl\":null,\"recruiterName\":null,\"college\":null,\"degree\":null,\"major\":null,\"techScreenerName\":null,\"projectCompletion\":null,\"flagStatus\":null,\"flagNotes\":null,\"marketingStatus\":null,\"client\":null,\"endClient\":null}";
		given().header("Content-Type", "application/json")
		.body(dummy)
		.when() 
		.put("http://localhost:10001/trainees/").then().assertThat().statusCode(204);
		log.debug("Trainee Controller test: Updating trainee");
	}
}
