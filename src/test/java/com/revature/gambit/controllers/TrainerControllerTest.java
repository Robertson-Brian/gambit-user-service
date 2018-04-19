package com.revature.gambit.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;

import com.revature.gambit.GambitTest;
import com.revature.gambit.entities.Trainer;
import com.revature.gambit.services.TrainerService;

import io.restassured.http.ContentType;

public class TrainerControllerTest extends GambitTest {

    private static final Logger log = Logger.getLogger(TrainerControllerTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    private TrainerService trainerService;

    private static final String BASE_URI = "/trainers";
    private static final String FIND_TRAINER_BY_EMAIL_URI = BASE_URI + "/email/{email:.+}/";
    private static final String FIND_ALL_TRAINER_TITLES_URI = BASE_URI + "/titles";
    private static final String FIND_ALL_TRAINERS_URI = BASE_URI;
    private static final String REGISTER_TRAINER_URI = BASE_URI;
    private static final String FIND_TRAINER_BY_EMAIL_URL = BASE_URI;

    @Test
    public void testDeleteTrainer() {
	log.info("Deleting a Trainer");
	int trainerId = trainerService.findTrainerByEmail("patrick.walsh@revature.com").getUserId();
	given().port(port).delete(BASE_URI + "/{id}", trainerId).then().assertThat().statusCode(HttpStatus.OK_200);
    }

    @Test
    public void testDeleteNonexistentTrainer() {
	log.info("Deleting a Trainer");
	int trainerId = -1;
	given().port(port).delete(BASE_URI + "/{id}", trainerId).then().assertThat()
		.statusCode(HttpStatus.INTERNAL_SERVER_ERROR_500);
    }

    @Test
    public void findTrainerByEmail200() {
	log.debug("test findTrainerByEmail with bad input");
	String email = "steven.kelsey@revature.com";
	given().when().port(port).get(FIND_TRAINER_BY_EMAIL_URI, email).then().assertThat()
		.statusCode(HttpStatus.OK_200).body("email", equalTo(email));

    }

    @Test
    public void findTrainerByEmail500() {
	log.debug("test findTrainerByEmail with bad input");
	String email = "sdjkssx@gmail.com";
	String body = given().when().port(port).get(FIND_TRAINER_BY_EMAIL_URI, email).then().assertThat()
		.statusCode(HttpStatus.OK_200).extract().body().asString();

	assertEquals(body, "");
    }

    @Test
    public void testFindAllTitles() {
	log.debug("Find all trainers titles at : " + FIND_ALL_TRAINER_TITLES_URI);

	given().port(port).basePath(FIND_ALL_TRAINER_TITLES_URI).when().get().then().assertThat()
		.statusCode(HttpStatus.OK_200).body("$", hasItems("Lead Trainer", "Vice President of Technology",
			"Technology Manager", "Senior Java Developer", "Trainer", "Senior Trainer"));

    }

    @Test
    public void findTrainerByEmailNonTrainer() {
	log.debug("test findTrainerByEmail with non trainer email.");
	String email = "ychenq001@gmail.com";
	String body = given().when().port(port).get(FIND_TRAINER_BY_EMAIL_URI, email).then().assertThat()
		.statusCode(HttpStatus.OK_200).extract().body().asString();

	assertEquals(body, "");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFindAllTrainers() {
	log.debug("Find all trainers titles at : " + FIND_ALL_TRAINERS_URI);

	List<Trainer> trainers = new ArrayList<>();

	trainers = given().port(port).basePath(FIND_ALL_TRAINERS_URI).when().get().then().assertThat()
		.statusCode(HttpStatus.OK_200).extract().body().as(trainers.getClass());

	assertTrue(!trainers.isEmpty());
    }

    @Test
    public void testRegisterTrainer() {
	Trainer newTrainer = new Trainer("Mark", "Fleres", "mfleres@gmail.com", "Trainer");

	given().contentType(ContentType.JSON).body(newTrainer).when().port(port).post(REGISTER_TRAINER_URI).then()
		.assertThat().statusCode(HttpStatus.OK_200).and().contentType(ContentType.JSON).and()
		.body("firstName", equalTo("Mark"));

	// Test that a repeat register fails (email must be unique)
	given().contentType(ContentType.JSON).body(newTrainer).when().port(port).post(REGISTER_TRAINER_URI).then()
		.assertThat().statusCode(HttpStatus.BAD_REQUEST_400);

	Trainer emptyTrainer = new Trainer("", "", "", "");
	given().contentType(ContentType.JSON).body(emptyTrainer).when().port(port).post(REGISTER_TRAINER_URI).then()
		.assertThat().statusCode(HttpStatus.BAD_REQUEST_400);
    }
}
