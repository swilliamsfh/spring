package com.five.cybor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.five.cybor.model.dto.AgentResponse;
import com.five.cybor.repository.AgentRepository;

@Service
public class AgentService {

	private final AgentRepository agentRepository;

	public AgentService(AgentRepository agentRepository) {
		super();
		this.agentRepository = agentRepository;
	}
	
	private List<AgentResponse> getAgents(int square) {
		
		return agentRepository.
			   findBySquare(square).
			   stream().
			   map(entity-> 
			   new AgentResponse(
				   				entity.getUser(),
				     			entity.getName(),
								entity.getTitle(),
								entity.getSquare())
			   ).toList();
	}
	
	public List<AgentResponse> agents(int square){
		return getAgents(square);
	}
	
}
