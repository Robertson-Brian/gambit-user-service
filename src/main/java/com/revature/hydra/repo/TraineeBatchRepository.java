package com.revature.hydra.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.hydra.entities.TraineeBatch;

public interface TraineeBatchRepository extends JpaRepository<TraineeBatch, Integer>{

	@SuppressWarnings("unchecked")
	TraineeBatch save(TraineeBatch traineeBatch);
}
