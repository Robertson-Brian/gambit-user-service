package com.revature.gambit.controllers;

import static io.restassured.RestAssured.when;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TraineeControllerTest {

	private static final Logger log = Logger.getLogger(TraineeControllerTest.class);
	
	private static final String BASE_URL = "http://localhost:10000/trainees";
	
	@Autowired
	private TraineeController traineeController;
	
	@Test
	public void empty() {
	}
	
	@Test
	public void getAll(){
		when().get(BASE_URL).then().assertThat()
				.statusCode(HttpStatus.OK.value());
	}
}
