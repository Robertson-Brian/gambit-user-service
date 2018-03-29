package com.revature.hydra.services;

import java.util.List;

import com.revature.hydra.entities.Trainee;

/**
 * Our Hydra Trainee interface. Provides all of the methods that our
 * implementation will need.
 * 
 * @author Charles Courtois
 *
 */
public interface TraineeService {
	Trainee save(Trainee trainee);

	void update(Trainee trainee);

	void delete(Trainee trainee);

	List<Trainee> findAllByBatchAndStatus(int batchId, String status);

	List<Trainee> getAll();
}
