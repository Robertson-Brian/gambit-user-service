package com.revature.gambit.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.revature.gambit.GambitTest;
import com.revature.gambit.entities.Trainee;
import com.revature.gambit.entities.TrainingStatus;

public class TraineeServiceTest extends GambitTest {

	private static final Logger log = Logger.getLogger(TraineeServiceTest.class);

	@Autowired
	private TraineeService traineeService;
	
	
	/**
	 * Tests that a unique trainee is created successfully. 
	 */
	@Test
	public void save() {
		log.trace("Testing trainee save");
		Trainee trainee = new Trainee("Daniel", "Pickles", "dan.pickles@gogomail.com", "ayasn161hs9aes",
				TrainingStatus.Training, 1, "Extensure");
	
		// trainee has a batch.
		trainee.getBatches().add(1);
		trainee = traineeService.save(trainee);
		assertNotEquals(0, trainee.getUserId());
		log.trace("Trainee saved! " + trainee);

		log.debug("Testing trainee save (no batch)");
		
		// candidate has been scheduled for the technical discussion
		Trainee candidate = new Trainee("Howard", "Johnson", "howard.johnson@brooks.net", "ajsy1b173h29479w",
				TrainingStatus.Scheduled, "Edward Jones");
		candidate = traineeService.save(candidate);
		assertNotEquals(0, candidate.getUserId());
		log.trace("Trainee saved! " + candidate);
	
	}
	
	/**
	 * Tests that null is returned for new trainee with email that already exists. 
	 */
	@Test
	public void saveDuplicate() {
		log.debug("Testing that Trainee cannot register if email already exists");
		Trainee trainee = new Trainee("Howard","Johnson","howard.johnson@hotmail.com");
		
		// Checks that if email exists, it returns null
		assertEquals(null, traineeService.save(trainee));
		log.trace("Trainee could not register because of existing email");
	}
	
	/**
	 * Tests that find a user by their email.
	 */
	@Test
	public void findByEmail() {
		log.debug("Testing trainee save");
		Trainee trainee = new Trainee("Daniel", "Pickles", "dan.pickles@gogomail.com", "ayasn161hs9aes",
				TrainingStatus.Training, 1, "Extensure");
		traineeService.save(trainee);
		
		assertEquals(trainee, traineeService.findByEmail("dan.pickles@gogomail.com"));
	}
	@Test
	public void findByInvalidEmail() {
		log.debug("Testing trainee save");
		Trainee trainee = new Trainee("Daniel", "Pickles", "dan.pickles@gogomail.com", "ayasn161hs9aes",
				TrainingStatus.Training, 1, "Extensure");
		traineeService.save(trainee);
		
		assertNotEquals(trainee, traineeService.findByEmail("this.email.does.not@exist.com"));
	}
	
	/**
	 * Tests methods:
	 * com.revature.gambit.services.TraineeServiceImpl.getAll()
	 * 
	 * Creates a List of all Trainees, then cycles through them to verify
	 * 	they exist in the table.  
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
	 * Tests methods:
	 * com.revature.gambit.services.TraineeServiceImpl.getAll()
	 * 
	 * 	Verifies that table exists.
	 */
	@Test
	public void getAllNotNull(){
		log.debug("Testing that a table exists.");
		assertNotNull(traineeService.getAll());
		log.trace("Trainee Table Exists.");
	}	
	/**
	 * Tests the delete method of the Trainee Service Layer.
	 * 
	 */
	@Test
	public void delete() {
		log.debug("TraineeServiceTest.delete()");
		Trainee test = new Trainee("TestTrainDelete", "TestTrainDelete", "TestTrainDelete@brooks.net", "ajsy1b173h29479w",
				TrainingStatus.Scheduled, "TestTrainDelete");
		traineeService.save(test);
		int initialSize = traineeService.getAll().size();
		traineeService.delete(test);
		int currentSize = traineeService.getAll().size();
		assertNotEquals(initialSize,currentSize);
	}
	/**
	 * 
	 * Checks for trainees in Batch  with a trainingStatus of 'training'
	 * current database only has one trainee that matches those specifications.
	 */
	@Test
	public void findAllTraineeByBatchAndStatus(){
		log.debug("Testing find all by batch and status.");
		List<Trainee> result = traineeService.findAllByBatchAndStatus(1, "Training");
		assertEquals(1, result.size());	
	}
	
	/**
	 * Check for trainees in a batch that doesn't contain any trainees
	 * This should return a list of length 0.
	 */
	@Test
	public void findAllTraineeByBadBatchAndStatus(){
		log.debug("Testing find all by batch and status using unused batch number");
		assertEquals(0,traineeService.findAllByBatchAndStatus(3, "Training").size());
	}
	
	/**
	 * Checks for trainees with an invalid training status
	 * checks that the returned list is null.
	 */
	@Test
	public void findAllTraineeByBatchAndBadStatus(){
		log.debug("Testing find all by batch and status using wrong status.");
		assertNull(traineeService.findAllByBatchAndStatus(1, "invalid"));
	}

	@Test
	public void update() {
		log.trace("Testing trainee save");
		Trainee trainee = new Trainee("Daniel", "Pickles", "dan.pickles@gogomail.com", "ayasn161hs9aes",
				TrainingStatus.Training, 1, "Extensure");
		// trainee has a batch.
		trainee.getBatches().add(1);
		trainee = traineeService.save(trainee);
		log.trace("Testing trainee update");
		// trainee has a different batch now
		trainee.getBatches().add(2);
		trainee = traineeService.update(trainee);
		// resourceIds must be same when updating
		assertEquals(trainee.getResourceId(), trainee.getResourceId());
		// batches must be loaded from database and added to list
		Set<Integer> expected = new HashSet<>();
		expected.add(1);
		expected.add(2);
		assertEquals(expected, trainee.getBatches());
	}
}
