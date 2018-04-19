package com.revature.gambit.controllers;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.revature.gambit.GambitTest;

public class TraineeControllerTest extends GambitTest {
	
	@LocalServerPort
	private int port;
	
	private static final Logger log = Logger.getLogger(TraineeControllerTest.class);

	private static final String BASE_URI = "/trainees";
	
	/**
	 * Tests that trainee is created and status code is 201
	 */
	@Test
	public void save() {
		log.debug("Testing Trainee Insert");
		String body = "{\"userId\" : 0,"
				+ "\"firstName\": \"Shaleen\","
				+ "\"lastName\": \"Anwar\","
				+ "\"email\": \"shaleen.anwar@gmail.com\"}";
		given()
			.port(port)
			.basePath(BASE_URI)
			.header("Content-Type", "application/json")
			.body(body)
			.when()	
			.post()
			.then()
			.assertThat()
			.statusCode(HttpStatus.CREATED.value());
		log.trace("New trainee created");
	}

	/**
	 * Tests connection is OK with getAll
	 */
	@Test
	public void getAll(){
		log.debug("Testing trainee getAll");
		given()
			.port(port)
			.basePath(BASE_URI)
			.when()
			.get()
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
			.port(port)
			.basePath(BASE_URI)
			.header("Content-Type", "application/json")
			.body(trainee)
			.when()
			.delete()
			.then()
			.assertThat()
			.statusCode(HttpStatus.NO_CONTENT.value());
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
			.port(port)
			.basePath(BASE_URI + "/email")
			.param("email",email)
			.when()
			.get()
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
			.port(port)
			.basePath(BASE_URI + "/email")
			.param("email",email)
			.when()
			.get()
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
			.port(port)
			.basePath(BASE_URI + "/email")
			.param("email", email)
			.when()
			.get()
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
			.port(port)
			.basePath(BASE_URI + "/email")
			.param("email", email)
			.when()
			.get()
			.then()
			.assertThat()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void getAllTrainees() {
		log.debug("Testing getting all trainees.");
		given()
			.port(port)
			.basePath(BASE_URI)
			.when()
			.get()
			.then()
			.assertThat()
			.statusCode(HttpStatus.OK.value());
	}

	/**
	 * Checks that getByBatchAndStatus returns a 200 status code.
	 *  
	 */
	@Test
	public void getByBatchAndStatus() {
		log.debug("getByBatchAndStatus unit test starts here.");
		given()
			.port(port)
			.basePath(BASE_URI + "/batch/1/status/Training")
			.when()
			.get()
			.then()
			.assertThat()
			.statusCode(HttpStatus.OK.value());
	}

	/**
	 * This method tests updating a trainee.
	 * It asserts that a 204 request will be received, as a PUT method.
	 * @Author: Ismael Khalil
	 */
	@Test
	public void update() {
		log.debug("Trainee Controller test: Updating trainee");
		String trainee = "{\"userId\":1900,"
				+ "\"firstName\":\"Johnny\","
				+ "\"lastName\":\"Chapman\","
				+ "\"email\":\"chandradatgir@yahoo.com\","
				+ "\"trainingStatus\":\"Dropped\"}";
		given()
			.port(port)
			.basePath(BASE_URI)
			.header("Content-Type", "application/json")
			.body(trainee)
			.when()
			.put()
			.then()
			.assertThat()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}
}
