package com.five.cybor.service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.five.cybor.entity.Role;
import com.five.cybor.entity.UserModel;


public class UserDetailsImpl implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final UserModel user;
	
	public UserDetailsImpl(UserModel user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = user.getRoles();
		return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
	}

	@Override
	public @Nullable String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
    public boolean isAccountNonExpired() {
        return true;
    }
	
	 @Override
	public boolean isAccountNonLocked() {
	    return true;
	}
	 
	@Override
	public boolean isCredentialsNonExpired() {
	   return true;
	}
	
	@Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
	
	  public Long getId() {
	        return user.getId();
	    }
	    
	    /**
	     * Obtiene el email del usuario
	     * 
	     * @return String - El email del usuario
	     */
	    public String getEmail() {
	        return user.getEmail();
	    }
	    
	    /**
	     * Método estático factory para crear UserDetailsImpl
	     * 
	     * @param user - La entidad User
	     * @return UserDetailsImpl - Nueva instancia
	     * 
	     * Patrón Factory Method: Proporciona una forma elegante de crear instancias
	     */
	    public static UserDetailsImpl create(UserModel user) {
	        return new UserDetailsImpl(user);
	    }
	
	
	
}
