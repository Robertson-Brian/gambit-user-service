package com.revature.gambit.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.revature.gambit.entities.Trainee;
import com.revature.gambit.entities.TrainingStatus;
import com.revature.gambit.messaging.SenderTest;

import static com.revature.gambit.util.MessagingUtil.TOPIC_REGISTER_TRAINEE;
import static com.revature.gambit.util.MessagingUtil.TOPIC_UPDATE_TRAINEE;
import static com.revature.gambit.util.MessagingUtil.TOPIC_DELETE_TRAINEE;

/**
 * Test methods for inserting, updating, retrieving, and deleting.
 * 
 */

@Transactional
public class TraineeServiceTest extends SenderTest {

	private static final Logger log = Logger.getLogger(TraineeServiceTest.class);

	@Autowired
	private TraineeService traineeService;


	/**
	 * Tests that a new trainee is created successfully.
	 * 
	 * @author Shaleen Anwar
	 */
	@Test
	public void save() {
		log.trace("Testing trainee save");
		Trainee trainee = new Trainee("Daniel", "Pickles", "dan.pickles@gogomail.com", "ayasn161hs9aes",
				TrainingStatus.Training, 1, "Extensure");

		// Trainee has a batch.
		trainee.getBatches().add(1);
		trainee = traineeService.save(trainee);
		assertNotEquals(0, trainee.getUserId());
		log.trace("Trainee saved! " + trainee);
		
		//Kafka Test
		Trainee kafkaTrainee = (Trainee)receive(TOPIC_REGISTER_TRAINEE,Trainee.class);
		assertNotNull(kafkaTrainee);
		assertEquals(trainee.getUserId(),kafkaTrainee.getUserId());

		log.debug("Testing trainee save (no batch)");

		// Candidate has been scheduled for the technical discussion
		Trainee candidate = new Trainee("Howard", "Johnson", "howard.johnson@brooks.net", "ajsy1b173h29479w",
				TrainingStatus.Scheduled, "Edward Jones");
		candidate = traineeService.save(candidate);
		assertNotEquals(0, candidate.getUserId());
		log.trace("Trainee saved! " + candidate);
		
		
	}

	/**
	 * Tests that null is returned for new trainee with email that already exists.
	 * 
	 * @author Shaleen Anwar
	 */
	@Test
	public void saveDuplicate() {
		log.debug("Testing that Trainee cannot register if email already exists");
		Trainee trainee = new Trainee("Howard","Johnson","howard.johnson@hotmail.com");

		// Checks that if email exists, it returns null
		assertEquals(null, traineeService.save(trainee));
		log.trace("Trainee could not register because of existing email");
		
		//Kafka Test
		Trainee kafkaTrainee = (Trainee) receive(TOPIC_REGISTER_TRAINEE,Trainee.class);
		assertNull(kafkaTrainee);
	}

	/**
	 * Tests finding a trainee by their email.
	 * 
	 * @author Joel DeJesus
	 */
	@Test
	public void findByEmail() {
		log.debug("Testing trainee save");
		Trainee trainee = new Trainee("Daniel", "Pickles", "dan.pickles@gogomail.com", "ayasn161hs9aes",
				TrainingStatus.Training, 1, "Extensure");
		traineeService.save(trainee);

		assertEquals(trainee, traineeService.findByEmail("dan.pickles@gogomail.com"));
	}

	/**
	 * Tests that null is returned when finding an email that doesn't exist.
	 * 
	 * @author Joel DeJesus
	 */
	@Test
	public void findByInvalidEmail() {
		log.debug("Testing trainee save");
		Trainee trainee = new Trainee("Daniel", "Pickles", "dan.pickles@gogomail.com", "ayasn161hs9aes",
				TrainingStatus.Training, 1, "Extensure");
		traineeService.save(trainee);

		assertNotEquals(trainee, traineeService.findByEmail("this.email.does.not@exist.com"));
	}

	/**
	 * Creates a List of all trainees, then cycles through them 
	 * to verify that they exist in the table.
	 * 
	 * @author Alejandro Iparraguirre
	 */
	@Test
	public void getAllEntitiesCorrespond(){
		log.debug("Testing getAll entities match existing entities on table.");		
		List<Trainee> listTrainee = traineeService.getAll();
		for(Trainee trainee : listTrainee){
			assertEquals(trainee, traineeService.findByEmail(trainee.getEmail())); 
		}
		log.trace("Entities correspond");
	}

	/** 
	 * Verifies that table exists.
	 * 
	 * @author Alejandro Iparraguirre
	 */
	@Test
	public void getAllNotNull(){
		log.debug("Testing that a table exists.");
		assertNotNull(traineeService.getAll());
		log.trace("Trainee Table Exists.");
	}	
	/**
	 * Tests deleting a trainee from the table.
	 * 
	 * @author Joseph Arbelaez
	 */
	@Test
	public void delete() {
		log.debug("TraineeServiceTest.delete()");
		Trainee test = new Trainee("TestTrainDelete", "TestTrainDelete", "TestTrainDelete@brooks.net", "ajsy1b173h29479w",
				TrainingStatus.Scheduled, "TestTrainDelete");
		test = traineeService.save(test);
		int initialSize = traineeService.getAll().size();
		traineeService.delete(test);
		int currentSize = traineeService.getAll().size();
		assertNotEquals(initialSize,currentSize);
		
		//Kafka Test
		Trainee kafkaTrainee = (Trainee) receive(TOPIC_DELETE_TRAINEE,Trainee.class);
		assertNotNull(kafkaTrainee);
		assertEquals(test.getUserId(), kafkaTrainee.getUserId());
	}
	/**
	 * Checks for trainees in Batch  with a trainingStatus of 'training'.
	 * Current database only has one trainee that matches those specifications.
	 * 
	 * @author Brian Ethier
	 */
	@Test
	public void findAllTraineeByBatchAndStatus(){
		log.debug("Testing find all by batch and status.");
		List<Trainee> result = traineeService.findAllByBatchAndStatus(1, "Training");
		assertEquals(1, result.size());	
	}

	/**
	 * Check for trainees in a batch that doesn't contain any trainees.
	 * This should return a list of length 0.
	 * 
	 * @author Brian Ethier
	 */
	@Test
	public void findAllTraineeByBadBatchAndStatus(){
		log.debug("Testing find all by batch and status using unused batch number");
		assertEquals(0,traineeService.findAllByBatchAndStatus(3, "Training").size());
	}

	/**
	 * Checks for trainees with an invalid training status.
	 * The returned list should return null.
	 * 
	 * @author Brian Ethier
	 */
	@Test
	public void findAllTraineeByBatchAndBadStatus(){
		log.debug("Testing find all by batch and status using wrong status.");
		assertNull(traineeService.findAllByBatchAndStatus(1, "invalid"));
	}

	/**
	 * Tests creating and updating an employee.
	 * 
	 * @author Ismael Khalil
	 */
	@Test
	public void testUpdate() {
		log.debug("Testing trainee update)");
		Trainee targetTrainee = traineeService.findByEmail("dlaut1@hotmail.com");
		log.trace("targetTrainee = " + targetTrainee);
		assertEquals("Laut", targetTrainee.getFirstName());
		
		targetTrainee.setEmail("mrpickles@gmail.com");
		targetTrainee.setFirstName("Tommy");
		Trainee updateTargetTrainee = traineeService.update(targetTrainee);
		log.trace("updateTargetTrainee = " + updateTargetTrainee);
		List<String> updatedList = Arrays.asList("Tommy","Daniel","mrpickles@gmail.com");
		assertThat(updatedList,CoreMatchers.hasItems(updateTargetTrainee.getFirstName(),updateTargetTrainee.getLastName(),updateTargetTrainee.getEmail()));
	
		//Kafka Test
		Trainee kafkaTrainee = (Trainee)receive(TOPIC_UPDATE_TRAINEE,Trainee.class);
		assertNotNull(kafkaTrainee);
		assertEquals(updateTargetTrainee.getUserId(),kafkaTrainee.getUserId());
		
		updateTargetTrainee.setFirstName("Steve");
		updateTargetTrainee.setLastName("Johns");
		traineeService.update(updateTargetTrainee);
		log.trace("updateTargetTrainee second time = " + updateTargetTrainee);
		List<String> newUpdatedList = Arrays.asList("Steve","Johns","mrpickles@gmail.com");
		assertThat(newUpdatedList,CoreMatchers.hasItems(updateTargetTrainee.getFirstName(),updateTargetTrainee.getLastName(),updateTargetTrainee.getEmail()));
		assertNotEquals("Tommy",updateTargetTrainee.getFirstName());
		
		//Kafka Test Again
		kafkaTrainee = (Trainee)receive(TOPIC_UPDATE_TRAINEE,Trainee.class);
		assertNotNull(kafkaTrainee);
		assertEquals(updateTargetTrainee.getUserId(),kafkaTrainee.getUserId());
	}

	/**
	 * Tests that null is returned when updating a nonexistent employee userId.
	 * 
	 * @author Ismael Khalil
	 */
	@Test
	public void updateNull() {
		log.trace("Testing trainee update");
		Trainee trainee = new Trainee("Test", "ing", "testing123@hmail.com", "ayasn161hs9aes",
				TrainingStatus.Training, 1, "Extensure");
		trainee = traineeService.update(trainee);
		// userId must not exist
		assertNull(trainee);
		
		//Kafka Test
		Trainee kafkaTrainee = (Trainee)receive(TOPIC_UPDATE_TRAINEE,Trainee.class);
		assertNull(kafkaTrainee);
	}
}