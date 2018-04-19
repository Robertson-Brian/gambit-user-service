package com.revature.gambit.services;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.revature.gambit.GambitTest;
import com.revature.gambit.entities.Trainer;

public class TrainerServiceTest extends GambitTest {

    private static final Logger log = Logger.getLogger(TrainerServiceTest.class);

    @Autowired
    private TrainerService trainerService;

    private Trainer trainer1;
    private Trainer trainer2;

    @Before
    public void testCreateTrainers() {
	trainer1 = new Trainer("John", "Doe", "JohnDoe@gmail.com", "Trainer");
	trainer2 = new Trainer("Peter", "Alagna", "peter.alagna@revature.com", "Wizard");

	trainerService.newTrainer(trainer1);
	trainerService.newTrainer(trainer2);
    }

    @Test
    public void testNewTrainer() {
	log.debug("Testing trainerService.newTrainer(Trainer trainer)");
	Trainer newTrainer = new Trainer("Mark", "Fleres", "mfleres@gmail.com", "Dr.");
	log.trace("newTrainer = " + newTrainer);
	Trainer savedTrainer = trainerService.newTrainer(newTrainer);
	log.trace("savedTrainer = " + savedTrainer);
	assertNotEquals(0, savedTrainer.getUserId());
	assertEquals(newTrainer.getTitle(), savedTrainer.getTitle());
	assertEquals(newTrainer.getFirstName(), savedTrainer.getFirstName());

	// Test Repeat Trainer
	Trainer savedRepeatTrainer = trainerService.newTrainer(newTrainer);
	assertEquals(null, savedRepeatTrainer);

	// Test Empty Trainer
	Trainer emptyTrainer = new Trainer("", "", "", "");
	Trainer savedEmptyTrainer = trainerService.newTrainer(emptyTrainer);
	assertEquals(null, savedEmptyTrainer);
    }

    @Test
    public void testDeleteTrainer() {
	trainerService.delete(trainer1.getUserId());
	assertNull(trainerService.findById(trainer1.getUserId()));
    }

    @Test
    public void testDeleteNonexistentTrainer() {
	assertThatThrownBy(() -> {
	    trainerService.delete(-1);
	});
    }

    @Test
    public void testGetAllTitles() {

	log.debug("Testing trainerService.getAllTitles()");
	assertEquals(6, trainerService.getAllTitles().size());
	assertNotEquals(0, trainerService.getAllTitles().size());
    }

    @Test
    public void testGetAllTrainers() {
	log.debug("Testing trainerService.getAll()");
	assertEquals(12, trainerService.getAll().size());
	assertNotEquals(0, trainerService.getAll().size());
    }

}
