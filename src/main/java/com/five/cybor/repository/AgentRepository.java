package com.five.cybor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.five.cybor.model.AgentEntity;

public interface AgentRepository extends JpaRepository<AgentEntity, String>{

	@Query(value = "SELECT * FROM Agent WHERE square = :unit", nativeQuery = true)
    List<AgentEntity> findBySquare(int unit);
	
}
