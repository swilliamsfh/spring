package com.five.cybor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.five.cybor.model.AgentEntity;
import com.five.cybor.model.AssingAcountsEntity;

public interface AssignAccountsRepository extends JpaRepository<AssingAcountsEntity, String>, JpaSpecificationExecutor<AssingAcountsEntity>{


}
