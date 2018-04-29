package com.revature.gambit.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.gambit.entities.Trainee;
import com.revature.gambit.entities.TrainingStatus;
import com.revature.gambit.messaging.Sender;
import com.revature.gambit.repositories.TraineeRepository;

import static com.revature.gambit.util.MessagingUtil.TOPIC_REGISTER_TRAINEE;
import static com.revature.gambit.util.MessagingUtil.TOPIC_UPDATE_TRAINEE;
import static com.revature.gambit.util.MessagingUtil.TOPIC_DELETE_TRAINEE;

@Service("traineeService")
public class TraineeServiceImpl implements TraineeService {

	private static final Logger log = Logger.getLogger(TraineeServiceImpl.class);

	@Autowired
	private TraineeRepository traineeRepository;
	
	@Autowired
	private Sender sender; // Use this to send messages to other services.

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
			Trainee savedTrainee = traineeRepository.save(trainee);
			if(savedTrainee != null) {
				sender.publish(TOPIC_REGISTER_TRAINEE, savedTrainee);
			}
			return savedTrainee;
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
	public List<Trainee> findAllByBatchAndStatus(int batchId, String status) {
		log.debug("Trainee Service recieved request: Finding all by batch: " + batchId + " with status: " + status);
		return traineeRepository.findAllByBatchesAndTrainingStatus(batchId,TrainingStatus.valueOf(status));
	}

	@Transactional
	public List<Trainee> getAll() {
		log.debug("findAll Trainees.");
		return traineeRepository.findAll();
	}

	@Transactional
	public Trainee findByEmail(String email) {
		log.trace("findByEmail: " + email);
		if(traineeRepository.findByEmail(email)!=null)
			return traineeRepository.findByEmail(email);
		else
			return null;
	}
}
