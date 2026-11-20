package com.five.cybor.controller;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.five.cybor.model.AgentEntity;
import com.five.cybor.model.AssingAcountsEntity;
import com.five.cybor.model.dto.AgentResponse;
import com.five.cybor.model.dto.AssignResponse;
import com.five.cybor.repository.AgentSpecification;
import com.five.cybor.repository.AssignAccountsRepository;
import com.five.cybor.service.AssingAccountService;

@RestController
@RequestMapping("/api/v1/collections/")
@CrossOrigin(origins = "http://localhost:5173")
public class AssignAccountsController {

	private final AssingAccountService accountService;
		
	public AssignAccountsController(AssingAccountService accountService) {
		super();
		this.accountService = accountService;
	}

	@GetMapping("accounts")
    ResponseEntity<PagedModel<AssignResponse>> getAgents(
    		@RequestParam Map<String, String> allParams,
    		Pageable pageable ){
		
		for (Entry<String, String> entry : allParams.entrySet()) {
		    System.out.println("Clave: " + entry.getKey() + " | Valor: " + entry.getValue());
		}
		
		Page<AssignResponse> sendResponse =  accountService.account(allParams);
			
		return ResponseEntity.ok(new PagedModel<>(sendResponse));
		
	}
		
	
}
