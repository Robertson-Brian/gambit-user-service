package com.revature.gambit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.gambit.entities.Trainee;

/**
 * Our Spring Data repository that provides the needed custom queries for our
 * microservice.
 * 
 * @author Charles Courtois
 */
public interface TraineeRepository extends JpaRepository<Trainee, Integer> {

	List<Trainee> findAllByBatchesAndTrainingStatus(Integer batchId, String traineeStatus);
	
	Trainee findOneByEmail(String email);
	
	Trainee findOneByResourceId(String resourceId);
	
	List<Trainee> findAll();

}
