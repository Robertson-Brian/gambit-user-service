package com.revature.gambit.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

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
		
		// Checks that if email exists, it returns null
		assertEquals(null, traineeService.save(trainee));
	}

	@Test
	public void findByEmail() {
		log.trace("Testing trainee save");
		Trainee trainee = new Trainee("Daniel", "Pickles", "dan.pickles@gogomail.com", "ayasn161hs9aes",
				TrainingStatus.Training, 1, "Extensure");
		traineeService.save(trainee);
		
		assertEquals(trainee, traineeService.findByEmail("dan.pickles@gogomail.com"));
	}
	@Test
	public void findByInvalidEmail() {
		log.trace("Testing trainee save");
		Trainee trainee = new Trainee("Daniel", "Pickles", "dan.pickles@gogomail.com", "ayasn161hs9aes",
				TrainingStatus.Training, 1, "Extensure");
		traineeService.save(trainee);
		
		assertNotEquals(trainee, traineeService.findByEmail("this.email.does.not@exist.com"));
	}
	
	/**
	 * Tests methods:
	 * com.revature.gambit.services.TraineeServiceImpl.getAll()
	 * 
	 * After creating three trainees and calling getAll method returns all three.
	 * The check is done by comparing the expected size and the size of the table.
	 * 
	 */
	@Test
	public void getAll(){
		log.debug("Testing trainee getAll");
		//creating 3 trainees 
		Trainee trainee = new Trainee("Daniel", "Pickles", "dan.pickles@gogomail.com", "ayasn161hs9aes",
				TrainingStatus.Training, 1, "Extensure");
		trainee.getBatches().add(1);
		traineeService.save(trainee);
		
		Trainee trainee1 = new Trainee("Daniel", "Pick", "dan.pick@gogomail.com", "ayasn161hs9aes",
				TrainingStatus.Training, 1, "Extensure");
		trainee1.getBatches().add(2);
		traineeService.save(trainee1);
		
		Trainee trainee2 = new Trainee("Daniel", "Les", "dan.les@gogomail.com", "ayasn161hs9aes",
				TrainingStatus.Training, 1, "Extensure");
		trainee2.getBatches().add(3);
		traineeService.save(trainee2);
		
		log.debug("Testing table exists and the size of table is correct");
		assertNotNull(traineeService.getAll());
		assertEquals(3, traineeService.getAll().size());
	}	
	/**
	 * Tests the delete method of the Trainee Service Layer.
	 * 
	 */
	@Test
	public void delete() {
		log.debug("Test delete Trainee");
		Trainee test = new Trainee("TestTrainDelete", "TestTrainDelete", "TestTrainDelete@brooks.net", "ajsy1b173h29479w",
				TrainingStatus.Scheduled, "TestTrainDelete");
		traineeService.save(test);
		int initialSize = traineeService.getAll().size();
		traineeService.delete(test);
		int currentSize = traineeService.getAll().size();
		assertNotEquals(initialSize,currentSize);
	}
	
	@Test
	public void update() {
		log.trace("Testing trainee save");
		Trainee trainee = new Trainee("Daniel", "Pickles", "dan.pickles@gogomail.com", "ayasn161hs9aes",
				TrainingStatus.Training, 1, "Extensure");
		// trainee has a batch.
		trainee.getBatches().add(1);
		trainee = traineeService.save(trainee);
		assertNotEquals(0, trainee.getUserId());
		log.trace("Trainee saved! " + trainee);
		log.trace("Testing trainee update");
		Trainee update = new Trainee("Daniel", "Pickles", "dan.pickles@gogomail.com", "", TrainingStatus.Training, 1,
				"Belotte");
		// trainee has a different batch now
		update.getBatches().add(2);
		Trainee check = traineeService.save(update);
		// resourceIds must be same when updating
		assertEquals(check.getResourceId(), trainee.getResourceId());
		// batches must be loaded from database and added to list
		Set<Integer> expected = new HashSet<>();
		expected.add(1);
		expected.add(2);
		assertEquals(expected, check.getBatches());
	}
}
