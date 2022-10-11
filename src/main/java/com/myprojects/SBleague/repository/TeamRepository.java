package com.myprojects.SBleague.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myprojects.SBleague.model.Team;

public interface TeamRepository extends JpaRepository<Team, String>{
	
	List<Team> findAllByOwner(String owner);
}
