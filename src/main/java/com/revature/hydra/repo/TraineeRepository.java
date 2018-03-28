package com.revature.hydra.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.hydra.entities.Trainee;

/**
 * Our Spring Data repository that provides the needed custom queries for our
 * microservice.
 * 
 * @author Charles Courtois
 */
public interface TraineeRepository extends JpaRepository<Trainee, Integer> {
	// A custom query used in the findAllByBatch method in the TraineeServiceImpl
	// class.
	List<Trainee> findAllByBatchBatchIdAndTrainingStatusNot(Integer batchId, String status);

	// A custom query used in the findDroppedByBatch method in the
	// TraineeServiceImpl class.
	List<Trainee> findAllByBatchBatchIdAndTrainingStatus(Integer batchId, String status);

	Trainee findOneByResourceId(String asString);

	// Supposed to be used by ListenerRepoDispatcher
	List<Trainee> findAllByBatchBatchId(int asInt);
}
