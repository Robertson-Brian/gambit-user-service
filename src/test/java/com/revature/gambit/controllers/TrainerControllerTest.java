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

public class TrainerControllerTest extends GambitTest {

    private static final Logger log = Logger.getLogger(TrainerControllerTest.class);

    @LocalServerPort
    private int port;

    private static final String BASE_URL = "http://localhost:10001/trainers/";
    private static final String BASE_URI = "/trainers";
    private static final String FIND_TRAINER_BY_EMAIL_URL = BASE_URL + "/email/{email:.+}/";
    private static final String FIND_ALL_TRAINER_TITLES_URL = BASE_URL + "/titles";
    private static final String FIND_ALL_TRAINERS_URL = BASE_URL;
    private static final String REGISTER_TRAINER_URL = BASE_URL;

    @Test
    public void testDeleteTrainer() {
	log.info("Deleting a Trainer");
	char trainerId = 1;
	given().port(port).delete(BASE_URI + "/{id}", trainerId).then().assertThat().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testDeleteNonexistentTrainer() {
	log.info("Deleting a Trainer");
	short trainerId = -1;
	given().port(port).delete(BASE_URI + "/{id}", trainerId).then().assertThat()
		.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    public void testDeleteNullTrainer() {
	log.info("Deleting a Trainer");
	given().port(port).delete(BASE_URI + "/{id}", ).then().assertThat()
		.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    public void findTrainerByEmail() {
	when().get(FIND_TRAINER_BY_EMAIL_URL, "steven.kelsey@revature.com").then().assertThat()
		.statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testFindAllTitles() {
	log.info("Find all trainers titles at : " + FIND_ALL_TRAINER_TITLES_URL);

	when().get(FIND_ALL_TRAINER_TITLES_URL).then().assertThat().statusCode(HttpStatus.OK.value()).body("$",
		hasItems("Lead Trainer", "Vice President of Technology", "Technology Manager", "Senior Java Developer",
			"Trainer", "Senior Trainer"));

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFindAllTrainers() {
	log.info("Find all trainers titles at : " + FIND_ALL_TRAINERS_URL);

	List<Trainer> trainers = new ArrayList<>();

	trainers = when().get(FIND_ALL_TRAINERS_URL).then().assertThat().statusCode(HttpStatus.OK.value()).extract()
		.body().as(trainers.getClass());

	assertTrue(!trainers.isEmpty());
    }

    @Test
    public void testRegisterTrainer() {
	Trainer newTrainer = new Trainer("Mark", "Fleres", "mfleres@gmail.com", "Trainer");

	given().contentType(ContentType.JSON).body(newTrainer).when().port(port).post(REGISTER_TRAINER_URL).then()
		.assertThat().statusCode(HttpStatus.OK.value()).and().contentType(ContentType.JSON).and()
		.body("firstName", equalTo("Mark"));

	// Test that a repeat register fails (email must be unique)
	given().contentType(ContentType.JSON).body(newTrainer).when().port(port).post(REGISTER_TRAINER_URL).then()
		.assertThat().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

	Trainer emptyTrainer = new Trainer("", "", "", "");
	given().contentType(ContentType.JSON).body(emptyTrainer).when().port(port).post(REGISTER_TRAINER_URL).then()
		.assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
