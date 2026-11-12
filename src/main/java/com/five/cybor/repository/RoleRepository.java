package com.five.cybor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.five.cybor.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByName(String name);
	
	boolean existsByName(String name);
	
	void deleteByName(String name);
	
}
