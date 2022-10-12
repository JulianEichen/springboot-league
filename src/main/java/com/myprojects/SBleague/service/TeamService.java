package com.myprojects.SBleague.service;

import java.util.List;

import com.myprojects.SBleague.model.Team;
import com.myprojects.SBleague.web.dto.MatchDto;
import com.myprojects.SBleague.web.dto.TeamDto;


public interface TeamService {
	List<Team> getAllTeams();
	List<TeamDto>getAllTeamDto();
	
	List<Team> getAllTeamsOrdered();
	List<TeamDto>getAllTeamDtoOrdered();
	
	List<Team> getAllTeamsByOwner(String owner);
	List<TeamDto>getAllTeamDtoByOwner(String owner);
	
	Team saveTeam(TeamDto teamDto);
	
	Team getTeamById(String Id);
	
	Team updateTeam(Team team);
	
	void deleteTeamById(String Id);
	
	void deleteStatistics(MatchDto matchDto);

	void updateStatistics(MatchDto matchDto);
	
}
