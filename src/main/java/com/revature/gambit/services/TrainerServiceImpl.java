package com.revature.gambit.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.revature.gambit.entities.Trainer;
import com.revature.gambit.entities.User;
import com.revature.gambit.repositories.TrainerRepository;
import com.revature.gambit.repositories.UserRepository;


@Service("trainerService")
public class TrainerServiceImpl implements TrainerService {

	@Autowired
	public TrainerRepository trainerRepository;

	@Autowired
	private UserRepository userRepository;

	private static final Logger log = Logger.getLogger(TrainerServiceImpl.class);
	
	private List<Trainer> trainers;

	public void init(){
		  log.debug("Load a list of all trainers when the application starts up.");
		  trainers = trainerRepository.findAll();
		  log.info("Static trainer list size: "+trainers);		
	  }
	
	public void delete(Integer id) {
		log.debug("Method called to delete a trainer.");
		trainerRepository.delete(id);
	}

	@HystrixCommand(fallbackMethod = "findByIdFallback")
	public Trainer findById(Integer trainerId) {
		log.debug("Method called to find Trainer by ID with id: " + trainerId);
		return trainerRepository.findByUserId(trainerId);
	}

	public Trainer newTrainer(Trainer trainer) {
		log.debug("Method called to create a new trainer from Trainer object.");
		if(trainer == null ||
				trainer.getEmail() == null || trainer.getFirstName() == null || trainer.getLastName() == null || trainer.getTitle() == null ||
				trainer.getEmail() == "" || trainer.getFirstName() == "" || trainer.getLastName() == "" || trainer.getTitle() == "" ||
				findTrainerByEmail(trainer.getEmail()) != null) {
			return null;
		}
		return trainerRepository.save(trainer);
	}

	public Trainer promoteToTrainer(User user, String title) {
		log.debug("Method called to promote a user to a trainer.");
		if(user == null) {
			return null;
		}
		User baseUser;
		log.trace("Finding user by email");
		if((baseUser = userRepository.findByEmail(user.getEmail())) == null) {
			log.trace("Finding user by name");
			if((baseUser = userRepository.findUserByFirstNameAndLastName(user.getFirstName(), user.getLastName())) == null) {
				log.trace("User does not exist in the database");
				return null;
			}
		}
		userRepository.delete(baseUser.getUserId());
		Trainer userToPromote = new Trainer(baseUser,title);
		return this.newTrainer(userToPromote);
	}

	public Trainer update(Trainer trainer) {
		log.debug("Method called to update a trainer first name, last name, email, and title");
		Trainer updatingTrainer = trainerRepository.findByUserId(trainer.getUserId());
		BeanUtils.copyProperties(trainer, updatingTrainer,"userId");
		return trainerRepository.save(updatingTrainer);
	}
	
	@HystrixCommand(fallbackMethod = "findTrainerByEmailFallback")
	public Trainer findTrainerByEmail(String email) {
		log.debug("Method called to findTrainerByEmail with email: " + email);
		if(email !=null)
			throw new RuntimeException("break it");
		return trainerRepository.findByEmail(email);

	}
    
	@HystrixCommand(fallbackMethod = "getTitlesFallback")
	public List<String> getAllTitles() {
		log.debug("Method called to list all titles.");
		return trainerRepository.findDistinctTitle();
	}

	@HystrixCommand(fallbackMethod = "getAllFallback")
	public List<Trainer> getAll() {
		log.debug("Method called to get all trainers.");	
		return trainerRepository.findAll();
	}
	
    @HystrixCommand(fallbackMethod = "findByNameFallback")
	public Trainer findByName(String firstName, String lastName) {
		log.debug("Method called to get findByName.");
		return trainerRepository.findTrainerByFirstNameAndLastName(firstName, lastName);
	}
	
	
	/*
	 * Below are all fallback methods
	 */
	
	@SuppressWarnings("unused")
	private List<Trainer> getAllFallback(){
		log.debug("This is the fallback method for TrainerService.getAll()."
		  		+ "A list of trainers will be returned back.");		  
		return trainers;
	  }
   
	public List<String> getTitlesFallback(){
		log.debug("This is the fallback method for TrainerService.getAllTitles()."
			  		+ "A list of titles will be returned back.");
		List<String> titles = new ArrayList<>();
		  for(Trainer trainer : trainers){
			  titles.add(trainer.getTitle());
		  }
		return titles;
	  }
	
	public Trainer findByIdFallback(Integer trainerId){
		log.debug("This is the fallback method for TrainerService.findById()."
			  		+ "A list of trainers will be returned back.");		    
		return  trainers
				.stream()
	            .filter((trainer)->trainerId == trainer.getUserId())
	            .findAny()
	            .orElse(null);
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
		log.debug("This is the fallback method for TrainerService.findByName()."
			  		+ "A list of trainers will be returned back.");		
		return  trainers
				.stream()
				.filter(
				(trainer)->
				(firstName+lastName)
				.equals
				(trainer.getFirstName()+trainer.getLastName()))
				.findAny()
				.orElse(null);
	  }
}