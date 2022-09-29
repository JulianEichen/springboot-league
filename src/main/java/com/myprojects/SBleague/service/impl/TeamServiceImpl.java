package com.myprojects.SBleague.service.impl;

import java.util.List;

import com.myprojects.SBleague.model.Team;
import com.myprojects.SBleague.repository.TeamRepository;
import com.myprojects.SBleague.service.TeamService;
import com.myprojects.SBleague.web.dto.TeamRegistrationDTO;

public class TeamServiceImpl implements TeamService{

	// inject repository
	private TeamRepository teamRepository;
	public TeamServiceImpl(TeamRepository teamRepository) {
		super();
		this.teamRepository = teamRepository;
	}
	
	@Override
	public List<Team> getAllTeams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Team saveTeam(TeamRegistrationDTO teamDto) {
		Team team = new Team(teamDto.getName(), teamDto.getCoach());
		return teamRepository.save(team);
	}

	@Override
	public Team getTeamById(Long Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Team updateTeam(Team team) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTeamById(Long Id) {
		// TODO Auto-generated method stub
		
	}

}
