package com.five.cybor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="agents")
public class AgentEntity {

	@Id
	private String username;
	private String name;
	private String title;
	private int unit;
	
	public AgentEntity(String username, String name, String title, int unit) {
		super();
		this.username = username;
		this.name = name;
		this.title = title;
		this.unit = unit;
	}

	public String getUser() {
		return username;
	}

	public void setUser(String user) {
		this.username = user;
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

	public int getSquare() {
		return unit;
	}

	public void setSquare(int square) {
		this.unit = square;
	}
	
	
	 
}
