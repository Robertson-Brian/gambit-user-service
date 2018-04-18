package com.revature.gambit.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
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

	private static final String REGISTER_TRAINER_URL = BASE_URL;
	
	@Test
	public void testRegisterTrainer() {
		Trainer newTrainer = new Trainer("Mark","Fleres","mfleres@gmail.com","Doctor");
		
		given().
			contentType(ContentType.JSON).
			body(newTrainer).
		when().
			post(REGISTER_TRAINER_URL).
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
			post(REGISTER_TRAINER_URL).
		then().
			assertThat().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
}
