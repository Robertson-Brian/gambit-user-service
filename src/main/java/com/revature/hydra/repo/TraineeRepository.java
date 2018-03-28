package com.revature.hydra.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.hydra.entities.Trainee;

/**
 * Our Spring Data repository that provides the needed custom queries for our
 * microservice.
 * 
 * @author Charles Courtois
 */
public interface TraineeRepository extends JpaRepository<Trainee, Integer> {
	
	@Query(nativeQuery = true, value="SELECT trainee_batch.trainee_id FROM TRAINEE_BATCH, trainee WHERE TRAINEE_BATCH.trainee_id = :t_id AND trainee.training_status = :t_status")
	List<Trainee> findAllByBatchAndStatus(@Param("t_id") Integer t_id, @Param("t_status") String t_status);
//	// A custom query used in the findAllByBatch method in the TraineeServiceImpl
//	// class.
//	List<Trainee> findAllByBatchBatchIdAndTrainingStatusNot(Integer batchId, String status);
//
//	// A custom query used in the findDroppedByBatch method in the
//	// TraineeServiceImpl class.
//	List<Trainee> findAllByBatchBatchIdAndTrainingStatus(Integer batchId, String status);

	Trainee findOneByResourceId(String asString);
}
