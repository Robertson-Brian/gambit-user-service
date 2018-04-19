package com.revature.gambit.controllers;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.revature.gambit.entities.Trainee;
import com.revature.gambit.entities.TrainingStatus;

import com.revature.gambit.GambitTest;

public class TraineeControllerTest extends GambitTest {
	
	@LocalServerPort
	private int port;
	
	private static final Logger log = Logger.getLogger(TraineeControllerTest.class);

	private static final String BASE_URL = "http://localhost:10001/trainees";
	
	/**
	 * Tests that trainee is created and status code is 201
	 */
	@Test
	public void save() {
		String body = "{\"userId\" : 0,\"firstName\": \"Shaleen\",\"lastName\": \"Anwar\",\"email\": \"shaleen.anwar@gmail.com\"}";
		given()
			.header("Content-Type", "application/json")
			.body(body)
			.when()	
			.post(BASE_URL).then().assertThat().statusCode(201);

	}

	/**
	 * Tests connection is OK with getAll
	 */
	@Test
	public void getAll(){
		log.debug("Testing trainee getAll");
		when()
			.get(BASE_URL)
			.then()
			.assertThat()
			.statusCode(HttpStatus.OK.value());
		log.trace("All Trainees retrieved");
	}

	/**
	 * Tests deletion of a trainee.
	 * Asserts that a 204 - No Content status is returned.
	 */
	public void deleteTest() {
		String trainee = "{\"userId\":36,"
				+ "\"firstName\":\"Gir\","
				+ "\"lastName\":\"Chandradat\","
				+ "\"email\":\"chandradatgir@yahoo.com\","
				+ "\"trainingStatus\":\"Dropped\"}";
		given()
			.header("Content-Type", "application/json")
			.body(trainee)
			.when()
			.delete(BASE_URL)
			.then()
			.assertThat()
			.statusCode(204);
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
		given()
			.param("email",email)
			.when()
			.get(BASE_URL + "/email")
			.then()
			.assertThat()
			.statusCode(HttpStatus.OK.value())
			.and()
			.body("firstName",equalTo(firstName));
	}

	@Test
	public void findTraineeByEmailLaut() {
		log.trace("Test find Howard by email.");
		String email = "dlaut1@hotmail.com";
		String firstName= "Laut";
		given()
			.param("email",email)
			.when()
			.get(BASE_URL + "/email")
			.then()
			.assertThat()
			.statusCode(HttpStatus.OK.value())
			.and()
			.body("firstName",equalTo(firstName));
	}

	@Test
	public void findTraineeByEmailChang() {
		log.trace("Test find Chang by email.");
		String email = "kchangfatt@gmail.com";
		String firstName= "Chang Fatt";
		given()
			.param("email",email)
			.when()
			.get(BASE_URL + "/email")
			.then()
			.assertThat()
			.statusCode(HttpStatus.OK.value())
			.and()
			.body("firstName",equalTo(firstName));
	}

	@Test
	public void findTraineeByEmailFalse() {
		log.trace("Test null email.");
		String email = "dsgdgsdg";
		given()
			.param("email",email)
			.when()
			.get(BASE_URL + "/email")
			.then()
			.assertThat()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	
	/**
	 * Checks that getByBatchAndStatus returns a 200 status code.
	 *  
	 */
	@Test
	public void getAllTrainees() {
		log.debug("Testing getting all trainees.");
		given()
			.port(port)
			.basePath(BASE_URL)
			.when()
			.get()
			.then()
			.assertThat()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void getByBatchAndStatus() {
		log.debug("getByBatchAndStatus unit test starts here.");
		given().when().get("http://localhost:10000/trainees/batch/1/status/Training").then().assertThat().statusCode(200);
	
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
