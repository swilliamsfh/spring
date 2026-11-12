package com.five.cybor.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.five.cybor.entity.Role;
import com.five.cybor.entity.UserModel;
import com.five.cybor.model.dto.AuthResponse;
import com.five.cybor.model.dto.LoginRequest;
import com.five.cybor.model.dto.RegisterRequest;
import com.five.cybor.repository.RoleRepository;
import com.five.cybor.repository.UserRepository;


@Service
public class AuthService {

	private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final Logger log = org.apache.logging.log4j.LogManager.getLogger(getClass()); 
	
	public AuthService( PasswordEncoder passwordEncoder,
			JwtService jwtService, UserRepository userRepository, AuthenticationManager authenticationManager,
			RoleRepository roleRepository) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.roleRepository = roleRepository;
	}
	
	public AuthResponse register(RegisterRequest registerRequest) {
		
		log.info("Iniciando registro de usuario: {}", registerRequest.getUsername() );
		
		if(userRepository.existsByUsername(registerRequest.getUsername())) {
			log.info("Ya existe un usuario con el nombre: " + registerRequest.getUsername());
		}
		
		UserModel user = new UserModel();
		user.setUsername(registerRequest.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setEmail(registerRequest.getEmail());
		user.setEnabled(true);
		Set<Role> defaultRoles = getDefaultRoles();
        user.setRoles(defaultRoles);
		userRepository.save(user);
		
		UserDetails userDetails = User.builder().
				username(user.getUsername()).
				password(user.getPassword()).
				build();
		
		String token = jwtService.generateToken(userDetails);
		
		return new AuthResponse(token,user.getRoles());
	}
	
	public AuthResponse Login(LoginRequest loginRequest) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
				);
	
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String token = jwtService.generateToken(userDetails);
		Set<Role> roles = jwtService.roles(userDetails);
	
		return new AuthResponse(token, roles);
			
		
	}
	
	private Set<Role> getDefaultRoles() {
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    log.info("Creando rol por defecto: ROLE_USER");
                    Role newRole = new Role("ROLE_USER");
                    return roleRepository.save(newRole);
                });
        
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        return roles;
    }
	
	
	
}
