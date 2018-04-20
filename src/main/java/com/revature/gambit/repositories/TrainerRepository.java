package com.revature.gambit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.revature.gambit.entities.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

     Trainer findByUserId(int userId);
	 Trainer findByEmail(String email);
	 
	 @Modifying
	 @Query("update Trainer t set t.title = ?1 where t.email = ?2")
	 int updateTitle(String title, String email);

    @Query("SELECT DISTINCT title FROM Trainer")
     List<String> findDistinctTitle();
	

}
