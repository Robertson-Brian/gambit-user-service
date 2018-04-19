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
	public void findTrainerById(){
		log.debug("Find trainer by id");
		Trainer newTrainer = new Trainer("Patrick","Walsh","patrick.walsh@revature.com","Lead Trainer");
		Trainer findById = trainerService.findById(39);
		log.info("Trainer Title "+findById.getTitle()+" "+ " "+findById.getFirstName()+" "+findById.getLastName());
		assertEquals(newTrainer.getFirstName(), findById.getFirstName());
		assertEquals(newTrainer.getLastName(), findById.getLastName());
		assertEquals(newTrainer.getEmail(), findById.getEmail());
		assertEquals(newTrainer.getTitle(), findById.getTitle());


		

	}
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
	}

	@Test
	public void testGetAllTitles(){

		log.debug("Testing trainerService.getAllTitles()");
		assertEquals(6, trainerService.getAllTitles().size());
		assertNotEquals(0, trainerService.getAllTitles().size());
	}

	@Test
	public void testGetAllTrainers(){
		log.debug("Testing trainerService.getAll()");
		assertEquals(12, trainerService.getAll().size());
		assertNotEquals(0, trainerService.getAll().size());
	}
}

