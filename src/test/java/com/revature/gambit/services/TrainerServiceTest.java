package com.revature.gambit.services;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.hamcrest.CoreMatchers;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.revature.gambit.GambitTest;
import com.revature.gambit.entities.Trainer;

public class TrainerServiceTest extends GambitTest {

    private static final Logger log = Logger.getLogger(TrainerServiceTest.class);

    @Autowired
    private TrainerService trainerService;

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
	int patrickId = trainerService.findTrainerByEmail("patrick.walsh@revature.com").getUserId();
	trainerService.delete(patrickId);
	assertNull(trainerService.findById(patrickId));
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

    @Test 
    public void testFindTrainerByEmail() {
	log.debug("Testing trainerService.findTrainerByEmail with correct email address");
	String expected = "steven.kelsey@revature.com";
	Trainer trainer = trainerService.findTrainerByEmail("steven.kelsey@revature.com");
	assertEquals(trainer.getEmail(), expected);
    }

    @Test 
    public void testFindTrainerByEmailInvalid() {
	log.debug("Testing trainerService.findTrainerByEmail with invalid email address");
	Trainer trainer = trainerService.findTrainerByEmail("fdjnfjdd@revature.com");
	assertEquals(trainer, null);
    }

    @Test
    public void testFindTrainerByEmailNonTrainer() {
	log.debug("Testing trainerService.findTrainerByEmail with non-trainer email address");
	Trainer trainer = trainerService.findTrainerByEmail("ychenq001@gmail.com");
	assertEquals(trainer, null);
    }
    
    @Test 
	public void testUpdate(){
		log.debug("Testing trainer update)");
		Trainer targetTrainer = trainerService.findById(trainerService.findTrainerByEmail("patrick.walsh@revature.com").getUserId());
		log.trace("targetTrainer ="+targetTrainer);
		assertEquals("Patrick",targetTrainer.getFirstName());
		
		targetTrainer.setEmail("np4@hotmail.com");
		targetTrainer.setTitle("Technology Manager");
		Trainer updateTargetTrainer =trainerService.update(targetTrainer);
		log.trace("updateTargetTrainer = " + updateTargetTrainer);
		List<String> updatedList = Arrays.asList("Patrick","Walsh","np4@hotmail.com","Technology Manager");
		assertThat(updatedList,CoreMatchers.hasItems(updateTargetTrainer.getFirstName(),updateTargetTrainer.getLastName(),updateTargetTrainer.getEmail(),updateTargetTrainer.getTitle()));
	
		updateTargetTrainer.setFirstName("Stev");
		updateTargetTrainer.setLastName("Johns");
		trainerService.update(updateTargetTrainer);
		log.trace("updateTargetTrainer second time = " + updateTargetTrainer);
		List<String> newUpdatedList = Arrays.asList("Steve","Johns","np4@hotmail.com","Technology Manager");
		assertThat(newUpdatedList,CoreMatchers.hasItems(updateTargetTrainer.getFirstName(),updateTargetTrainer.getLastName(),updateTargetTrainer.getEmail(),updateTargetTrainer.getTitle()));
		assertNotEquals("steves",updateTargetTrainer.getFirstName());
	}


}
