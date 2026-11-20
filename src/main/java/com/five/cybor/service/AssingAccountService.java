package com.five.cybor.service;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.five.cybor.model.AssingAcountsEntity;
import com.five.cybor.model.dto.AssignResponse;
import com.five.cybor.repository.AgentSpecification;
import com.five.cybor.repository.AssignAccountsRepository;

@Service
public class AssingAccountService {

	private final AssignAccountsRepository assign;
	private final FiltersSpecefication filter;
	
	public AssingAccountService(AssignAccountsRepository assign, FiltersSpecefication filter) {
		super();
		this.assign = assign;
		this.filter = filter;
	}

	private Page<AssignResponse> getAccounts(Map<String,String> allParameters) {
		
		int unit = Integer.parseInt(allParameters.get("unit"));
				
		Specification<AssingAcountsEntity> specification = filter.getFilter(allParameters, "daunit", unit);
		Pageable pageable = filter.getPageable(allParameters);
	    
		Page<AssingAcountsEntity> accountsPage = assign.findAll(
				specification,
				pageable
				) ;
		return accountsPage.map(AssignResponse::new);	   
	}
	
	public Page<AssignResponse> account(Map<String,String> allParameters){
		return getAccounts(allParameters);
	}	
	

	
}
