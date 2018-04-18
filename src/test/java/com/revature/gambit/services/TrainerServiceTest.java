package com.revature.gambit.services;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.gambit.entities.Trainer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrainerServiceTest {
	
	private static final Logger log = Logger.getLogger(TrainerServiceTest.class);
	
	@Autowired
	private TrainerService trainerService; 
	@Test																																																																																																		
	public void findTrainerById(){
		Trainer trainer = new Trainer();
		trainer.setFirstName("Genesis");
		trainer.setLastName("Bonds");
		trainer.setEmail("genesis.bonds@revature.com");
		System.out.println(trainer);
		Trainer findById = trainerService.findById(6);
System.out.println(findById);
		assertEquals(trainer,findById);
		
	}
		
		
	}


