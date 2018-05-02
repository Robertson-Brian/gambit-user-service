package com.revature.gambit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.gambit.entities.Trainee;
import com.revature.gambit.entities.TrainingStatus;

/**
 * Our Spring Data repository that provides the needed custom queries for our
 * microservice.
 * 
 * @author Charles Courtois
 */
@Transactional
public interface TraineeRepository extends JpaRepository<Trainee, Integer> {

	/**
	 * 
	 * @param batchId - number of which batch to search
	 * @param trainingStatus - status of trainee to search
	 * @return List of all trainees in batchId with trainingstatus
	 */
	List<Trainee> findAllByBatchesAndTrainingStatus(Integer batchId, TrainingStatus trainingStatus);
	
	Trainee findByUserId(int userId);

	/**
	 * 
	 * @param batchId - number of which batch to search
	 * @Author Alejandro Iparraguirre
	 * @return List of all trainees in batchId
	 */
	List<Trainee> findAllByBatches(Integer batchId);
	
	Trainee findByEmail(String email);
	
	Trainee findOneByResourceId(String resourceId);

	void delete(Trainee trainee);
}
