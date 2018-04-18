package com.revature.gambit.controllers;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.HttpStatus;
import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TraineeControllerTest {

	private static final Logger logger = Logger.getLogger(TraineeControllerTest.class);
	private static final String BASE_URL = "http://localhost:10001";
	private static final String DELETE_TRAINEE = BASE_URL + "/trainees";
	
	@Autowired
	private TraineeController traineeController;
	
	/**
	 * Tests deletion of a trainee.
	 * Asserts that a 204 - No Content status is returned.
	 */
	@Test
	public void deleteTest() {
		String trainee = "{\"userId\":36,\"firstName\":\"Gir\",\"middleName\":null,\"lastName\":\"Chandradat\",\"email\":\"chandradatgir@yahoo.com\",\"password\":null,\"backupPassword\":null,\"role\":null,\"homePhone\":null,\"mobilePhone\":null,\"token\":null,\"resourceId\":null,\"trainingStatus\":\"Dropped\",\"profileUrl\":null,\"recruiterName\":null,\"college\":null,\"degree\":null,\"major\":null,\"techScreenerName\":null,\"projectCompletion\":null,\"flagStatus\":null,\"flagNotes\":null,\"marketingStatus\":null,\"client\":null,\"endClient\":null}";
		given()
			.header("Content-Type", "application/json")
			.body(trainee)
		 		.when()
		 			.delete(DELETE_TRAINEE)
					.then().assertThat().statusCode(204);
	}
}
