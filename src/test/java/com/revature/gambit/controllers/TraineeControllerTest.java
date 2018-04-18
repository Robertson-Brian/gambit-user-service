package com.revature.gambit.controllers;

import static io.restassured.RestAssured.given;

import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;

import com.revature.gambit.GambitTest;
import com.revature.gambit.repositories.TraineeRepository;

public class TraineeControllerTest extends GambitTest {

	@LocalServerPort
	private int port;
	
	private static final Logger log = Logger.getLogger(TraineeControllerTest.class);

	private static final String BASE_URI = "/trainees";
	
	@Test
	public void getAllTrainees() {
		given().port(port).basePath(BASE_URI).when()
		.get().then().assertThat()
		.statusCode(HttpStatus.OK_200);

		System.out.println(given().port(port).basePath(BASE_URI).when().get().getBody().asString());
	}
}
