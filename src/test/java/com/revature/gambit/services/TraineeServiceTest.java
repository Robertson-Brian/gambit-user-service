package com.revature.gambit.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
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
		// be sure the fields actually changed
		assertEquals("Belotte", check.getClient());
		log.trace("Trainee updated!");

		log.trace("Testing trainee save (no batch)");
		// candidate has been scheduled for the technical discussion
		Trainee candidate = new Trainee("Howard", "Johnson", "howard.johnson@brooks.net", "ajsy1b173h29479w",
				TrainingStatus.Scheduled, "Edward Jones");
		candidate = traineeService.save(candidate);
		assertNotEquals(0, candidate.getUserId());
		log.trace("Trainee saved! " + candidate);

	}
	
	/**
	 * Adds 5 trainees to DB
	 * Checks for trainees in Batch 3 with a trainingStatus of 'training'
	 * compares to 2 of the 5 trainees that were expected
	 */
	@Test
	public void findAllTraineeByBatchAndStatus(){
		Trainee trainee1 = new Trainee("John", "Smith", "John.smith@gogomail.com", "ayasn161hs9aes1",
				TrainingStatus.Training, 2, "Extensure");
		trainee1.getBatches().add(3);
		trainee1 = traineeService.save(trainee1);
		assertNotEquals(0, trainee1.getUserId());
		Trainee trainee2 = new Trainee("John2", "Smith", "John2.smith@gogomail.com", "ayasn161hs9aes2",
				TrainingStatus.Training, 2, "Extensure");
		trainee2.getBatches().add(3);
		trainee2 = traineeService.save(trainee2);
		assertNotEquals(0, trainee2.getUserId());
		Trainee trainee3 = new Trainee("John3", "Smith", "John3.pickles@gogomail.com", "ayasn161hs9aes3",
				TrainingStatus.Marketing, 1, "Extensure");
		trainee3.getBatches().add(3);
		trainee3 = traineeService.save(trainee3);
		assertNotEquals(0, trainee3.getUserId());
		Trainee trainee4 = new Trainee("John4", "Smith", "John4.pickles@gogomail.com", "ayasn161hs9aes4",
				TrainingStatus.Employed, 1, "Extensure");
		trainee4.getBatches().add(4);
		trainee4 = traineeService.save(trainee4);
		assertNotEquals(0, trainee4.getUserId());
		Trainee trainee5 = new Trainee("John5", "Smith", "John5.smith@gogomail.com", "ayasn161hs9aes5",
				TrainingStatus.Dropped, 1, "Extensure");
		trainee5.getBatches().add(4);
		trainee5 = traineeService.save(trainee5);
		assertNotEquals(0, trainee5.getUserId());
		
		log.trace("5 trainee's made. proceeding to test.");
		
		List<Trainee> expected = new ArrayList<>();
		expected.add(trainee1);
		expected.add(trainee2);
		List<Trainee> result = traineeService.findAllByBatchAndStatus(3, "Training");
		assertEquals(expected, result);	
	}
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

