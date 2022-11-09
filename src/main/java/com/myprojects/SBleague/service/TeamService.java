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
	
	List<TeamDto>getAllActiveTeamDtoOrdered();
	
	List<Team> getAllTeamsByOwnerId(Long ownerId);
	List<TeamDto>getAllTeamDtoByOwnerId(Long ownerId);
	
	Team saveTeam(TeamDto teamDto,String userEmail);
	
	Team getTeamById(Long Id);
	
	Team updateTeam(Team team);
	
	void resetTeamById(Long id);
	void enrollmentById(Long id, boolean enrolled);
	
	void deleteTeamById(Long Id);
	
	void deleteStatistics(MatchDto matchDto);

	void updateStatistics(MatchDto matchDto);
	
	String getOwnerNameByTeamName(String teamName);
}
