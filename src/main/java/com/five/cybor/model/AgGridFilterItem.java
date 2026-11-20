package com.five.cybor.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AgGridFilterItem {
	
	// Tipo de filtro: "text", "number", "date", "set"
    private String filterType;

    // Operador: "contains", "notContains", "equals", "notEqual", 
    // "startsWith", "endsWith", "greaterThan", "lessThan", "inRange", "blank", "notBlank"
    private String type;

    // Valor principal del filtro (String, Number o Boolean)
    private Object filter;

    // Segundo valor (usado cuando type = "inRange")
    private Object filterTo;

    // Para filtros de tipo fecha (AG Grid envía "dateFrom" y "dateTo" en formato "YYYY-MM-DD HH:mm:ss")
    private String dateFrom;
    private String dateTo;

    // Para filtros de selección múltiple (Set Filter en AG Grid Enterprise)
    private List<String> values;

    // --- Soporte para filtros combinados (AND / OR en una misma columna) ---
    private String operator; // "AND" o "OR"
    private AgGridFilterItem condition1;
    private AgGridFilterItem condition2;
    private List<AgGridFilterItem> conditions;
	
    public AgGridFilterItem(String filterType, String type, Object filter, Object filterTo, String dateFrom,
			String dateTo, List<String> values, String operator, AgGridFilterItem condition1,
			AgGridFilterItem condition2, List<AgGridFilterItem> conditions) {
		super();
		this.filterType = filterType;
		this.type = type;
		this.filter = filter;
		this.filterTo = filterTo;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.values = values;
		this.operator = operator;
		this.condition1 = condition1;
		this.condition2 = condition2;
		this.conditions = conditions;
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getFilter() {
		return filter;
	}

	public void setFilter(Object filter) {
		this.filter = filter;
	}

	public Object getFilterTo() {
		return filterTo;
	}

	public void setFilterTo(Object filterTo) {
		this.filterTo = filterTo;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public AgGridFilterItem getCondition1() {
		return condition1;
	}

	public void setCondition1(AgGridFilterItem condition1) {
		this.condition1 = condition1;
	}

	public AgGridFilterItem getCondition2() {
		return condition2;
	}

	public void setCondition2(AgGridFilterItem condition2) {
		this.condition2 = condition2;
	}

	public List<AgGridFilterItem> getConditions() {
		return conditions;
	}

	public void setConditions(List<AgGridFilterItem> conditions) {
		this.conditions = conditions;
	}	
	
    
    
}
