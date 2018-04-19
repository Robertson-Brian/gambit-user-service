package com.revature.gambit.controllers;


import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import org.springframework.boot.context.embedded.LocalServerPort;

import com.revature.gambit.GambitTest;
import com.revature.gambit.entities.Trainer;

import io.restassured.http.ContentType;

public class TrainerControllerTest extends GambitTest {

	@LocalServerPort
	private int port;
	
	private static final Logger log = Logger.getLogger(TrainerControllerTest.class);
	
	private static final String BASE_URL = "/trainers";
	private static final String FIND_TRAINER_BY_EMAIL_URL = BASE_URL + "/email/{email:.+}/";
	private static final String FIND_ALL_TRAINER_TITLES_URL = BASE_URL + "/titles";
	private static final String FIND_ALL_TRAINERS_URL = BASE_URL;
	private static final String REGISTER_TRAINER_URL = BASE_URL;
	
	@Test
	public void findTrainerByEmail() {
		when().
			get(FIND_TRAINER_BY_EMAIL_URL, "steven.kelsey@revature.com").
		then().assertThat().
			statusCode(HttpStatus.OK_200);
	}
	
	@Test
	public void testFindAllTitles() {
		log.info("Find all trainers titles at : "+FIND_ALL_TRAINER_TITLES_URL);
		
		 when()
		.get(FIND_ALL_TRAINER_TITLES_URL)
		.then().assertThat().statusCode(HttpStatus.OK_200)
		.body("$", hasItems("Lead Trainer","Vice President of Technology",
	   		                "Technology Manager","Senior Java Developer",
		    		        "Trainer","Senior Trainer")); 
		 
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllTrainers(){
		log.info("Find all trainers titles at : "+FIND_ALL_TRAINERS_URL);
		
		List<Trainer> trainers = new ArrayList<>();
		
		trainers = when().get(FIND_ALL_TRAINERS_URL)
		                 .then().assertThat().statusCode(HttpStatus.OK_200)
		                 .extract().body()
		  				 .as(trainers.getClass());
																						
		assertTrue(!trainers.isEmpty());
	}
	
    @Test
	public void testRegisterTrainer() {
		Trainer newTrainer = new Trainer("Mark","Fleres","mfleres@gmail.com","Trainer");
		
		given().
			contentType(ContentType.JSON).
			body(newTrainer).
		when().
			port(port).post(REGISTER_TRAINER_URL).
		then().
			assertThat().statusCode(HttpStatus.OK_200).
		and().
			contentType(ContentType.JSON).
		and().
			body("firstName", equalTo("Mark"));
		
		//Test that a repeat register fails (email must be unique)
		given().
			contentType(ContentType.JSON).
			body(newTrainer).
		when().
			port(port).post(REGISTER_TRAINER_URL).
		then().
			assertThat().statusCode(HttpStatus.INTERNAL_SERVER_ERROR_500);
		
		Trainer emptyTrainer = new Trainer("","","","");
		given().
			contentType(ContentType.JSON).
			body(emptyTrainer).
		when().
			port(port).post(REGISTER_TRAINER_URL).
		then().
			assertThat().statusCode(HttpStatus.BAD_REQUEST_400);
	}
}

