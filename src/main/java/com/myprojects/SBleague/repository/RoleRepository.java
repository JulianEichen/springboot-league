package com.myprojects.SBleague.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myprojects.SBleague.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByName(String name);
	boolean existsByName(String name);
}
