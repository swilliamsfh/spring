package com.five.cybor.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.five.cybor.service.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsServiceImpl userDetailsService;
	private List<String> cors = List.of("http://localhost:5173");
	private List<String> methods = List.of("GET","POST","PUT","DELETE","OPTIONS","PATCH");
	private List<String> headers = List.of("*");
    
    /**
     * ConfiguraciOn principal de la cadena de filtros de seguridad
     * 
     * @param http - Objeto HttpSecurity para configurar seguridad HTTP
     * @return SecurityFilterChain - Cadena de filtros configurada
     * 
     * Configuraciones aplicadas:
     * 1. CORS habilitado con configuración personalizada
     * 2. CSRF deshabilitado (no necesario en APIs stateless)
     * 3. Autorización de endpoints configurada
     * 4. Gestión de sesiones deshabilitada (STATELESS)
     * 5. Proveedor de autenticación personalizado
     * 6. Filtro JWT agregado antes del filtro de autenticación estándar
     */ 
    
	SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserDetailsServiceImpl userDetailsService) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.userDetailsService = userDetailsService;
	}

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        	//Configurar CORS Permitir request croos-orign
        	.cors(cors->cors.configurationSource(corsConfigurationSource()))
        	//Deshabilitar CSRF no necesarios para APIS stateless
            .csrf(AbstractHttpConfigurer::disable) 
            //Configuracion request
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                	"/api/v1/auth/login",
            		"/api/v1/auth/register")
                .permitAll() 
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/user/**").hasAllRoles("user","ADMIN")
                .anyRequest().authenticated()                  
            )
            
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); 

        return http.build();
    }
	
	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
		return config.getAuthenticationManager();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
		
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		//Origines permitidos
		configuration.setAllowedOriginPatterns(cors);
		//Metodos permitidos
		configuration.setAllowedMethods(methods);
		//Encabezados Permitidos
		configuration.setAllowedHeaders(headers);
		//Permitir cookies - authorization headers
		configuration.setAllowCredentials(true);
		//Aplicar la configuraciona todas las rutas
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
		
	}

		 	
}