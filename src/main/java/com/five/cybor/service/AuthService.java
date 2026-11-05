package com.five.cybor.service;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;


import com.five.cybor.model.UserModel;
import com.five.cybor.model.dto.AuthResponse;
import com.five.cybor.model.dto.LoginRequest;
import com.five.cybor.model.dto.RegisterRequest;
import com.five.cybor.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService {

	private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final UserRepository userRepository;
	
	public AuthService( PasswordEncoder passwordEncoder,
			JwtService jwtService, UserRepository userRepository, AuthenticationManager authenticationManager) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
	}
	
	public AuthResponse register(RegisterRequest request) {
		
		UserModel user = new UserModel();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setEmail(request.getEmail());
		user.setRole(request.getRole());
				
		userRepository.save(user);
		
		UserDetails userDetails = User.builder().
				username(user.getUsername()).
				password(user.getPassword()).
				authorities(user.getRole()).
				build();
		
		String token = jwtService.generateToken(userDetails);
		
		return new AuthResponse(token,user.getRole());
	}
	
	public AuthResponse Login(LoginRequest loginRequest) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
				);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		String token = jwtService.generateToken(userDetails);
		String roles = jwtService.roles(userDetails);
				
		return new AuthResponse(token, roles);
		
		
	}
	
	public ResponseEntity<Map<String, String>> refresh(String refreshToken) {
	  
		// 1. El método ahora valida la firma y la existencia en nuestra "lista blanca/base de datos"
	    if (refreshToken == null || !jwtService.isRefreshTokenValid(refreshToken)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    // 2. Extraemos el usuario con la llave criptográfica correcta de refresh
	    String username = jwtService.extractUsernameFromRefresh(refreshToken);
	    
	    // 3. Generamos un nuevo Access Token de ciclo corto para que Pinia lo recoja
	    String newAccessToken = jwtService.generateRefreshEx(username);

	    return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
	}
	
	
	public ResponseEntity<Void> logout(HttpServletResponse response) {
        // Destruir la cookie en el navegador asignando maxAge(0)
        ResponseCookie cookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/api/auth")
                .maxAge(0)
                .build();
                
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok().build();
    }
	
}
