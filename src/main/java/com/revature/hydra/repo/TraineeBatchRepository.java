package com.revature.hydra.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.hydra.entities.TraineeBatch;

public interface TraineeBatchRepository extends JpaRepository<TraineeBatch, Integer>{

	void deleteByTraineeId(int traineeId);

//	@SuppressWarnings("unchecked")
//	TraineeBatch save(TraineeBatch traineeBatch);
	
	
}
