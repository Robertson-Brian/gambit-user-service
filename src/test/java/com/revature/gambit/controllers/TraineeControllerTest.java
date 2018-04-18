package com.revature.gambit.controllers;

import static io.restassured.RestAssured.given;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TraineeControllerTest {
	
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
		 			.delete("http://localhost:10001/trainees")
					.then().assertThat().statusCode(204);
	}
}
