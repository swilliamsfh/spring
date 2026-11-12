package com.five.cybor.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.five.cybor.model.dto.AgentResponse;
import com.five.cybor.service.AgentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/collections/")
@CrossOrigin(origins = "http://localhost:5173")
public class AgentController {

	private final AgentService agentService;
    private final Logger log = LogManager.getLogger(getClass());  
	
	public AgentController(AgentService agentService) {
		super();
		this.agentService = agentService;
	}
	
	@GetMapping("agents")
    ResponseEntity<PagedModel<AgentResponse>> getAgents(
    		@RequestParam Map<String, String> allParams,
    		Pageable pageable ){
		
		for (Entry<String, String> entry : allParams.entrySet()) {
		    System.out.println("Clave: " + entry.getKey() + " | Valor: " + entry.getValue());
		}
		
		Page<AgentResponse> sendResponse =  agentService.agents(allParams);
			
		return ResponseEntity.ok(new PagedModel<>(sendResponse));
		
	}
}
