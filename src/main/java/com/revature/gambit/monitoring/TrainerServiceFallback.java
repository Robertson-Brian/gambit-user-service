package com.revature.gambit.monitoring;

import java.util.List;

import com.revature.gambit.entities.Trainer;
/**
 * 
 * @author Jing Yu
 *
 */
public interface TrainerServiceFallback {
	
	  /**
	  * Execute this method once the application is fully startup
	  */
	  public void init();
	  
	  /**
	   * 
	   * @return
	   */
	  public List<Trainer> getAllFallback();
	  
	  /**
	   * 
	   * @return
	   */
	  public List<String> getTitlesFallback();
	  
	  /**
	   * 
	   * @param trainerId
	   * @return
	   */
	  public Trainer findByIdFallback(Integer trainerId);
	  
	  /**
	   * 
	   * @param email
	   * @return
	   */
	  public Trainer findTrainerByEmailFallback(String email);
	  
	  /**
	   * 
	   * @param firstName
	   * @param lastName
	   * @return
	   */
	  public Trainer findByNameFallback(String firstName, String lastName);
}
