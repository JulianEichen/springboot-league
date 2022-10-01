package com.myprojects.SBleague.service;

import java.util.List;

import com.myprojects.SBleague.model.Team;
import com.myprojects.SBleague.web.dto.MatchRegistrationDTO;
import com.myprojects.SBleague.web.dto.TeamRegistrationDTO;


public interface TeamService {
	List<Team> getAllTeams();
	
	List<Team> getAllTeamsOrdered();
	
	Team saveTeam(TeamRegistrationDTO teamDto);
	
	Team getTeamById(Long Id);
	
	Team updateTeam(Team team);
	
	void deleteTeamById(Long Id);

	Team updateHomeTeam(MatchRegistrationDTO regDto);

	Team updateAwayTeam(MatchRegistrationDTO regDto);
	
}
