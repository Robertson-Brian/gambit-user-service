package com.revature.gambit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.revature.gambit.entities.Trainer;

@Transactional
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

    public Trainer findByUserId(int userId);

    @Query("SELECT DISTINCT title FROM Trainer")
    public List<String> findDistinctTitle();

}
