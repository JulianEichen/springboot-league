package com.myprojects.SBleague.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myprojects.SBleague.model.Season;

public interface SeasonRepository extends JpaRepository<Season, Long> {

}
