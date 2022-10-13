package com.myprojects.SBleague.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myprojects.SBleague.model.Match;

public interface MatchRepository extends JpaRepository<Match, String>{
	
	Match findByName(String name);
	void deleteByName(String name);
	Match findById(Long id);
}
