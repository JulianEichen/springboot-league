package com.myprojects.SBleague.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myprojects.SBleague.model.Team;
import com.myprojects.SBleague.repository.TeamRepository;
import com.myprojects.SBleague.service.TeamService;
import com.myprojects.SBleague.web.dto.MatchRegistrationDTO;
import com.myprojects.SBleague.web.dto.TeamRegistrationDTO;

@Service
public class TeamServiceImpl implements TeamService{
	
	// inject repository
	private TeamRepository teamRepository;
	public TeamServiceImpl(TeamRepository teamRepository) {
		super();
		this.teamRepository = teamRepository;
	}
	
	@Override
	public List<Team> getAllTeams() {
		return teamRepository.findAll();
	}

	@Override
	public List<Team> getAllTeamsOrdered() {
		TeamChecker checker = new TeamChecker();
		List<Team> teams = teamRepository.findAll();
		teams.sort(checker);
		return teams;
	}
	
	
	@Override
	public Team saveTeam(TeamRegistrationDTO teamDto) {
		Team team = new Team(teamDto.getName(), teamDto.getCoach(),0,0,0,0,0);
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

	@Override
	public Team updateByMatch(MatchRegistrationDTO regDto) {
		String homeTeam = regDto.getHomeTeam();
		String awayTeam = regDto.getAwayTeam();
		int homePoints = regDto.getHomePoints();
		int awayPoints = regDto.getAwayPoints();
		int result = 0;

		// determine result
		if (homePoints > awayPoints) {
			result = 1;
		} else if (homePoints < awayPoints) {
			result = -1;
		}
		// Team team = new Team(homeTeam, teamDto.getCoach(),0,0,0,0,0);
		
		return null;
	}

}
