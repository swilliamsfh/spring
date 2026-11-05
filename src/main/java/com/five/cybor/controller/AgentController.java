package com.five.cybor.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.five.cybor.model.dto.AgentResponse;
import com.five.cybor.service.AgentService;

@RestController
@RequestMapping("/api/v1/collections/")
@CrossOrigin(origins = "http://localhost:5173")
public class AgentController {

	private final AgentService agentService;

	public AgentController(AgentService agentService) {
		super();
		this.agentService = agentService;
	}
	
	@GetMapping("agents")
    List<AgentResponse> getAgents(int unit){
		return agentService.agents(unit);
	}
}
