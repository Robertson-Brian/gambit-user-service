package com.revature.gambit.controllers;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.gambit.entities.Trainer;
import com.revature.gambit.services.TrainerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrainerControllerTest {

	private static final Logger log = Logger.getLogger(TrainerControllerTest.class);
	
	private static final String BASE_URL = "http://localhost:10001/trainers";
	
	private static final String FIND_TRAINER_BY_EMAIL_URL = BASE_URL + "/email/{email:.+}/";
	private static final String FIND_TRAINER_BY_NAME_URL = BASE_URL + "/name/{firstName}/{lastName}";
	
	@Autowired
	private TrainerService trainerService;
	
//	@Test
//	public void findTrainerByEmail200() {
//		log.info("Testing findTrainerByEmail with good input");
//		
//		String email = "steven.kelsey@revature.com";
//		Trainer expected = trainerService.findTrainerByEmail(email);
//		
//		when().
//			get(FIND_TRAINER_BY_EMAIL_URL, email).
//		then().assertThat().
//			statusCode(HttpStatus.OK.value()).
//			body("email", is(expected.getEmail()));
//	}
	
	@Test
	public void findTrainerByEmail500() {
		String email = "sdjkssx@gmail.com";
		
		log.info("test findTrainerByEmail with bad input");
		
		when().
			get(FIND_TRAINER_BY_EMAIL_URL, email).
		then().assertThat().
			statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	
	@Test 
	public void findTrainerByEmailNonTrainer() {
		String email = "ychenq001@gmail.com";
		
		log.info("test findTrainerByEmail with non trainer email.");
		
		String body = when().
						get(FIND_TRAINER_BY_EMAIL_URL, email).
					  then().assertThat().
					  	statusCode(HttpStatus.OK.value()).extract().body().asString();
		
		assertEquals(body, "");
	}
	
	@Test
	public void findTrainerByName200() {
		String firstName = "Steven";
		String lastName = "Kelsey";
		
		Trainer expected = trainerService.findByName(firstName, lastName); 
		
		when().
			get(FIND_TRAINER_BY_NAME_URL, firstName, lastName).
		then().assertThat().
			statusCode(HttpStatus.OK.value()).
			body("firstName", is("Steven")).
			body("lastName", is("Kelsey"));
			
	}
	
	@Test
	public void findTrainerByName500() {
		String firstName = "dejideji";
		String lastName = "sxiwjdijiew";
		
		when().
			get(FIND_TRAINER_BY_NAME_URL, firstName, lastName).
		then().assertThat().
			statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
	
	@Test
	public void findTrainerByNameNonTrainer() {
		String firstName = "Chen";
		String lastName = "Yan";
		
		String body = when().
						get(FIND_TRAINER_BY_NAME_URL, firstName, lastName).
					  then().assertThat().
					  	statusCode(HttpStatus.OK.value()).extract().body().asString();
		
		assertEquals(body, "");
	}
	
}
