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

	List<Trainee> findAllByBatch(int batchId);

	List<Trainee> findDroppedByBatch(int batchId);

	void update(Trainee trainee);

	void delete(Trainee trainee);
}
