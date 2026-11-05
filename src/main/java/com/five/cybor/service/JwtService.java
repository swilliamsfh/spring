package com.five.cybor.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET_KEY = "7c616d6f72657370617361646f736c6f736375616c65736e756e63616f6c766964617265";
	private static final String REFRESH_SECRET_KEY = "7c61346d6f72657370617361646f736c6f736375616c65736e756e63616f6c766964617265";
	private final Map<String, String> activeRefreshTokens = new ConcurrentHashMap<>();
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	public String roles(UserDetails userdetails) {
		return getRoles(userdetails);
	}
	
	public String generateRefresh(String username) {
		return generateRefreshToken(username);
	}
	
	private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		
		String roles = getRoles(userDetails);
				 
		extraClaims.put("roles", roles);
		
		return Jwts.builder().
			   setClaims(extraClaims).
			   setSubject(userDetails.getUsername()).
			   setIssuedAt(new Date(System.currentTimeMillis())).
			   setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)).
			   signWith(getSingInKey(SECRET_KEY), SignatureAlgorithm.HS256).
			   compact();
	}
	
	private String generateRefreshToken(String userName) {
		
		String refreshToken = Jwts.builder().
							  setSubject(userName).
							  setIssuedAt(new Date(System.currentTimeMillis())).
							  setExpiration(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000)).
							  signWith(getSingInKey(REFRESH_SECRET_KEY)).compact();
	
		activeRefreshTokens.put(userName, refreshToken);
        return refreshToken;
	}
	
	private String getRoles(UserDetails userDetails) {
		return userDetails.
			   getAuthorities().
			   stream().
			   map(GrantedAuthority::getAuthority).
			   collect(Collectors.joining(","));
	
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
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

	private Key getSingInKey(String secret) {
		 byte[] keyBytes = Decoders.BASE64.decode(secret);
		 return Keys.hmacShaKeyFor(keyBytes);
	}
	
	
	public boolean isRefreshTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            String username = claims.getSubject();
            
            // Verificamos si el token no ha sido revocado y coincide con el almacenado
            return activeRefreshTokens.containsKey(username) && activeRefreshTokens.get(username).equals(token);
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsernameFromRefresh(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public void revokeRefreshToken(String username) {
        activeRefreshTokens.remove(username);
    }
	
    public String generateRefreshEx(String username) {
    	return generateAccessTokenRefresh(username);
    }
    
    private String generateAccessTokenRefresh(String username) {
        
        		return Jwts.builder().
           				   setSubject(username).
        				   setIssuedAt(new Date(System.currentTimeMillis())).
        				   setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)).
        				   signWith(getSingInKey(SECRET_KEY), SignatureAlgorithm.HS256).
        				   compact();
                
    }
	
}
