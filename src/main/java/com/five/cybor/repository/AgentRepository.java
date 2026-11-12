package com.five.cybor.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.five.cybor.model.AgentEntity;
@Repository
public interface AgentRepository extends JpaRepository<AgentEntity, String>, JpaSpecificationExecutor<AgentEntity>{

	Page<AgentEntity> findByUnitAndNameContainingIgnoreCase(int squareId, Pageable pageable, String username);
	
}
