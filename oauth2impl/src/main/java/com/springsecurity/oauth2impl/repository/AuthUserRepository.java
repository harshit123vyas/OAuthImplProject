package com.springsecurity.oauth2impl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springsecurity.oauth2impl.entity.AuthUser;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer>{
	
	Optional<AuthUser> findByEmail(String email);

}
