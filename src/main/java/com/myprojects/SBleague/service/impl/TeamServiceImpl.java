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
	public Team updateHomeTeam(MatchRegistrationDTO regDto) {
		String homeTeam = regDto.getHomeTeam();
		int homePoints = regDto.getHomePoints();
		int awayPoints = regDto.getAwayPoints();
		
		Team existingHomeTeam = teamRepository.findById(homeTeam).get();

		// determine result
		if (homePoints > awayPoints) {
			existingHomeTeam.setPoints(existingHomeTeam.getPoints()+2); // TODO: add league rules
			existingHomeTeam.setWins(existingHomeTeam.getWins()+1);
		} else if (homePoints < awayPoints) {
			existingHomeTeam.setLosses(existingHomeTeam.getLosses()+1);
		} else {
			existingHomeTeam.setPoints(existingHomeTeam.getPoints()+1);
			existingHomeTeam.setDraws(existingHomeTeam.getDraws()+1);
		}
		
		
		return teamRepository.save(existingHomeTeam);
	}

	@Override
	public Team updateAwayTeam(MatchRegistrationDTO regDto) {
		String awayTeam = regDto.getAwayTeam();
		int homePoints = regDto.getHomePoints();
		int awayPoints = regDto.getAwayPoints();
		
		Team existingAwayTeam = teamRepository.findById(awayTeam).get();

		// determine result
		if (homePoints > awayPoints) {
			existingAwayTeam.setLosses(existingAwayTeam.getLosses()+1)
			;
		} else if (homePoints < awayPoints) {
			existingAwayTeam.setPoints(existingAwayTeam.getPoints()+2); 
			existingAwayTeam.setWins(existingAwayTeam.getWins()+1);
		} else {
			existingAwayTeam.setPoints(existingAwayTeam.getPoints()+1);
			existingAwayTeam.setDraws(existingAwayTeam.getDraws()+1);
		}

		return teamRepository.save(existingAwayTeam);
	}

}
