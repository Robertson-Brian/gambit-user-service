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
		log.trace("save trainee: " + trainee);
		// check if trainee already exists
		Trainee preexisting = traineeRepository.findOneByEmail(trainee.getEmail());
		log.trace("Trainee exists: " + preexisting);
		if (preexisting != null && preexisting.getBatches() != null) {
			// if so, add the trainee's batch assignments
			log.trace("adding prexisting batches: " + preexisting.getBatches() + " to new batches: "
					+ trainee.getBatches());
			trainee.getBatches().addAll(preexisting.getBatches());
			// maintain their Salesforce resourceId
			log.trace("setting resourceId for trainee as: " + preexisting.getResourceId());
			trainee.setResourceId(preexisting.getResourceId());
			trainee.setUserId(preexisting.getUserId());
			return traineeRepository.save(trainee);
		} else {
			return traineeRepository.save(trainee);
		}
	}

	@Transactional
	public List<Trainee> findAllByBatchAndStatus(int batchId, String status) {
		log.debug("Trainee Service recieved request: Finding all by batch: " + batchId + " with status: " + status);
		return traineeRepository.findAllByBatchesAndTrainingStatus(batchId,TrainingStatus.valueOf(status));
	}
	
	@Transactional
	public void delete(int traineeId) {
		Trainee trainee = traineeRepository.findOne(traineeId);
		log.trace("Deleting trainee: " + trainee);
		traineeRepository.delete(trainee);
	}

	@Transactional
	public List<Trainee> getAll() {
		log.trace("findAll Trainees.");
		return traineeRepository.findAll();
	}
	
	@Transactional
	public Trainee findByEmail(String email) {
		log.trace("findByEmail: " + email);
		if (email == null) {
			return null;
		}
		return traineeRepository.findOneByEmail(email);
	}

}
