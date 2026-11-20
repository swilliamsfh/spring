package com.five.cybor.repository;

import org.springframework.data.jpa.domain.Specification;

import com.five.cybor.model.AgentEntity;

import jakarta.persistence.criteria.Expression;

public class AgentSpecification {

	public static <T> Specification<T> filtrarPorCampo(String campo, String operador, Object valor){
		
		return (root, query, cb) -> {
            // Si el operador es "isBlank", no necesitamos un valor de entrada
          /*  if ("isBlank".equalsIgnoreCase(operador)) {
                return cb.or(
                    cb.isNull(root.get(campo)),
                    cb.equal(cb.trim(root.get(campo)), "")
                );
            }
            
            if(valor == null) {
            	
            	return cb.conjunction();
            }
            */
            Expression<String> campoTexto = cb.lower(root.get(campo).as(String.class));
            
            String valorTexto = valor.toString().toLowerCase();
            System.out.println(operador.toLowerCase());
            switch (operador.toLowerCase()) {
            
 case "notblank":
            	
            	return cb.isNotNull(root.get(campo));
            
 case "blank":
 	
 	return cb.isNull(root.get(campo));
            	
			case "equals": 
				return cb.equal(root.get(campo), valor);
			
			case "notequal":
                return cb.notEqual(root.get(campo), valor);

            case "contains":
                return cb.like(campoTexto, "%" + valorTexto + "%");

            case "notcontains":
                return cb.notLike(campoTexto, "%" + valorTexto + "%");

            case "startswith":
            	return cb.like(campoTexto, valorTexto + "%");
            	
            case "endswith":
            	return cb.like( campoTexto, "%" + valorTexto );
           
            case "isnull":
            	return cb.isNull(root.get(campo));
			
           	
            	
            default:
				throw new IllegalArgumentException("Unexpected value: " + valorTexto);
			}
            
		};
		
	}
	
}
