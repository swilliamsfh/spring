package com.five.cybor.model.dto;

import com.five.cybor.model.AgentEntity;

public class AgentResponse {

	private String username;
	private String name;
	private String title;
	private int unit;
	
	public AgentResponse(String username, String name, String title, int unit) {
		super();
		this.username = username;
		this.name = name;
		this.title = title;
		this.unit = unit;
	}

	public AgentResponse(AgentEntity entity) {
		
		this.username = entity.getUsername();
		this.name = entity.getName();
		this.title = entity.getTitle();
		this.unit = entity.getUnit();
		
	}
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

		
	 
	
}
