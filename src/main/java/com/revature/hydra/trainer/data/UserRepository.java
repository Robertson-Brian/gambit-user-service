package com.revature.hydra.trainer.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);

	User findByUserId(Integer userId);

	User findUserByFirstNameAndLastName(String firstName, String lastName);

	@Query("select distinct u.role from User u")
	List<Integer> findAllUserRoles();


}
