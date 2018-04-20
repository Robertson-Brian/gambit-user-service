package com.revature.gambit.monitoring;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.gambit.entities.Trainer;
import com.revature.gambit.repositories.TrainerRepository;

/**
 * This class provides fall back methods to read only methods
 * in TrainerService.java
 * 
 * @author Jing Yu
 *
 */

@Component
public class TrainerServiceFallbackImpl implements TrainerServiceFallback {

	private static final Logger log = Logger.getLogger(TrainerServiceFallbackImpl.class);

	@Autowired
	private TrainerRepository trainerRepository;	
	
	private List<Trainer> trainers;
	 /**
	  * Execute this method once the application is fully startup
	  */
	  public void init(){
		  log.debug("Load a list of all trainers when the application starts up.");
		  trainers = trainerRepository.findAll();
		  log.info("Static trainer list size: "+trainers);		
	  }
	  
	  public List<Trainer> getAllFallback(){
		  log.info("This is the fallback method for TrainerService.getAll()."
		  		+ "A list of trainers will be returned back.");		  
		  return trainers;
	  }
	  
	  public List<String> getTitlesFallback(){
		  log.info("This is the fallback method for TrainerService.getAllTitles()."
			  		+ "A list of titles will be returned back.");
		  List<String> titles = new ArrayList<>();
		  for(Trainer trainer : trainers){
			  titles.add(trainer.getTitle());
		  }
		  return titles;
	  }
	  
	  public Trainer findByIdFallback(Integer trainerId){
		  return null;
	  }
	  
	  public Trainer findTrainerByEmailFallback(String email){
		  return null;
	  }
	  
	  public Trainer findByNameFallback(String firstName, String lastName){
		  return null;
	  }
	  
}
