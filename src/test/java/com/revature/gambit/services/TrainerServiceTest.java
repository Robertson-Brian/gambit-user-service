package com.revature.gambit.services;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TrainerServiceTest {

	private static final Logger logger = Logger.getLogger(TrainerServiceTest.class);

	@Autowired
	private TrainerService trainerService;
	
	@Test
	public void save() {
		
	}
}
