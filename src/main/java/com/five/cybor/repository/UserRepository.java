package com.five.cybor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.five.cybor.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{

	 Optional<UserModel> findByUsername(String username);

}
