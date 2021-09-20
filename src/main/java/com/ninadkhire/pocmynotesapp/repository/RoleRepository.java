package com.ninadkhire.pocmynotesapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ninadkhire.pocmynotesapp.models.ERole;
import com.ninadkhire.pocmynotesapp.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(ERole name);
	
}
