package com.revature.gambit.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.revature.gambit.entities.Trainer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TrainerServiceTest {

	private static final Logger logger = Logger.getLogger(TrainerServiceTest.class);

	@Autowired
	private TrainerService trainerService;
	
	@Test
	public void save() {
		logger.debug("Testing trainerService.newTrainer(Trainer trainer)");
		Trainer newTrainer = new Trainer("Mark","Fleres","mfleres@gmail.com","Dr.");
		logger.trace("newTrainer = " + newTrainer);
		Trainer savedTrainer = trainerService.newTrainer(newTrainer);
		logger.trace("savedTrainer = " + savedTrainer);
		assertNotEquals(0, savedTrainer.getUserId());
		assertEquals(newTrainer.getTitle(), savedTrainer.getTitle());
		assertEquals(newTrainer.getFirstName(), savedTrainer.getFirstName());
	}
}
