package com.revature.gambit.controllers;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TraineeControllerTest {

	private static final Logger log = Logger.getLogger(TraineeControllerTest.class);

	@Autowired
	private TraineeController traineeController;

	@Test
	public void save() {
		String body = "{\"userId\" : 0,\"firstName\": \"Shaleen\",\"lastName\": \"Anwar\",\"email\": \"shaleen.anwar@gmail.com\"}";
		given().header("Content-Type", "application/json")
				.body(body)
				.when()	
				.post("http://localhost:10001/trainees/").then().assertThat().statusCode(201);

	}
	

}
