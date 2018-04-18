package com.revature.gambit.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TraineeControllerTest {
	
	private static final Logger log = Logger.getLogger(TraineeControllerTest.class);
	
	private static final String BASE_URL = "http://localhost:10001";
	
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
	
	/**Test methods:
	 * 
	 * @see com.revature.gambit.services.TraineeServiceTest
	 */
	@Test
	public void findTraineeByEmail() {
		log.trace("Test find Howard by email.");
		String email = "howard.johnson@hotmail.com";
		String firstName= "Howard";
		given().param("email",email).when().get("http://localhost:10000/trainees/email").then().assertThat().statusCode(200).and().body("firstName",equalTo(firstName));
	}
	@Test
	public void findTraineeByEmailLaut() {
		log.trace("Test find Howard by email.");
		String email = "dlaut1@hotmail.com";
		String firstName= "Laut";
		given().param("email",email).when().get("http://localhost:10000/trainees/email").then().assertThat().statusCode(200).and().body("firstName",equalTo(firstName));
	}
	@Test
	public void findTraineeByEmailChang() {
		log.trace("Test find Chang by email.");
		String email = "kchangfatt@gmail.com";
		String firstName= "Chang Fatt";
		given().param("email",email).when().get("http://localhost:10000/trainees/email").then().assertThat().statusCode(200).and().body("firstName",equalTo(firstName));
	}
	@Test
	public void findTraineeByEmailFalse() {
		log.trace("Test null email.");
		String email = "dsgdgsdg";
		given().param("email",email).when().get("http://localhost:10000/trainees/email").then().assertThat().statusCode(404);
	}
}
