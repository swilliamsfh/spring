package com.five.cybor.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.five.cybor.model.AgGridFilterItem;
import com.five.cybor.model.AgentEntity;
import com.five.cybor.repository.AgentSpecification;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Service
public class FiltersSpecefication {

	public <T> Specification<T> getFilter(Map<String,String> allParameters, String field, int fieldValue ){
		
		Specification<T> spec = Specification.where(
				AgentSpecification.filtrarPorCampo(field,"equals", fieldValue)
				);
		
		String filtersJson = allParameters.get("filters");
		Map<String, AgGridFilterItem> filtros = Collections.emptyMap();
       
		if (filtersJson != null && !filtersJson.trim().isEmpty() && !filtersJson.equalsIgnoreCase("null")) {
		    ObjectMapper mapper = new ObjectMapper();
		    filtros = mapper.readValue(filtersJson, new TypeReference<Map<String, AgGridFilterItem>>() {});
		}
			
			for (Map.Entry<String, AgGridFilterItem> entry : filtros.entrySet()) {
			    String campo = entry.getKey();
			    AgGridFilterItem criterio = entry.getValue();
			    System.out.println(criterio.getFilter() +" "+ criterio.getOperator() + " " + criterio.getType() );
			    Specification<T> filtroColumna = procesarCriterio(campo, criterio);
	            if (filtroColumna != null) {
	                spec = spec.and(filtroColumna); // Entre diferentes columnas siempre es AND
	            }
			    
			    /*if (criterio.getFilter() != null) {
			    	spec = spec.and(
			            AgentSpecification.filtrarPorCampo(campo, criterio.getType(), criterio.getFilter())
			        );
			    }*/
			   
			}
			return spec;
		
	}
	
	
	public Pageable getPageable(Map<String,String> allParameters) {
	
		int page = Integer.parseInt(allParameters.get("page"));
		int size = Integer.parseInt(allParameters.get("size"));
		Pageable pageable;
		
		String sortFields = allParameters.get("sort");
		if (sortFields != null) {
			String[] partes = sortFields.split(",");
			String field = partes[0];
			String orden = partes[1];
			Sort sorts = Sort.by(Sort.Direction.fromString(orden), field);
			pageable = PageRequest.of(page,size,sorts);
		}else {
			pageable = PageRequest.of(page,size);
		}
		
		return pageable;
	}
	
	private static <T> Specification<T> procesarCriterio(String campo, AgGridFilterItem criterio) {
	    if (criterio == null) {
	    	System.out.println("Aqui oerri5");
	        return null;
	    }
	    
	    System.out.println("Criterio"+criterio.getOperator()); 
	    // 1. Si tiene operador ("AND" o "OR"), combina condition1 y condition2
	    if (criterio.getOperator() != null && criterio.getConditions() != null) {
	    	
	        Specification<T> spec1 = procesarCriterio(campo, criterio.getConditions().get(0));
	        Specification<T> spec2 = procesarCriterio(campo, criterio.getConditions().get(1));
	       
	        if (spec1 == null) return spec2;
	        if (spec2 == null) return spec1;

	        if ("OR".equalsIgnoreCase(criterio.getOperator())) {
	        	System.out.println("Aqui oerri4"); 
	        	return spec1.or(spec2);
	           
	        } else {
	        	System.out.println("Aqui oerri3");
	        	return spec1.and(spec2);
	        }
	        
	    }
	    
	    if (criterio.getFilter() != null || criterio.getType().equals("notBlank") || 
	    		criterio.getType().equals("blank")) {
	    	System.out.println(criterio.getFilter() + "" + "Aqui operro");	
	        return AgentSpecification.filtrarPorCampo(campo, criterio.getType(), (criterio.getFilter() == null || "null".equals(String.valueOf(criterio.getFilter()))) ? "blank" : criterio.getFilter());
	    }

	    // 3. Caso base: Filtro de selección múltiple (Set Filter)
	    if (criterio.getValues() != null && !criterio.getValues().isEmpty()) {
	    	System.out.println("Aqui oerri");
	        return AgentSpecification.filtrarPorCampo(campo, "in", criterio.getValues());
	    }
	    System.out.println("Aqui oerri7");
		return null;
	}
}
