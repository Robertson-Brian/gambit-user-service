package com.revature.gambit.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.gambit.entities.Trainee;
import com.revature.gambit.entities.TrainingStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TraineeServiceTest {

	private static final Logger log = Logger.getLogger(TraineeServiceTest.class);

	@Autowired
	private TraineeService traineeService;

	@Test
	public void save() {
		log.debug("Testing trainee save");
		Trainee trainee = new Trainee("Daniel", "Pickles", "dan.pickles@gogomail.com", "ayasn161hs9aes",
				TrainingStatus.Training, 1, "Extensure");
		// trainee has a batch.
		trainee.getBatches().add(1);
		trainee = traineeService.save(trainee);
		assertNotEquals(0, trainee.getUserId());
		log.trace("Trainee saved! " + trainee);

		log.debug("Testing trainee update");
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
		// be sure the fields actually changed
		assertEquals("Belotte", check.getClient());
		log.trace("Trainee updated!");

		log.debug("Testing trainee save (no batch)");
		// candidate has been scheduled for the technical discussion
		Trainee candidate = new Trainee("Howard", "Johnson", "howard.johnson@brooks.net", "ajsy1b173h29479w",
				TrainingStatus.Scheduled, "Edward Jones");
		candidate = traineeService.save(candidate);
		assertNotEquals(0, candidate.getUserId());
		log.trace("Trainee saved! " + candidate);

	}
	
	/**
	 * Tests methods:
	 * com.revature.gambit.services.TraineeServiceImpl.getAll()
	 * 
	 * Calling the getAll method, and storing the size of the returned trainee list.
	 * Getting the size of the data-h2.sql table to compare
	 * 
	 * 
	 * If the count sizes match then the test succeeds.
	 * 
	 */
	@Test
	public void getAll(){
		log.trace("Testing trainee getAll.");
		List<Trainee> allTrainees= traineeService.getAll();
		assertNotNull(allTrainees);
	}
}
