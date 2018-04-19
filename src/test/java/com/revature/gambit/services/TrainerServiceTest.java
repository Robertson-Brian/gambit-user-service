package com.revature.gambit.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.revature.gambit.GambitTest;
import com.revature.gambit.entities.Trainer;

public class TrainerServiceTest extends GambitTest {

	private static final Logger log = Logger.getLogger(TrainerServiceTest.class);

	@Autowired
	private TrainerService trainerService;
	
	@Test
	public void testNewTrainer() {
		log.debug("Testing trainerService.newTrainer(Trainer trainer)");
		Trainer newTrainer = new Trainer("Mark","Fleres","mfleres@gmail.com","Dr.");
		log.trace("newTrainer = " + newTrainer);
		Trainer savedTrainer = trainerService.newTrainer(newTrainer);
		log.trace("savedTrainer = " + savedTrainer);
		assertNotEquals(0, savedTrainer.getUserId());
		assertEquals(newTrainer.getTitle(), savedTrainer.getTitle());
		assertEquals(newTrainer.getFirstName(), savedTrainer.getFirstName());
		
		//Test Empty Trainer
		Trainer emptyTrainer = new Trainer("","","","");
		Trainer savedEmptyTrainer = trainerService.newTrainer(emptyTrainer);
		assertEquals(null,savedEmptyTrainer);
	}
	
	@Test
	public void testGetAllTitles(){
		
		log.debug("Testing trainerService.getAllTitles()");
		assertEquals(1, trainerService.getAllTitles().size());
		assertNotEquals(0, trainerService.getAllTitles().size());
	}
	
	@Test
	public void testGetAllTrainers(){
		log.debug("Testing trainerService.getAll()");
		assertEquals(1, trainerService.getAll().size());
		assertNotEquals(0, trainerService.getAll().size());
	}
}
