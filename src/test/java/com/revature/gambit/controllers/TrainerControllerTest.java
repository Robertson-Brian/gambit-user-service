package com.revature.gambit.controllers;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.gambit.entities.Trainer;

import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrainerControllerTest {

	private static final Logger log = Logger.getLogger(TrainerControllerTest.class);
	
	private static final String BASE_URL = "http://localhost:10001/trainers";
	private static final String FIND_TRAINER_BY_EMAIL_URL = BASE_URL + "/email/{email:.+}/";
	private static final String FIND_ALL_TRAINER_TITLES_URL = BASE_URL + "/titles";
	private static final String FIND_ALL_TRAINERS_URL = BASE_URL + "/";

	@Autowired
	private TrainerController trainerController;
	
	@Test
	public void findTrainerByEmail() {
		when().
			get(FIND_TRAINER_BY_EMAIL_URL, "steven.kelsey@revature.com").
		then().assertThat().
			statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void testFindAllTitles() {
		log.info("Find all trainers titles at : "+FIND_ALL_TRAINER_TITLES_URL);
		when().get(FIND_ALL_TRAINER_TITLES_URL)
		      .then().assertThat().statusCode(200)
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
		                 .then().assertThat().statusCode(200)
		                 .extract().body()
		  				 .as(trainers.getClass());
																						
		assertTrue(!trainers.isEmpty());
	}
	
}