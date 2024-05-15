package com.springsecurity.oauth2impl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springsecurity.oauth2impl.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	
		Optional<Role> findByRoleName(String name);
	
}
