package com.five.cybor.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.five.cybor.model.AgentEntity;
import com.five.cybor.model.dto.AgentResponse;
import com.five.cybor.repository.AgentRepository;

@Service
public class AgentService {

	private final AgentRepository agentRepository;
	private final FiltersSpecefication filters;
    	
	public AgentService(AgentRepository agentRepository, FiltersSpecefication filters) {
		super();
		this.agentRepository = agentRepository;
		this.filters = filters;
	}
	
	private Page<AgentResponse> getAgents(Map<String,String> allParameters) {
		
		int unit = Integer.parseInt(allParameters.get("unit"));
					
		Specification<AgentEntity> specification = filters.getFilter(allParameters, "unit", unit);
		Pageable pageable = filters.getPageable(allParameters);  
		
		Page<AgentEntity> agentsPage = agentRepository.findAll(
				specification,
				pageable
				) ;
		
		return agentsPage.map(AgentResponse::new);	   
	}
	
	public Page<AgentResponse> agents(Map<String,String> allParameters){
		return getAgents(allParameters);
	}

	public List<AgentResponse> getAllAgents(int unit) {
		
		List<AgentEntity> entity = agentRepository.findByUnit(unit);
		List<AgentResponse> response = entity.stream().map(AgentResponse::new).collect(Collectors.toList());
		
		return response;
		
	}	
	
}
