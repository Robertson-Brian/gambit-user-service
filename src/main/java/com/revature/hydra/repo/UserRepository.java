package com.revature.hydra.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.hydra.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);

	User findByUserId(Integer userId);

	User findUserByFirstNameAndLastName(String firstName, String lastName);

	@Query("select distinct r.role from User r")
	List<String> findAllUserRoles();

}
