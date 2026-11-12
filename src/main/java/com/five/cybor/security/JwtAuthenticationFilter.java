package com.five.cybor.security;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.five.cybor.service.JwtService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private Logger logger = LogManager.getLogger(JwtAuthenticationFilter.class);	
	
	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
		super();
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(@Nonnull HttpServletRequest request,
			                        @Nonnull HttpServletResponse response,
			                        @Nonnull FilterChain filterChain)
			throws ServletException, IOException {
	
        String jwt = parseJWTFromRequest(request);
        
	try {
		if (jwt == null ) {
	       filterChain.doFilter(request, response); // Continuar con el siguiente filtro si no hay token
           return;
        }

        String username = jwtService.extractUsername(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
	}catch(ExpiredJwtException e) {
		logger.error("Token JWT ha expirado: " + e.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"error\":\"Token expirado\"}");
        response.setContentType("application/json");
        return;
	}  catch (SignatureException e) {
        logger.error("Firma del token JWT inválida: " + e.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"error\":\"Token con firma inválida\"}");
        response.setContentType("application/json");
        return;  
	}
	catch (IOException e) {
        logger.error("Firma del token JWT inválida: " + e.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"error\":\"Token con firma inválida\"}");
        response.setContentType("application/json");
        return;
        
    } catch (MalformedJwtException e) {
        logger.error("Token JWT malformado: " + e.getMessage());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"error\":\"Token malformado\"}");
        response.setContentType("application/json");
        return;
        
    } catch (Exception e) {
        logger.error("Error procesando token JWT: " + e.getMessage());
    }
	        filterChain.doFilter(request, response);
	}

	
	private String parseJWTFromRequest(HttpServletRequest request) {
		
		String headerAuth = request.getHeader("Authorization");
		
		if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7);
		}
		return null;
	}
	
	
}
