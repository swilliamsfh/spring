package com.five.cybor.model.dto;

public class AgentResponse {

	private String user;
	private String name;
	private String title;
	private int square;
	
	public AgentResponse(String user, String name, String title, int square) {
		super();
		this.user = user;
		this.name = name;
		this.title = title;
		this.square = square;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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
		return square;
	}

	public void setSquare(int square) {
		this.square = square;
	}
	
	
	
}
