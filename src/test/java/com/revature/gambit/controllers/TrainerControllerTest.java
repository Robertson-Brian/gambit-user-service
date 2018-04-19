package com.revature.gambit.controllers;


import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.apache.log4j.Logger;
import org.junit.Before;
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

    @Autowired
    private TrainerController trainerController;
	private static final String FIND_TRAINER_BY_EMAIL_URL = BASE_URL + "/email/{email:.+}/";
	private static final String FIND_ALL_TRAINER_TITLES_URL = BASE_URL + "/titles";
    private static final String BASE_URL = "http://localhost:10001/trainers/";
	private static final String FIND_ALL_TRAINERS_URL = BASE_URL;
    private static final String REGISTER_TRAINER_URL = BASE_URL;
    private static final String DELETE_TRAINER_URL = BASE_URL + "{id}";

    private Trainer trainer1;
    private Trainer trainer2;

    @Before
    public void testCreateTrainers() {
	trainer1 = new Trainer("John", "Doe", "JohnDoe@gmail.com", "Trainer");
	trainer2 = new Trainer("Mark", "Fleres", "mfleres@gmail.com", "Doctor");
    }

    @Test
    public void testDeleteTrainer() {
	log.info("Deleting a Trainer");
	int trainerId = trainerController.registerTrainer(trainer1).getBody().getUserId();

	given().expect().when().delete(DELETE_TRAINER_URL, trainerId).then().assertThat()
		.statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testDeleteNonexistentTrainer() {
	log.info("Deleting a Trainer");
	int trainerId = -1;

	given().expect().when().delete(DELETE_TRAINER_URL, trainerId).then().assertThat()
		.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

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
		
		 when()
		.get(FIND_ALL_TRAINER_TITLES_URL)
		.then().assertThat().statusCode(HttpStatus.OK.value())
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
		                 .then().assertThat().statusCode(HttpStatus.OK.value())
		                 .extract().body()
		  				 .as(trainers.getClass());
																						
		assertTrue(!trainers.isEmpty());
	}
	
    @Test
    public void testRegisterTrainer() {

	given().contentType(ContentType.JSON).body(trainer2).when().post(REGISTER_TRAINER_URL).then().assertThat()
		.statusCode(HttpStatus.OK.value()).and().contentType(ContentType.JSON).and()
		.body("firstName", equalTo("Mark"));

	// Test that a repeat register fails (email must be unique)
	given().contentType(ContentType.JSON).body(trainer2).when().post(REGISTER_TRAINER_URL).then().assertThat()
		.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}

