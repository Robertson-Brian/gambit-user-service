package com.revature.gambit.services;

import static com.revature.gambit.util.MessagingUtil.TOPIC_DELETE_TRAINEE;
import static com.revature.gambit.util.MessagingUtil.TOPIC_REGISTER_TRAINEE;
import static com.revature.gambit.util.MessagingUtil.TOPIC_UPDATE_TRAINEE;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.revature.gambit.entities.Trainee;
import com.revature.gambit.entities.TrainingStatus;
import com.revature.gambit.messaging.Sender;
import com.revature.gambit.repositories.TraineeRepository;

@Service("traineeService")
public class TraineeServiceImpl implements TraineeService {

	private static final Logger log = Logger.getLogger(TraineeServiceImpl.class);

	@Autowired
	private TraineeRepository traineeRepository;
	
	@Autowired
	private Sender sender; // Use this to send messages to other services.

	public List<Trainee>  traineeList;

	public void init(){
		log.debug("Loading All Trainees when Application loads");
		traineeList = traineeRepository.findAll();
		log.info("All Trainee information"+ traineeList);
	}


	@Transactional
	public Trainee save(Trainee trainee) {
		log.debug("save trainee: " + trainee);
		if(trainee.getFirstName() == "" || trainee.getLastName() == "" || trainee.getEmail() == "") {
			return null;
		}
		// check if trainee already exists
		Trainee preexisting = traineeRepository.findByEmail(trainee.getEmail());
		log.trace("Trainee exists: " + preexisting);
		if (preexisting != null) {
			return null;
		} else {
			Trainee savedTrainee = traineeRepository.save(trainee);
			if(savedTrainee != null) {
				sender.publish(TOPIC_REGISTER_TRAINEE, savedTrainee);
			}
			return savedTrainee;
		}
	}

	@Transactional
	public Trainee update(Trainee trainee) {
		log.debug("Testing update method for " + trainee);

		Trainee preexisting = traineeRepository.findByEmail(trainee.getEmail());
		log.trace("Trainee exists: " + preexisting);
		if (preexisting != null) {
			// if so, add the trainee's batch assignments
			log.trace("adding prexisting batches: " + preexisting.getBatches() + " to new batches: "
					+ trainee.getBatches());
			trainee.getBatches().addAll(preexisting.getBatches());
			// maintain their Salesforce resourceId
			log.trace("setting resourceId for trainee as: " + preexisting.getResourceId());
			trainee.setResourceId(preexisting.getResourceId());
			trainee.setUserId(preexisting.getUserId());
			Trainee updatedTrainee = traineeRepository.save(trainee);
			if(updatedTrainee != null) {
				sender.publish(TOPIC_UPDATE_TRAINEE, updatedTrainee);
			}
			return updatedTrainee;
		}
		return null;
	}

	@Transactional
	public void delete(Trainee trainee) {
		log.debug("TraineeServiceImpl.delete" + trainee);
		traineeRepository.delete(trainee);
		sender.publish(TOPIC_DELETE_TRAINEE, trainee);
	}

	@Transactional
	@HystrixCommand(fallbackMethod="findAllByBatchAndStatusFallBack")
	public List<Trainee> findAllByBatchAndStatus(int batchId, String status) {
		log.debug("Trainee Service recieved request: Finding all by batch: " + batchId + " with status: " + status);
		TrainingStatus trainingStatus;
		try {
			trainingStatus = TrainingStatus.valueOf(status);
		} catch (Exception e) {
			return null;
		}
		return traineeRepository.findAllByBatchesAndTrainingStatus(batchId,trainingStatus);
	}


	@Transactional
	public List<Trainee> findAllByBatch(int batchId) {
		log.debug("Trainee Service recieved request: Finding all by batch: " + batchId);
		return traineeRepository.findAllByBatches(batchId);
	}
	
	@Transactional
	@HystrixCommand(fallbackMethod="getAllFallBack")
	public List<Trainee> getAll() {
		log.debug("findAll Trainees.");
		return traineeRepository.findAll();
	}
	
	@Transactional
	public Trainee findByUserId(int userId) {
		log.debug("Finding Trainee by userId: " + userId);
		return traineeRepository.findByUserId(userId);
	}

	@Transactional
	@HystrixCommand(fallbackMethod="findByEmailFallBack")
	public Trainee findByEmail(String email) {
		log.debug("findByEmail: " + email);
		if(traineeRepository.findByEmail(email)!=null)
			return traineeRepository.findByEmail(email);
		else
			return null;
	}


	/* FallBack methods for Read Operation */

	public List<Trainee> findAllByBatchAndStatusFallBack(int batchId, String status){
		log.info("FallBack Method For findAllByBatchAndStatus");
		return traineeList.stream()
				.filter(
						(trainee)->	
						(trainee.getBatches().contains(batchId))
						&&
						(TrainingStatus.valueOf(status))
						.equals(trainee.getTrainingStatus()))
				.collect(Collectors.toList());

	}


	public List<Trainee> getAllFallBack(){
		log.debug("If getAll goes wrong, this Fallback executes");
		return traineeList;
	}

	public Trainee findByEmailFallBack(String email){
		log.debug("FallBack for find trainee by Email");

		return traineeList.stream()
				.filter(
						(trainee)->
						email.equals(trainee.getEmail()))
				.findAny()
				.orElse(null);
	}

}
