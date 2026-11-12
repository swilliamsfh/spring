package com.five.cybor.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.five.cybor.model.AgentEntity;
import com.five.cybor.model.dto.AgentResponse;
import com.five.cybor.repository.AgentRepository;
import com.five.cybor.repository.AgentSpecification;

@Service
public class AgentService {

	private final AgentRepository agentRepository;
    	
	public AgentService(AgentRepository agentRepository) {
		super();
		this.agentRepository = agentRepository;
	}
	
	private Page<AgentResponse> getAgents(Map<String,String> allParameters) {
		
		int unit = Integer.parseInt(allParameters.get("unit"));
		int page = Integer.parseInt(allParameters.get("page"));
		int size = Integer.parseInt(allParameters.get("size"));
		
		Specification<AgentEntity> specification = Specification.where(
				AgentSpecification.filtrarPorCampo("unit","equals",unit)
				);
		
		if (allParameters.get("name_op") != null) {
            specification = specification.and(
                AgentSpecification.filtrarPorCampo("name", allParameters.get("name_op"), allParameters.get("name"))
            );
        }
		
		String sortFields = allParameters.get("sort");
		String[] partes = sortFields.split(",");
		String field = partes[0];
		String orden = partes[1];
		Sort sorts = Sort.by(Sort.Direction.fromString(orden), field);
				 
		Pageable pageable = PageRequest.of(page,size,sorts);
		Page<AgentEntity> agentsPage = agentRepository.findAll(
				specification,
				pageable
				) ;
		return agentsPage.map(AgentResponse::new);	   
	}
	
	public Page<AgentResponse> agents(Map<String,String> allParameters){
		return getAgents(allParameters);
	}	
	
}
