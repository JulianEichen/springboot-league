package com.myprojects.SBleague.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myprojects.SBleague.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{
	
	Team findByName(String name);
	List<Team> findAllByOwnerId(Long ownerId);
	List<Team> findAllByEnrolled(boolean enrolled);
	boolean existsByName(String name);
}
