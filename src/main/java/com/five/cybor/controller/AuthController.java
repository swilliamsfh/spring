package com.five.cybor.controller;



import java.util.Map;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.five.cybor.model.dto.AuthResponse;
import com.five.cybor.model.dto.LoginRequest;
import com.five.cybor.model.dto.RegisterRequest;
import com.five.cybor.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

	private final AuthService authService;
	//private Logger logger = LogManager.getLogger(getClass());

	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
		return ResponseEntity.ok(authService.register(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
		return ResponseEntity.ok(authService.Login(request));
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<Map<String, String>> refresh(@CookieValue(name = "refresh_token", required = false) String refreshToken) {
	   	return authService.refresh(refreshToken);
	}
	
	@PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        return authService.logout(response);
    }
}
