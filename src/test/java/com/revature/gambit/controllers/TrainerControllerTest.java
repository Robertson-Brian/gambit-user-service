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

import com.revature.gambit.entities.Trainer;
import com.revature.gambit.messaging.KafkaTest;
import com.revature.gambit.services.TrainerService;

import io.restassured.http.ContentType;

/**
 *
 * Tests for inserting, updating, retrieving and deleting Trainers
 * from HTTP Requests.
 *
 */
public class TrainerControllerTest extends KafkaTest {

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


    /**
     * Tests promoting a user from trainee to trainer.
     * 
     * @author Mark Fleres
     * @return Status Code 200 upon success.
     */
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

    /**
     * Tests that promoting a user fails when the user is non-existent.
     * 
     * @author Mark Fleres
     * @return Status Code 400 upon success.
     */
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

    /**
     * Tests promoting a user by name.
     * 
     * @author Mark Fleres
     * @return Status Code 200 upon success.
     */
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

    /**
     * Tests that promoting a user fails when the trainer is null.
     * 
     * @author Mark Fleres
     * @return Status Code 400 upon success.
     */
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

    /**
     * Tests deleting a trainer.
     * 
     * @author Raymond Xia
     * @return Status Code 200 upon success.
     */
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

    /**
     * Tests that a delete fails with a nonexistent trainer.
     *
     * @author Raymond Xia
     * @return Status Code 400 upon success.
     */
    @Test
    public void testDeleteNonexistentTrainer() {
    	log.debug("Deleting a Trainer");
    	int trainerId = -1;
    	given()
    	       .port(port)
    	       .delete(BASE_URI + "/{id}", trainerId)
    	       .then()
    	       .assertThat()
    		   .statusCode(HttpStatus.BAD_REQUEST_400);
    }

    /**
     * Tests that a trainer retrieval from their email.
     *
     * @author Jeffrey Reyes
     * @return Status Code 200 upon success.
     */
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

    /**
     * Tests that trainer retrieval fails with a invalid email.
     *
     * @author Jeffrey Reyes
     * @return Status Code 404 upon success
     */
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

    /**
     * Tests that all titles for a trainer can be retrieved.
     *
     * @author Jing Yu
     * @return Status Code 200 upon success.
     */
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

    /**
     * Tests trainer retrieval fails with a non-trainer email.
     *
     * @author Jing Yu
     * @return Status Code 404 upon success.
     */
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

    /**
     * Tests retrieval of all trainers.
     *
     * @author Jing Yu
     * @return Status Code 200 upon success.
     */
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

    /**
     * Tests that a trainer can be registered.
     *
     * @author Mark Fleres
     * @return Status Code 200 upon success
     */
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


    /**
     * Tests that a trainer cannot be registered when trainer already exists.
     *
     * @author Mark Fleres
     * @return Status Code 400 upon success.
     */
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

    /**
     * Tests that registering an empty trainer object cannot be registered.
     *
     * @author Mark Fleres
     * @return Status Code 400 upon success.
     */
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


    /**
     * Test trainer retrieval by their ID.
     *
     * @author Junyu Chen
     * @return Status Code 200 upon success.
     */
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
    * Test 1: Testing that a trainer cannot be updated if the trainer doesn't exist.
    * Test 2: Testing that a trainer ID can be updated.
    * Test 3: Testing that a trainer title can be updated.
    * 
    * @author Nikhil Pious
    * @return Test 1: Status Code 400 upon success.
    * @return Test 2: Status Code 200 upon success.
    * @return Test 3: Status Code 200 upon success.
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
			   .statusCode(HttpStatus.BAD_REQUEST_400);

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

   /**
    * Tests finding trainer by name
    * 
    * @author Jeffrey Reyes
    * @return Status Code 200 upon success
    */
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

   /**
    * Tests finding trainer by name fails with invalid Trainer.
    * 
    * @author Jeffrey Reyes
    * @return Status Code 404 upon success
    */
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
   /**
    * Tests finding trainer by name fails with non-trainer.
    * 
    * @author Jeffrey Reyes
    * @return Status Code 404 upon success
    */
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
