package com.revature.gambit.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.gambit.entities.Trainee;
import com.revature.gambit.entities.TrainingStatus;
import com.revature.gambit.repositories.TraineeRepository;

@Service("traineeService")
public class TraineeServiceImpl implements TraineeService {

	private static final Logger log = Logger.getLogger(TraineeServiceImpl.class);

	@Autowired
	private TraineeRepository traineeRepository;

	@Transactional
	public Trainee save(Trainee trainee) {
		log.debug("save trainee: " + trainee);
		if(trainee.getFirstName() == "" || trainee.getLastName() == "" || trainee.getEmail() == "") {
			return null;
		}
		// check if trainee already exists
		Trainee preexisting = traineeRepository.findByEmail(trainee.getEmail());
		log.debug("Trainee exists: " + preexisting);
		if (preexisting != null) {
			return null;
		} else {
			return traineeRepository.save(trainee);
		}
	}

	@Transactional
	public Trainee update(Trainee trainee) {
		log.trace("Testing update method for " + trainee);

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
			return traineeRepository.save(trainee);
		}
		return null;
	}

	@Transactional
	public void delete(Trainee trainee) {
		log.debug("TraineeServiceImpl.delete" + trainee);
		traineeRepository.delete(trainee);
	}

	@Transactional
	public List<Trainee> findAllByBatchAndStatus(int batchId, String status) {
		log.debug("Trainee Service recieved request: Finding all by batch: " + batchId + " with status: " + status);
		try {
			TrainingStatus trainingStatus = TrainingStatus.valueOf(status);
		} catch (IllegalArgumentException e) {
			return null;
		}
		return traineeRepository.findAllByBatchesAndTrainingStatus(batchId,TrainingStatus.valueOf(status));
	}

	@Transactional
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
	public Trainee findByEmail(String email) {
		log.trace("findByEmail: " + email);
		return traineeRepository.findByEmail(email);
	}
}
