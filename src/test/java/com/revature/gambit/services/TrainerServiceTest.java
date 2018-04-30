package com.revature.gambit.services;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

import com.revature.gambit.entities.Trainer;
import com.revature.gambit.entities.User;
import com.revature.gambit.messaging.KafkaTest;

/**
 * Tests for inserting, updating, retrieving and deleting Trainers.
 */
public class TrainerServiceTest extends KafkaTest {


    private static final Logger log = Logger.getLogger(TrainerServiceTest.class);

    @Autowired
    private TrainerService trainerService;

    /**
     * Tests that a trainee can be promoted to trainer.
     * 
     * @author Mark Fleres
     */
    @Test
    public void testPromoteTrainer() {
    	String title = "Trainer";
    	User userToPromote = new User("Laut","Daniel","dlaut1@hotmail.com");
    	log.trace("trainerToPromote.id = " + userToPromote.getUserId());
    	assertNotEquals(null, userToPromote);
    	
    	Trainer promotedTrainer = trainerService.promoteToTrainer(userToPromote, title);
    	assertNotEquals(null,promotedTrainer);
    	
    	assertEquals(title,promotedTrainer.getTitle());
    	
    	assertEquals(promotedTrainer.getFirstName(),userToPromote.getFirstName());
    	
    	assertNotEquals(null,trainerService.findTrainerByEmail("dlaut1@hotmail.com"));
    }
    
    /**
     * Tests that a trainee can be promoted using only email.
     * 
     * @author Mark Fleres
     */
    @Test
    public void testPromoteTrainerWithOnlyEmail() {
    	String title = "Trainer";
    	User userToPromote = new User("","","dlaut1@hotmail.com");
    	log.trace("trainerToPromote.id = " + userToPromote.getUserId());
    	assertNotEquals(null, userToPromote);
    	
    	Trainer promotedTrainer = trainerService.promoteToTrainer(userToPromote, title);
    	assertNotEquals(null,promotedTrainer);
    	
    	assertEquals(title,promotedTrainer.getTitle());
    	
    	assertEquals("Laut",promotedTrainer.getFirstName());
    	
    	assertNotEquals(null,trainerService.findTrainerByEmail("dlaut1@hotmail.com"));
    }
    
    /**
     * Tests that a trainee can be promoted to trainer with only name.
     * 
     * @author Mark Fleres
     */
    @Test
    public void testPromoteTrainerWithOnlyName() {
    	String title = "Trainer";
    	User userToPromote = new User("Laut","Daniel","");
    	log.trace("trainerToPromote.id = " + userToPromote.getUserId());
    	assertNotEquals(null, userToPromote);
    	
    	Trainer promotedTrainer = trainerService.promoteToTrainer(userToPromote, title);
    	assertNotEquals(null,promotedTrainer);
    	
    	assertEquals(title,promotedTrainer.getTitle());
    	
    	assertEquals(promotedTrainer.getFirstName(),userToPromote.getFirstName());
    	
    	assertNotEquals(null,trainerService.findTrainerByEmail("dlaut1@hotmail.com"));
    }
    
    /**
     * Tests that a null trainee cannot be promoted.
     * 
     * @author Mark Fleres
     */
    @Test
    public void testPromoteEmptyTrainer() {
    	String title = "Trainer";
    	User userToPromote = new User("","","");
    	Trainer promotedTrainer = trainerService.promoteToTrainer(userToPromote, title);
    	assertEquals(null,promotedTrainer);
    }
    
    /**
     * Tests that a trainer is created successfully.
     * 
     * @author Mark Fleres
     */
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
    }
    
    /**
     * Tests the same trainer can not be created again.
     * 
     * @author Mark Fleres
     */
    @Test
    public void testNewTrainerRepeat() {
    	// Test Repeat Trainer
    	Trainer repeatTrainer = new Trainer("Patrick","Walsh","patrick.walsh@revature.com","Trainer");
    	Trainer savedRepeatTrainer = trainerService.newTrainer(repeatTrainer);
    	assertEquals(null, savedRepeatTrainer);
    }
    
    /**
     * Tests an empty trainer cannot be created.
     * 
     * @author Mark Fleres
     */
    @Test
    public void testNewTrainerEmpty() {
    	// Test Empty Trainer
    	Trainer emptyTrainer = new Trainer("", "", "", "");
    	Trainer savedEmptyTrainer = trainerService.newTrainer(emptyTrainer);
    	assertEquals(null, savedEmptyTrainer);
    }

    /**
     * Tests delete a trainer.
     * 
     * @author Raymond Xia
     */
    @Test 
    public void testDeleteTrainer() {
    	log.debug("Testing trainerService.delete()");
    	int patrickId = trainerService.findTrainerByEmail("patrick.walsh@revature.com").getUserId();
    	trainerService.delete(patrickId);
    	assertNull(trainerService.findById(patrickId));
    }

    /**
     * Test delete a non-existing trainer.
     * 
     * @author Raymond Xia
     */
    @Test 
    public void testDeleteNonexistentTrainer() {
    	assertThatThrownBy(() -> {
    		trainerService.delete(-1);
    	});
    }

    /**
     * Tests to retrieve all titles: 'Lead Trainer','Vice President of Technology'
     * 'Technology Manager','Senior Java Developer','Trainer','Senior Trainer'.
     * 
     * @author Jing Yu
     */
    @Test 
    public void testGetAllTitles() {
    	log.debug("Testing trainerService.getAllTitles()");
    	assertEquals(6, trainerService.getAllTitles().size());
    	assertNotEquals(0, trainerService.getAllTitles().size());
    }

    /**
     * Tests to retrieve all trainers.
     * 
     * @author Jing Yu
     */
    @Test 
    public void testGetAllTrainers() {
    	log.debug("Testing trainerService.getAll()");
    	List<Trainer> listTrainer = trainerService.getAll();
		for(Trainer trainer : listTrainer){
			assertEquals(trainer, trainerService.findTrainerByEmail(trainer.getEmail())); 
		}		
	}
    
    /**
     * Tests that get all Trainers does not return a null.
     * 
     * @author Jing Yu
     */
    @Test
	public void getAllNotNull(){
		log.debug("Testing that a table exists.");
		assertNotNull(trainerService.getAll());
		log.trace("Trainer Table Exists.");
	}	

    /**
     * Tests trainer retrieval by valid email address.
     * 
     * @author Jeffrey Reyes
     */
	@Test
	public void testFindTrainerByEmail() {
		log.debug("Testing trainerService.findTrainerByEmail with correct email address");
		String expected = "steven.kelsey@revature.com";
		Trainer trainer = trainerService.findTrainerByEmail("steven.kelsey@revature.com");
		assertEquals(trainer.getEmail(), expected);
	}

	/**
	 * Tests that trainer retrieval fails with an invalid email address.
	 * 
	 * @author Jeffrey Reyes
	 */
	@Test
	public void testFindTrainerByEmailInvalid() {
		log.debug("Testing trainerService.findTrainerByEmail with invalid email address");
		Trainer trainer = trainerService.findTrainerByEmail("jefrey@revature.com");
		assertEquals(trainer, null);
	}
	
    /**
     * Tests that trainer retrieval fails with an non-trainer email.
     * 
     * @author Jeffrey Reyes
     */
	@Test
	public void testFindTrainerByEmailNonTrainer() {
		log.debug("Testing trainerService.findTrainerByEmail with non-trainer email address");
		Trainer trainer = trainerService.findTrainerByEmail("ychenq001@gmail.com");
		assertEquals(trainer, null);
    }
	
	/**
	 * Test trainer retrieval by id.
	 * 
	 * @author Junyu Chen
	 */
	@Test                                                                                                                                                                                                                                                                                                                                                                                                        
    public void findTrainerById(){
        log.debug("Find trainer by id");
        Trainer newTrainer = new Trainer("Patrick","Walsh","patrick.walsh@revature.com","Lead Trainer");
         int userId = trainerService.findTrainerByEmail("patrick.walsh@revature.com").getUserId();
            Trainer findById = trainerService.findById(userId);
      
        assertEquals(newTrainer.getFirstName(), findById.getFirstName());
        assertEquals(newTrainer.getLastName(), findById.getLastName());
        assertEquals(newTrainer.getEmail(), findById.getEmail());
        assertEquals(newTrainer.getTitle(), findById.getTitle());
 }
    
	/**
	 * Tests updating a trainer.
	 * 
	 * @author Nikhil Pious
	 */
    @Test 
	public void testUpdate() {
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
	
		updateTargetTrainer.setFirstName("Steve");
		updateTargetTrainer.setLastName("Johns");
		Trainer trainer = trainerService.update(updateTargetTrainer);
		log.trace("updateTargetTrainer second time = " + updateTargetTrainer);
		List<String> newUpdatedList = Arrays.asList("Steve","Johns","np4@hotmail.com","Technology Manager");
		assertThat(newUpdatedList,CoreMatchers.hasItems(trainer.getFirstName(), trainer.getLastName(),trainer.getEmail(),trainer.getTitle()));
		assertNotEquals("steves",trainer.getFirstName());
	}
    
    /**
     * Tests trainer retrieval by name.
     * 
     * @author Jeffrey Reyes
     */
    @Test
    public void testFindByName() {
    	log.debug("Testing findByName with valid trainer.");
    	Trainer trainer = trainerService.findByName("Steven", "Kelsey");
    	assertEquals(trainer.getFirstName(), "Steven");
    	assertEquals(trainer.getLastName(), "Kelsey");
    }
    
    /**
     * Tests trainer retrieval fails with an invalid trainer .
     * 
     * @author Jeffrey Reyes
     */
    @Test
    public void testFindByNameInvalidTrainer() {
    	log.debug("Testing findByName with invalid trainer.");
    	Trainer trainer = trainerService.findByName("jeff", "rey");
    	assertEquals(trainer, null);
    }
    
    /**
     * Tests trainer retrieval fails with trainee input.
     * 
     * @author Jeffrey Reyes
     */
    @Test
    public void testFindByNameNonTrainer() {
    	log.debug("Testing findByName with non trainer.");
    	Trainer trainer = trainerService.findByName("Chen", "Yan");
    	assertEquals(trainer, null);
    }
    


}
