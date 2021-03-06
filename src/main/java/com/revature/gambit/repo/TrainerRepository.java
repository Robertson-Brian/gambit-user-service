package com.revature.gambit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.gambit.entities.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

	public Trainer findByUserId(int userId);

	@Query("SELECT DISTINCT title FROM Trainer")
	public List<String> findDistinctTitle();

}
