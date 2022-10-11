package com.myprojects.SBleague.service;

import java.util.List;

import com.myprojects.SBleague.model.Team;
import com.myprojects.SBleague.web.dto.MatchDto;
import com.myprojects.SBleague.web.dto.TeamRegistrationDto;


public interface TeamService {
	List<Team> getAllTeams();
	
	List<Team> getAllTeamsOrdered();
	
	List<Team> getAllTeamsByOwner(String owner);
	
	Team saveTeam(TeamRegistrationDto teamDto);
	
	Team getTeamById(String Id);
	
	Team updateTeam(Team team);
	
	void deleteTeamById(String Id);
	
	void deleteStatistics(MatchDto matchDto);

	void updateStatistics(MatchDto matchDto);
	
}
