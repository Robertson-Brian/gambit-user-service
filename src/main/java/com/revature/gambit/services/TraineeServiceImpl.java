package com.revature.gambit.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.gambit.entities.Trainee;
import com.revature.gambit.repositories.TraineeRepository;

@Service("traineeService")
public class TraineeServiceImpl implements TraineeService {

	private static final Logger log = Logger.getLogger(TraineeServiceImpl.class);

	@Autowired
	private TraineeRepository traineeRepository;

	@Transactional
	public Trainee save(Trainee trainee) {
		log.debug("save trainee: " + trainee);
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
	public List<Trainee> findAllByBatchAndStatus(int batchId, String status) {
		log.trace("Find all by batch: " + batchId + " with status: " + status);
		return traineeRepository.findAllByBatchesAndTrainingStatus(batchId, status);
	}
	
	@Transactional
	public void delete(Trainee trainee) {
		log.debug("TraineeServiceImpl.delete" + trainee);
		traineeRepository.delete(trainee);
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
