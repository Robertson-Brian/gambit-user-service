package com.revature.gambit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.gambit.entities.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{

	UserRole findByRole(String role);
}
