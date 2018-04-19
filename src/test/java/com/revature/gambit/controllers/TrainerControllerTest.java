package com.revature.gambit.controllers;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.revature.gambit.GambitTest;
import com.revature.gambit.entities.Trainer;

import io.restassured.http.ContentType;



public class TrainerControllerTest extends GambitTest{
	@LocalServerPort
	private int port;

	private static final Logger log = Logger.getLogger(TrainerControllerTest.class);	
	private static final String BASE_URI = "/trainers";
	private static final String FIND_TRAINER_BY_EMAIL_URI = BASE_URI + "/email/{email:.+}/";
	private static final String FIND_TRAINER_BY_ID_URI = BASE_URI + "/{id}";
	private static final String FIND_ALL_TRAINER_TITLES_URI = BASE_URI + "/titles";
	private static final String FIND_ALL_TRAINERS_URI = BASE_URI;
	private static final String REGISTER_TRAINER_URI = BASE_URI;
	
	@Test
	public void findTrainerByEmail() {
		given().port(port).
		when().
			get(FIND_TRAINER_BY_EMAIL_URI, "steven.kelsey@revature.com").
		then().assertThat().
			statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void testFindTrainerById(){
		log.debug("Testing findTrainerById ");
		given().
	    port(port).
        when().
		get(FIND_TRAINER_BY_ID_URI,2)
		.then()
		.assertThat()
		.statusCode(HttpStatus.OK.value())
		.body("firstName", equalTo("Dan"),
			 "lastName" ,equalTo("Pickles"),
			 "email", equalTo("pjw6193@hotmail.com"),
			 "role.role",equalTo("ROLE_VP"),
			"title", equalTo("Lead Trainer"));

	}
	
	
	@Test
	public void testFindAllTitles() {
		log.debug("Find all trainers titles at : "+FIND_ALL_TRAINER_TITLES_URI);
		
		given()

		.port(port)

		.basePath(FIND_ALL_TRAINER_TITLES_URI).

		 when()
		.get()
		.then().assertThat().statusCode(HttpStatus.OK.value())
		.body("$", hasItems("Lead Trainer","Vice President of Technology",
	   		                "Technology Manager","Senior Java Developer",
		    		        "Trainer","Senior Trainer")); 
		 
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllTrainers(){
		log.info("Find all trainers titles at : "+FIND_ALL_TRAINERS_URI);
		
		List<Trainer> trainers = new ArrayList<>();
		
		trainers = given()
				.port(port).when().get(FIND_ALL_TRAINERS_URI)
		                 .then().assertThat().statusCode(HttpStatus.OK.value())
		                 .extract().body()
		  				 .as(trainers.getClass());
																						
		assertTrue(!trainers.isEmpty());
	}
	
	@Test

	public void getAllTrainers() {

		log.debug("Testing getting all trainers.");

		given()

			.port(port)

			.basePath(BASE_URI)

			.when()

			.get()

			.then()

			.assertThat()

			.statusCode(HttpStatus.OK.value());

	}
	
	
    @Test
    public void testRegisterTrainer() {

		Trainer newTrainer = new Trainer("Mark","Fleres","mfleres@gmail.com","Trainer");
		given().
			contentType(ContentType.JSON).

			body(newTrainer).

		when().

			port(port).post(REGISTER_TRAINER_URI).

		then().

			assertThat().statusCode(HttpStatus.OK.value()).

		and().

			contentType(ContentType.JSON).

		and().

			body("firstName", equalTo("Mark"));

		

		//Test that a repeat register fails (email must be unique)

		given().

			contentType(ContentType.JSON).

			body(newTrainer).

		when().

			port(port).post(REGISTER_TRAINER_URI).

		then().

			assertThat().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		

		Trainer emptyTrainer = new Trainer("","","","");

		given().

			contentType(ContentType.JSON).

			body(emptyTrainer).

		when().

			port(port).post(REGISTER_TRAINER_URI).

		then().

			assertThat().statusCode(HttpStatus.BAD_REQUEST.value());

	}

}

