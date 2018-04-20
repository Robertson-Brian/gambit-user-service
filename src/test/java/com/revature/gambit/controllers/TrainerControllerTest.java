package com.revature.gambit.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
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
    private static final String UPDATE_TRAINER_URI = BASE_URI;
    private static final String FIND_TRAINER_BY_ID_URI = BASE_URI + "/{id}";
    private static final String PROMOTE_TRAINER_URI = BASE_URI + "/promote";
    private static final String FIND_TRAINER_BY_NAME_URI = BASE_URI + "/name/{firstName}/{lastName}";


    @Test
    public void testPromoteToTrainer() {
    	log.debug("Promote to trainer test.");
    	Trainer trainerToPromote = new Trainer("","","dlaut1@hotmail.com","Trainer");
    	given()
	       .contentType(ContentType.JSON)
	       .body(trainerToPromote)
	       .when()
	       .port(port)
	       .post(PROMOTE_TRAINER_URI)
	       .then()
		   .assertThat()
		   .statusCode(HttpStatus.OK_200)
		   .and()
		   .contentType(ContentType.JSON)
		   .and()
		   .body("title", equalTo("Trainer"));
    }

    @Test
    public void testPromoteToNonExistantTrainer() {
    	log.debug("Promote to trainer test with a non-existant trainer.");
    	Trainer trainerToPromote = new Trainer("","","mfleres@gmail.com","Trainer");
    	given()
	       .contentType(ContentType.JSON)
	       .body(trainerToPromote)
	       .when()
	       .port(port)
	       .post(PROMOTE_TRAINER_URI)
	       .then()
		   .assertThat()
		   .statusCode(HttpStatus.BAD_REQUEST_400);
    }

    @Test
    public void testPromoteToTrainerWithOnlyName() {
    	log.debug("Promote to trainer test with a non-existant trainer.");
    	Trainer trainerToPromote = new Trainer("Laut","Daniel","","Trainer");
    	given()
	       .contentType(ContentType.JSON)
	       .body(trainerToPromote)
	       .when()
	       .port(port)
	       .post(PROMOTE_TRAINER_URI)
	       .then()
		   .assertThat()
		   .statusCode(HttpStatus.OK_200)
		   .and()
		   .contentType(ContentType.JSON)
		   .and()
		   .body("title", equalTo("Trainer"));
    }

    @Test
    public void testPromoteToNoTrainer() {
    	log.debug("Promote to trainer test with empty fields.");
    	Trainer trainerToPromote = new Trainer("","","","");
    	given()
	       .contentType(ContentType.JSON)
	       .body(trainerToPromote)
	       .when()
	       .port(port)
	       .post(PROMOTE_TRAINER_URI)
	       .then()
		   .assertThat()
		   .statusCode(HttpStatus.BAD_REQUEST_400);
    }

    @Test
    public void testDeleteTrainer() {
    	log.debug("Deleting a Trainer");
    	int trainerId = trainerService.findTrainerByEmail("patrick.walsh@revature.com").getUserId();
    	given()
    	       .port(port)
    	       .delete(BASE_URI + "/{id}", trainerId)
    	       .then()
    	       .assertThat()
    	       .statusCode(HttpStatus.OK_200);
    }

    @Test
    public void testDeleteNonexistentTrainer() {
    	log.debug("Deleting a Trainer");
    	int trainerId = -1;
    	given()
    	       .port(port)
    	       .delete(BASE_URI + "/{id}", trainerId)
    	       .then()
    	       .assertThat()
    		   .statusCode(HttpStatus.INTERNAL_SERVER_ERROR_500);
    }

    @Test
    public void findTrainerByEmail200() {
    	log.debug("test findTrainerByEmail with bad input");
    	String email = "steven.kelsey@revature.com";
    	given()
    	       .when()
    	       .port(port)
    	       .get(FIND_TRAINER_BY_EMAIL_URI, email)
    	       .then()
    	       .assertThat()
    		   .statusCode(HttpStatus.OK_200)
    		   .body("email", equalTo(email));

    }

    @Test
    public void findTrainerByEmailInvalidTrainer() {
    	log.debug("test findTrainerByEmail with bad input");
    	String email = "jefrey@gmail.com";
    	String body = given()
    			             .when()
    			             .port(port)
    			             .get(FIND_TRAINER_BY_EMAIL_URI, email)
    			             .then()
    			             .assertThat()
    				         .statusCode(HttpStatus.NOT_FOUND_404)
    				         .extract()
    				         .body()
    				         .asString();

    	assertEquals(body, "");
    }

    @Test
    public void testFindAllTitles() {
    	log.debug("Find all trainers titles at : " + FIND_ALL_TRAINER_TITLES_URI);

    	given()
    	       .port(port)
    	       .basePath(FIND_ALL_TRAINER_TITLES_URI)
    	       .when()
    	       .get()
    	       .then()
    	       .assertThat()
    		   .statusCode(HttpStatus.OK_200)
    		   .body("$", hasItems("Lead Trainer", "Vice President of Technology",
    					"Technology Manager", "Senior Java Developer", "Trainer", "Senior Trainer"));

    }

    @Test
    public void findTrainerByEmailNonTrainer() {
    	log.debug("test findTrainerByEmail with non trainer email.");
    	String email = "ychenq001@gmail.com";
    	String body = given()
    			             .when()
    			             .port(port)
    			             .get(FIND_TRAINER_BY_EMAIL_URI, email)
    			             .then()
    			             .assertThat()
    				         .statusCode(HttpStatus.NOT_FOUND_404)
    				         .extract()
    				         .body()
    				         .asString();

    	assertEquals(body, "");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFindAllTrainers() {
    	log.debug("Find all trainers titles at : " + FIND_ALL_TRAINERS_URI);
    	List<Trainer> trainers = new ArrayList<>();
    	trainers = given()
    			          .port(port)
    			          .basePath(FIND_ALL_TRAINERS_URI)
    			          .when()
    			          .get()
    			          .then()
    			          .assertThat()
    			          .statusCode(HttpStatus.OK_200)
    			          .extract()
    			          .body()
    			          .as(trainers.getClass());

    	assertTrue(!trainers.isEmpty());
    }

    @Test
    public void testRegisterTrainer() {
    	Trainer newTrainer = new Trainer("Mark", "Fleres", "mfleres@gmail.com", "Trainer");
    	given()
    	       .contentType(ContentType.JSON)
    	       .body(newTrainer)
    	       .when()
    	       .port(port)
    	       .post(REGISTER_TRAINER_URI)
    	       .then()
    		   .assertThat()
    		   .statusCode(HttpStatus.OK_200)
    		   .and()
    		   .contentType(ContentType.JSON)
    		   .and()
    		   .body("firstName", equalTo("Mark"));
    }

    @Test
    public void testRegisterExistingTrainer() {
    	// Test that a repeat register fails (email must be unique)
    	Trainer repeatTrainer = new Trainer("Patrick","Walsh","patrick.walsh@revature.com","Trainer");
		given()
		       .contentType(ContentType.JSON)
		       .body(repeatTrainer)
		       .when()
		       .port(port)
		       .post(REGISTER_TRAINER_URI)
		       .then()
			   .assertThat()
			   .statusCode(HttpStatus.BAD_REQUEST_400);
    }

    @Test
    public void testRegisterEmptyTrainer() {
		Trainer emptyTrainer = new Trainer("", "", "", "");
		given()
		       .contentType(ContentType.JSON)
		       .body(emptyTrainer)
		       .when()
		       .port(port)
		       .post(REGISTER_TRAINER_URI)
		       .then()
			   .assertThat()
			   .statusCode(HttpStatus.BAD_REQUEST_400);
    }

    @Test
    public void testFindTrainerById(){
        log.debug("Testing findTrainerById ");
       int userId = trainerService.findTrainerByEmail("pjw6193@hotmail.com").getUserId();
        given()
               .port(port)
               .contentType(ContentType.JSON)
               .when()
               .get(FIND_TRAINER_BY_ID_URI,userId)
               .then()
               .assertThat()
               .statusCode(HttpStatus.OK_200)
               .body("firstName", equalTo("Dan"),
                     "lastName" ,equalTo("Pickles"),
                     "email", equalTo("pjw6193@hotmail.com"),
                     "role.role",equalTo("ROLE_VP"),
                     "title", equalTo("Lead Trainer"));

    }

   /**
    * @author Nikhil
    *
    * First test case: Testing with invalid trainer id
    * result:HTTP Status code=500
    * Second test case: Testing with trainer id, and updating first name,last name,email,and title
    * result:HTTP Status code=200, firstName is not null, and title is given title
    * Third test case: Updating only title
    * result:HTTP Status code=200,title has changed
    */

   @Test
   public void testUpdateTrainer() {
   	log.debug("Test for updating a trainer");
		Trainer targetTrainer = new Trainer("Nikhil","Pious","nikii@gmail.com","Technology Manager");
		targetTrainer.setUserId(37);
		given()
			   .contentType(ContentType.JSON)
			   .body(targetTrainer)
		       .when()
			   .port(port)
			   .put(UPDATE_TRAINER_URI)
		       .then()
			   .assertThat()
			   .statusCode(HttpStatus.INTERNAL_SERVER_ERROR_500);

		targetTrainer.setUserId(trainerService.findTrainerByEmail("patrick.walsh@revature.com").getUserId());
		given()
		       .contentType(ContentType.JSON)
		       .body(targetTrainer)
		       .when()
		       .port(port)
		       .put(UPDATE_TRAINER_URI)
		       .then()
		       .assertThat()
		       .statusCode(HttpStatus.OK_200)
		       .and()
		       .contentType(ContentType.JSON)
		       .and()
		       .body("firstName",is(notNullValue()))
		       .and()
		       .body("title", equalTo("Technology Manager"));

		targetTrainer.setTitle("Senior Java Developer");
		given()
		       .contentType(ContentType.JSON)
		       .body(targetTrainer)
		       .when()
		       .port(port)
		       .put(UPDATE_TRAINER_URI)
		       .then()
		       .assertThat()
		       .statusCode(HttpStatus.OK_200)
		       .and()
		       .contentType(ContentType.JSON)
		       .and()
		       .body("firstName", equalTo("Nikhil"))
		       .and()
		       .body("title", is(not("Technology Manager")));

	}

   @Test
   public void findTrainerByName200() {
	   log.debug("Testing findTrainerByName with valid trainer name");
	   String firstName = "Steven";
	   String lastName = "Kelsey";

	   Trainer expected = trainerService.findByName(firstName, lastName);

	   given()
	   		.when()
			.port(port)
			.get(FIND_TRAINER_BY_NAME_URI, firstName, lastName)
			.then()
			.assertThat()
			.statusCode(HttpStatus.OK_200)
			.body("firstName", equalTo(expected.getFirstName()))
			.body("lastName", equalTo(expected.getLastName()));

	}

   @Test
   public void findTrainerByNameInavlidTrainer() {
	   log.debug("Testing findTrainerByName with invalid user name");
	   String firstName = "jef";
	   String lastName = "rey";

	   String body = given()
			   			.when()
			   			.port(port)
			   			.get(FIND_TRAINER_BY_NAME_URI, firstName, lastName)
			   			.then()
			   			.assertThat()
			   			.statusCode(HttpStatus.NOT_FOUND_404).extract().body().asString();

	   assertEquals(body, "");


	}

   @Test
   public void findTrainerByNameNonTrainer() {
	   log.debug("Testing findByTrainerByName with valid user name, but who is not a trainer");
	   String firstName = "Chen";
	   String lastName = "Yan";

	   String body = given()
			   			.when()
						.port(port)
						.get(FIND_TRAINER_BY_NAME_URI, firstName, lastName)
						.then()
						.assertThat()
					  	.statusCode(HttpStatus.NOT_FOUND_404)
					  	.extract().body().asString();

		assertEquals(body, "");
	}

}
