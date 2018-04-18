package com.revature.gambit.controllers;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrainerControllerTest {

	private static final Logger log = Logger.getLogger(TrainerControllerTest.class);

	private static final String BASE_URL = "http://localhost:10001/trainers";
	private static final String FIND_TRAINER_BY_ID_URL = BASE_URL + "/{id}";

	@Test
	public void TestFindTrainerById(){
		log.trace("Testing findTrainerById ");
		when()
		.get(FIND_TRAINER_BY_ID_URL,2)
		.then()
		.statusCode(200)
		.body("firstName", equalTo("Dan"),
				"lastName" ,equalTo("Pickles"),
				"email", equalTo("pjw6193@hotmail.com"),
				"role.role",equalTo("ROLE_VP"),
				"title", equalTo("Lead Trainer"));

	}

}
