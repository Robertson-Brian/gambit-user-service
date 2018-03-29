package com.revature.hydra.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.hydra.entities.BatchTrainer;

public interface TrainerRepository extends JpaRepository<BatchTrainer, Integer> {

	BatchTrainer findByTrainerId(Integer trainerId);

	BatchTrainer findByUserId(Integer userId);

	@Query(value = "SELECT DISTINCT TITLE FROM TRAINER", nativeQuery = true)
	List<String> findTitles();

}
