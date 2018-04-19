package com.revature.gambit.controllers;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.revature.gambit.GambitTest;
import com.revature.gambit.entities.Trainer;
import com.revature.gambit.services.TrainerService;

import io.restassured.http.ContentType;

public class TrainerControllerTest extends GambitTest {

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TrainerControllerTest extends GambitTest {

	private static final Logger log = Logger.getLogger(TrainerControllerTest.class);

	@LocalServerPort
	private int port;

	private static final String BASE_URI = "/trainers";
	private static final String FIND_TRAINER_BY_EMAIL = BASE_URI + "/email/{email:.+}/";

	@Autowired
	private TrainerService trainerService;

	@Test
	public void findTrainerByEmail200() {
		log.info("Testing findTrainerByEmail with good input");

		String email = "steven.kelsey@revature.com";
		Trainer expected = trainerService.findTrainerByEmail(email);

		given().when().port(port).
			get(FIND_TRAINER_BY_EMAIL, email).
		then().assertThat().
			statusCode(HttpStatus.OK.value()).
			body("email", equalTo(expected.getEmail()));
	}

	@Test
	public void findTrainerByEmail500() {
		String email = "sdjkssx@gmail.com";

		log.info("test findTrainerByEmail with bad input");

		String body = given().when().port(port).
						get(FIND_TRAINER_BY_EMAIL, email).
					  then().assertThat().
					  	statusCode(HttpStatus.OK.value()).extract().body().asString();

		assertEquals(body, "");
	}

	@Test
	public void findTrainerByEmailNonTrainer() {
		String email = "ychenq001@gmail.com";

		log.info("test findTrainerByEmail with non trainer email.");

		String body = given().when().port(port).
				get(FIND_TRAINER_BY_EMAIL, email).
			      then().assertThat().
				statusCode(HttpStatus.OK.value()).extract().body().asString();

		assertEquals(body, "");

    @Test
	public void testRegisterTrainer() {
		Trainer newTrainer = new Trainer("Mark","Fleres","mfleres@gmail.com","Trainer");

		given().
			contentType(ContentType.JSON).
			body(newTrainer).
		when().
			port(port).post(REGISTER_TRAINER_URL).
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
			port(port).post(REGISTER_TRAINER_URL).
		then().
			assertThat().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

		Trainer emptyTrainer = new Trainer("","","","");
		given().
			contentType(ContentType.JSON).
			body(emptyTrainer).
		when().
			port(port).post(REGISTER_TRAINER_URL).
		then().
			assertThat().statusCode(HttpStatus.BAD_REQUEST.value());
	}
}
