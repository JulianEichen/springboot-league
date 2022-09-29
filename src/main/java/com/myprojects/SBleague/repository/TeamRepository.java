package com.myprojects.SBleague.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myprojects.SBleague.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

}
