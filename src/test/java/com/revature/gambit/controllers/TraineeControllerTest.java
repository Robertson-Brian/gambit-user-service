package com.revature.gambit.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import org.springframework.boot.context.embedded.LocalServerPort;

import com.revature.gambit.GambitTest;
import com.revature.gambit.entities.Trainee;

public class TraineeControllerTest extends GambitTest {
	
	@LocalServerPort
	private int port;
	
	private static final Logger log = Logger.getLogger(TraineeControllerTest.class);

	private static final String BASE_URI = "/trainees";
	
	/**
	 * Tests that trainee is created and status code returned is 201.
	 */
	@Test
	public void save() {
		log.debug("Testing Trainee Insert");
		Trainee trainee = new Trainee("John", "Smith", "example@gmail.com");
		given()
			.port(port)
			.basePath(BASE_URI)
			.header("Content-Type", "application/json")
			.body(trainee)
			.when()	
			.post()
			.then()
			.assertThat()
			.statusCode(HttpStatus.CREATED_201);
		log.trace("New trainee created");
	}
	
	/**
	 * Tests that a new trainee cannot be created if email already exists
	 * and that status code returned is 400.
	 */
	@Test
	public void saveDuplicate() {
		log.debug("Testing that Trainee cannot register if email already exists");
		Trainee trainee = new Trainee("Howard","Johnson","howard.johnson@hotmail.com");
		given()
			.port(port)
			.basePath(BASE_URI)
			.header("Content-Type", "application/json")
			.body(trainee)
			.when()	
			.post()
			.then()
			.assertThat()
			.statusCode(HttpStatus.BAD_REQUEST_400);
		log.trace("Trainee could not register because of existing email");
	}

	/**
	 * Tests deletion of a trainee.
	 * Asserts that a 204 - No Content status is returned.
	 */
	public void deleteTest() {
		log.debug("TraineeControllerTest.deleteTest()");
		Trainee trainee = new Trainee("Gir", "Chandradat", "chandradatgir@yahoo.com");
		trainee.setUserId(36);
		given()
			.port(port)
			.basePath(BASE_URI)
			.header("Content-Type", "application/json")
			.body(trainee)
			.when()
			.delete()
			.then()
			.assertThat()
			.statusCode(HttpStatus.NO_CONTENT_204);
	}

	/**Test methods:
	 * 
	 * @see com.revature.gambit.services.TraineeServiceTest
	 * Tests to see if the correct user is given with certain emails that correspond to said user
	 */
	@Test
	public void findTraineeByEmail() {
		log.debug("Test find Howard by email.");
		String email = "howard.johnson@hotmail.com";
		String firstName = "Howard";
		given()
			.port(port)
			.basePath(BASE_URI + "/email")
			.param("email",email)
			.when()
			.get()
			.then()
			.assertThat()
			.statusCode(HttpStatus.OK_200)
			.and()
			.body("firstName",equalTo(firstName));
	}

	@Test
	public void findTraineeByEmailLaut() {
		log.debug("Test find Howard by email.");
		String email = "dlaut1@hotmail.com";
		String firstName = "Laut";
		given()
			.port(port)
			.basePath(BASE_URI + "/email")
			.param("email",email)
			.when()
			.get()
			.then()
			.assertThat()
			.statusCode(HttpStatus.OK_200)
			.and()
			.body("firstName",equalTo(firstName));
	}

	@Test
	public void findTraineeByEmailChang() {
		log.debug("Test find Chang by email.");
		String email = "kchangfatt@gmail.com";
		String firstName = "Chang Fatt";
		given()
			.port(port)
			.basePath(BASE_URI + "/email")
			.param("email", email)
			.when()
			.get()
			.then()
			.assertThat()
			.statusCode(HttpStatus.OK_200)
			.and()
			.body("firstName",equalTo(firstName));
	}

	@Test
	public void findTraineeByEmailFalse() {
		log.debug("Test null email.");
		String email = "dsgdgsdg";
		given()
			.port(port)
			.basePath(BASE_URI + "/email")
			.param("email", email)
			.when()
			.get()
			.then()
			.assertThat()
			.statusCode(HttpStatus.NOT_FOUND_404);
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
			.statusCode(HttpStatus.OK_200);
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
			.statusCode(HttpStatus.OK_200);
	}
	
	/**
	 * Checks that when you send an invalid training status
	 * bad request gets returned.
	 */
	@Test
	public void getByBatchAndBadStatus() {
		log.debug("getByBatchAndStatus unit test starts here.");
		given()
			.port(port)
			.basePath(BASE_URI + "/batch/1/status/Train")
			.when()
			.get()
			.then()
			.assertThat()
			.statusCode(HttpStatus.BAD_REQUEST_400);
	}

	/**
	 * This method tests updating a trainee.
	 * It asserts that a 204 request will be received, as a PUT method.
	 * 
	 */
	@Test
	public void update() {
		log.debug("Trainee Controller test: Updating trainee");
		Trainee trainee = new Trainee("Howard", "Johnson", "howard.johnson@hotmail.com");
		trainee.setUserId(13);
		trainee.getBatches().add(2);
		trainee.setFirstName("John");
		given()
			.port(port)
			.basePath(BASE_URI)
			.header("Content-Type", "application/json")
			.body(trainee)
			.when()
			.put()
			.then()
			.assertThat()
			.statusCode(HttpStatus.NO_CONTENT_204);
	}

	/**
	 * This method will attempt to return a nonexistent employee,
	 * and return 
	 * 
	 */
	@Test
	public void updateNull() {
		log.debug("Trainee Controller test: Update a nonexistent employee");
		Trainee trainee = new Trainee("Howard", "Stern", "filler@hmail.com");
		given()
			.port(port)
			.basePath(BASE_URI)
			.header("Content-Type", "application/json")
			.when()
			.put()
			.then()
			.assertThat()
			.statusCode(HttpStatus.BAD_REQUEST_400);
	}
}