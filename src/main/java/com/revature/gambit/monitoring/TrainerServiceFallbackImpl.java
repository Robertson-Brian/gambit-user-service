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
		  log.info("This is the fallback method for TrainerService.findById()."
			  		+ "A list of trainers will be returned back.");	
		  //TO DO LIST: FIND A TRAINER FROM THE STATIC LIST BY TRAINERID
		  return null;
	  }
	  
	  public Trainer findTrainerByEmailFallback(String email){
			log.debug("This is the fallback method for TrainerService.findTrainerByEmailFallback()."
				  		+ "A list of trainers will be returned back.");		
			return  trainers
					.stream()
					.filter((trainer)->email.equals(trainer.getEmail()))
					.findAny()
					.orElse(null);
		  }
	  
	  public Trainer findByNameFallback(String firstName, String lastName){
		  log.info("This is the fallback method for TrainerService.findByName()."
			  		+ "A list of trainers will be returned back.");		
		  return null;
	  }
	  
}
