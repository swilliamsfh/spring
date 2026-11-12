package com.five.cybor.model.dto;

import java.util.HashSet;
import java.util.Set;

import com.five.cybor.entity.Role;

public class AuthResponse {

	private String token;
	private Set<Role> roles = new HashSet<>();
	
	public AuthResponse(String token, Set<Role> roles) {
		
		this.token =  token;
		this.roles =  roles;
		
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	
	
	

}
