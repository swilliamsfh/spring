package com.five.cybor.model.dto;

public class AuthResponse {

	private String token;
	private String roles;
	
	public AuthResponse(String token, String roles) {
		
		this.token =  token;
		this.roles =  roles;
		
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRol() {
		return roles;
	}

	public void setRol(String roles) {
		this.roles = roles;
	}
	
	

}
