package com.myprojects.SBleague.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myprojects.SBleague.model.Team;

public interface TeamRepository extends JpaRepository<Team, String>{
	
	Team findByName(String name);
	List<Team> findAllByOwnerId(Long ownerId);
}
