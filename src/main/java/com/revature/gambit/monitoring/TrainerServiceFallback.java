package com.revature.gambit.monitoring;

import java.util.List;

import com.revature.gambit.entities.Trainer;
/**
 * 
 * @author Jing Yu
 *
 */
public interface TrainerServiceFallback {

	  public void init();
	  
	  public List<Trainer> getAllFallback();
	  
	  public List<String> getTitlesFallback();
	  
	  public Trainer findByIdFallback(Integer trainerId);
	  
	  public Trainer findTrainerByEmailFallback(String email);
	  
	  public Trainer findByNameFallback(String firstName, String lastName);
}
