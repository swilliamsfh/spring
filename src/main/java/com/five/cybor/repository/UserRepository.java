package com.five.cybor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.five.cybor.entity.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{

	 Optional<UserModel> findByUsername(String username);
	 
	 Optional<UserModel> findByEmail(String email);

	 boolean existsByUsername(String username);
	 
	 boolean existsByEmail(String email);

	 List<UserModel> findByUsernameContainingIgnoreCase(String username);
	 
	 long countByEnabled(boolean enabled);
}
