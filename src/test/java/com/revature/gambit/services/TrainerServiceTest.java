package com.revature.gambit.services;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.revature.gambit.entities.Trainer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TrainerServiceTest {

    private static final Logger log = Logger.getLogger(TrainerServiceTest.class);

    @Autowired
    private TrainerService trainerService;

    private Trainer trainer1;
    private Trainer trainer2;

    @Before
    public void testCreateTrainers() {
	trainer1 = new Trainer("John", "Doe", "JohnDoe@gmail.com", "Trainer");
	trainer2 = new Trainer("Mark", "Fleres", "mfleres@gmail.com", "Doctor");
    }

    @Test
    public void testNewTrainer() {
	log.debug("Testing trainerService.newTrainer(Trainer trainer)");
	Trainer savedTrainer = trainerService.newTrainer(trainer2);
	assertNotEquals(0, savedTrainer.getUserId());
	assertEquals(trainer2.getTitle(), savedTrainer.getTitle());
	assertEquals(trainer2.getFirstName(), savedTrainer.getFirstName());
    }

    @Test
    public void testDeleteTrainer() {
	Trainer savedTrainer = trainerService.newTrainer(trainer1);
	trainerService.delete(savedTrainer.getUserId());
	assertNull(trainerService.findById(savedTrainer.getUserId()));
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

    @Test
    public void testDeleteNonexistentTrainer() {
	assertThatThrownBy(() -> {
	    trainerService.delete(-1);
	});
    }

}
