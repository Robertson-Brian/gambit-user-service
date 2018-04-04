package com.revature.hydra.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.hydra.entities.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

	Trainer findByTrainerId(Integer trainerId);

	Trainer findByUserId(Integer userId);

	@Query(value = "SELECT DISTINCT TITLE FROM TRAINER", nativeQuery = true)
	List<String> findTitles();

}
