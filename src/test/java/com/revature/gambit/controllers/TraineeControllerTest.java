package com.revature.gambit.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;

import com.revature.gambit.GambitTest;
import com.revature.gambit.entities.Trainee;
import com.revature.gambit.entities.TrainingStatus;
import com.revature.gambit.services.TraineeService;

import io.restassured.http.ContentType;

public class TraineeControllerTest extends GambitTest {
	
	@LocalServerPort
	private int port;
	
	private static final Logger log = Logger.getLogger(TraineeControllerTest.class);

	private static final String BASE_URI = "/trainees";
	
	@Autowired
	private TraineeService traineeService;
	
	/**
	 * Tests that trainee is successfully created.
	 * Asserts that Status Code 201 - CREATED is returned.
	 * 
	 * @author Shaleen Anwar
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
	 * Tests that a new trainee cannot be created if email already exists.
	 * Asserts that Status Code 400 - BAD REQUEST is returned.
	 * 
	 * @author Shaleen Anwar
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
	 * Tests that a new trainee canot be created if there are empty fields
	 * and that status code returned is 400.
	 */
	  @Test
	    public void saveEmptyTrainee() {
			Trainee emptyTrainee = new Trainee("","","");
			given()
			.port(port)
			.basePath(BASE_URI)
			.header("Content-Type", "application/json")
			.body(emptyTrainee)
			.when()	
			.post()
			.then()
			.assertThat()
			.statusCode(HttpStatus.BAD_REQUEST_400);
		log.trace("Trainee could not register because it was empty");
	    }

	/**
	 * Tests deletion of a trainee.
	 * Asserts that Status Code 204 - NO CONTENT is returned.
	 * 
	 * @author Joseph Arbelaez
	 */
	@Test
	public void deleteTest() {
		log.debug("TraineeControllerTest.deleteTest()");
		Trainee trainee = traineeService.findByEmail("xinguang.huang1@gmail.com");
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

	/** 
	 * Tests to see if the correct user is given,
	 * using certain emails that correspond to said user.
	 * Asserts that Status Code 200 - OK is returned.
	 * 
	 * @author Joel DeJesus
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
	
	/**
	 * Tests finding the trainee "Laut" by the given email.
	 * Asserts that Status Code 200 - OK is returned.
	 * 
	 * @author Joel DeJesus
	 */
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
	
	/**
	 * Tests finding the trainee "Chang Fatt" by the given email.
	 * Asserts that Status Code 200 - OK is returned.
	 * 
	 * @author Joel DeJesus
	 */
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
	
	/**
	 * Tests finding a nonexistent email.
	 * Asserts that Status Code 404 - NOT FOUND is returned.
	 * 
	 * @author Joel DeJesus
	 */
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
	
	/**
	 * Tests retrieving all trainees.
	 * Asserts that Status Code 200 - OK is returned.
	 * 
	 * @author Peter Alagna
	 */
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
	 * Tests finding a trainee by batch and training status.
	 * Asserts that Status Code 200 - OK is returned.
	 * 
	 * @author Brian Ethier
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
	 * Tests finding a trainee by an invalid training status.
	 * Asserts that Status Code 400 - BAD REQUEST is returned.
	 * 
	 * @author Brian Ethier
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
	 * Two Tests:
	 * First:  Tests updating a trainee's first name.
	 * 		   Asserts that Status Code 204 - NO CONTENT is returned.
	 * Second: Tests updating a nonexistent trainee.
	 * 		   Asserts that Status Code 400 - BAD REQUEST is returned.
	 * 
	 * @author Ismael Khalil
	 */
	
	@Test
	public void testUpdate() {
		log.debug("Trainee Controller test: Updating trainee's name");
		Trainee trainee = new Trainee("Larry", "Miller", "larrymiller@gmail.com");
		trainee.setUserId(13);
		trainee.getBatches().add(2);
		trainee.setFirstName("Howard");
		trainee.setLastName("Johnson");
		trainee.setEmail("howard.johnson@hotmail.com");
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
			log.trace("Updated trainee: " + trainee);
		
		log.debug("Trainee Controller test: Update a nonexistent trainee");
		Trainee nullTrainee = new Trainee("Howard", "Stern", "filler@hmail.com");
		given()
			.port(port)
			.basePath(BASE_URI)
			.header("Content-Type", "application/json")
			.when()
			.put()
			.then()
			.assertThat()
			.statusCode(HttpStatus.BAD_REQUEST_400);
			log.trace("Trainee does not exist.");
	}
	
	/**
	 * Test findByUserId method in TraineeController with a valid userId
	 */
	@Test
	public void findByUserId() {
		log.debug("Test valid findByUserId");
		Trainee trainee = new Trainee("Daniel", "Pickles", "dan.pickles@gogomail.com", "ayasn161hs9aes",
				TrainingStatus.Training, 1, "Extensure");
		trainee = traineeService.save(trainee);
		given()
			.port(port)
			.basePath(BASE_URI + "/" + trainee.getUserId())
			.when()
			.get()
			.then()
			.assertThat()
			.statusCode(HttpStatus.OK_200);
	}
	
	/**
	 * Test findByUserId method in TraineeController with an invalid userId
	 */
	@Test
	public void findByInvalidUserId() {
		log.debug("Test valid findByUserId");
		given()
			.port(port)
			.basePath(BASE_URI + "/99999999")
			.when()
			.get()
			.then()
			.assertThat()
			.statusCode(HttpStatus.NOT_FOUND_404);
	}
}