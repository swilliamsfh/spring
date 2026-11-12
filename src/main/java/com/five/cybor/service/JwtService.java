package com.five.cybor.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.five.cybor.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET_KEY = "7c616d6f72657370617361646f736c6f736375616c65736e756e63616f6c766964617265";
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		
		Set<Role> roles = getRoles(userDetails);
	 		   extraClaims.put("roles", roles);
		
		return Jwts.builder().
			   setClaims(extraClaims).
			   setSubject(userDetails.getUsername()).
			   setIssuedAt(new Date(System.currentTimeMillis())).
			   setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)).
			   signWith(getSingInKey(SECRET_KEY), SignatureAlgorithm.HS256).
			   compact();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	private <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().
		  	   setSigningKey(getSingInKey(SECRET_KEY)).
		  	   build().
		  	   parseClaimsJws(token).
		  	   getBody();
	}
	
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	private Key getSingInKey(String secret) {
		 byte[] keyBytes = Decoders.BASE64.decode(secret);
		 return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public Set<Role> roles(UserDetails userdetails) {
		return getRoles(userdetails);
	}
		
	private Set<Role> getRoles(UserDetails userDetails) {
		return userDetails.
			   getAuthorities().
			   stream().
			   map(authority -> new Role(authority.getAuthority())).
			   collect(Collectors.toSet());
		 
		 
	
	}

	private Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}
	
	
}
