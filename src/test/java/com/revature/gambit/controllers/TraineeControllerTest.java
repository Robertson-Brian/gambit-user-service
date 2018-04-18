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
	
	/**
	 * Checks that getByBatchAndStatus returns a 200 status code.
	 *  
	 */
	@Test
	public void getByBatchAndStatus() {
		log.debug("getByBatchAndStatus unit test starts here.");
		given().when().get("http://localhost:10000/trainees/batch/1/status/Training").then().assertThat().statusCode(200);
	
	}
}
