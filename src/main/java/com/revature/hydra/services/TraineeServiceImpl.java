package com.revature.hydra.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.hydra.entities.Trainee;
import com.revature.hydra.repo.TraineeRepository;

/**
 * Our Hydra Trainee Service implementation class. Implements all of the methods
 * defined in the TraineeService interface.
 * 
 * @author Charles Courtois
 *
 */
@Service
public class TraineeServiceImpl implements TraineeService {
	@Autowired
	TraineeRepository traineeRepo;

	/**
	 * The implemented method to create a new trainee.
	 */
	@Override
	@Transactional
	public Trainee save(Trainee trainee) {
		// Trainee id must be 0 to create a new trainee
		trainee.setTraineeId(0);
		return traineeRepo.save(trainee);
	}

	/**
	 * The implemented method to find all trainees in the batch with the provided
	 * batchId.
	 */
	@Override
	@Transactional
	public List<Trainee> findAllByBatch(int batchId) {
		return traineeRepo.findAllByBatchBatchIdAndTrainingStatusNot(batchId, "Dropped");
	}

	/**
	 * The implemented method to find all dropped trainees in the batch with the
	 * provided batchId.
	 */
	@Override
	@Transactional
	public List<Trainee> findDroppedByBatch(int batchId) {
		return traineeRepo.findAllByBatchBatchIdAndTrainingStatus(batchId, "Dropped");
	}

	/**
	 * The implemented method to update a trainee.
	 */
	@Override
	@Transactional
	public void update(Trainee trainee) {
		traineeRepo.save(trainee);
	}

	/**
	 * The implemented method to delete a trainee.
	 */
	@Override
	@Transactional
	public void delete(Trainee trainee) {
		traineeRepo.delete(trainee);

	}

}
