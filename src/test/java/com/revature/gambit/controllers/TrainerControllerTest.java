package com.revature.gambit.controllers;


import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.gambit.entities.Trainer;
import com.revature.gambit.services.TrainerService;
import io.restassured.http.ContentType;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TrainerControllerTest {

	private static final Logger log = Logger.getLogger(TrainerControllerTest.class);

	private static final String BASE_URL = "http://localhost:10001/trainers";

	private static final String FIND_TRAINER_BY_EMAIL_URL = BASE_URL + "/email/{email:.+}/";
	private static final String FIND_TRAINER_BY_NAME_URL = BASE_URL + "/name/{firstName}/{lastName}";
	private static final String FIND_TRAINER_BY_EMAIL_URL = BASE_URL + "/email/{email:.+}/";
	private static final String FIND_ALL_TRAINER_TITLES_URL = BASE_URL + "/titles";
	private static final String FIND_ALL_TRAINERS_URL = BASE_URL;
	private static final String REGISTER_TRAINER_URL = BASE_URL;

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

	@Test
	public void findTrainerByEmail() {
		when().
			get(FIND_TRAINER_BY_EMAIL_URL, "steven.kelsey@revature.com").
		then().assertThat().
			statusCode(HttpStatus.OK.value());
	}

	@Test
	public void testFindAllTitles() {
		log.info("Find all trainers titles at : "+FIND_ALL_TRAINER_TITLES_URL);

		 when()
		.get(FIND_ALL_TRAINER_TITLES_URL)
		.then().assertThat().statusCode(HttpStatus.OK.value())
		.body("$", hasItems("Lead Trainer","Vice President of Technology",
	   		                "Technology Manager","Senior Java Developer",
		    		        "Trainer","Senior Trainer"));

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindAllTrainers(){
		log.info("Find all trainers titles at : "+FIND_ALL_TRAINERS_URL);

		List<Trainer> trainers = new ArrayList<>();

		trainers = when().get(FIND_ALL_TRAINERS_URL)
		                 .then().assertThat().statusCode(HttpStatus.OK.value())
		                 .extract().body()
		  				 .as(trainers.getClass());

		assertTrue(!trainers.isEmpty());
	}

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
