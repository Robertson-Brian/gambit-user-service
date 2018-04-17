package com.revature.gambit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.gambit.entities.Trainee;
import com.revature.gambit.entities.TrainingStatus;

/**
 * Our Spring Data repository that provides the needed custom queries for our
 * microservice.
 * 
 * @author Charles Courtois
 */
public interface TraineeRepository extends JpaRepository<Trainee, Integer> {

	
	List<Trainee> findAllByBatchesAndTrainingStatus(Integer batchId, TrainingStatus trainingStatus);
	
	Trainee findOneByEmail(String email);
	
	Trainee findOneByResourceId(String resourceId);

}
