package com.revature.gambit.monitoring;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.gambit.entities.Trainer;
import com.revature.gambit.services.TrainerService;

/**
 * This class provides fall back methods to read only methods
 * in TrainerService.java
 * 
 * @author Jing Yu
 *
 */
//@Component
public class TrainerFallback {

//	private static final Logger log = Logger.getLogger(TrainerFallback.class);
//
//	//@Autowired
//	//private TrainerService trainerService;
//	
//	private static List<Trainer> trainers;
//	 /**
//	  * Execute this method once the application is fully startup
//	  */
//	  public void init(){
//		  log.debug("Load a list of all trainers when the application starts up.");
//		//  trainers = trainerService.getAll();
//		//  log.trace("Static trainer list size: "+trainers.size());
//	  }
}
