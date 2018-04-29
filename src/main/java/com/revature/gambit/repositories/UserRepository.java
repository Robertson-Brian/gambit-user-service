package com.revature.gambit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.gambit.entities.User;
import com.revature.gambit.entities.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByEmail(String email);

	User findByUserId(int userId);

	User findUserByFirstNameAndLastName(String firstName, String lastName);

	List<User> findByRole(UserRole role);

}
