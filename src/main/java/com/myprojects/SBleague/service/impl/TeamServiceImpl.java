package com.myprojects.SBleague.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myprojects.SBleague.model.Team;
import com.myprojects.SBleague.repository.TeamRepository;
import com.myprojects.SBleague.service.SeasonService;
import com.myprojects.SBleague.service.TeamService;
import com.myprojects.SBleague.web.dto.MatchDto;
import com.myprojects.SBleague.web.dto.TeamRegistrationDTO;

@Service
public class TeamServiceImpl implements TeamService {
	
	// inject repositories
	private TeamRepository teamRepository;
	private SeasonService seasonService;

	public TeamServiceImpl(TeamRepository teamRepository,SeasonService seasonService) {
		super();
		this.teamRepository = teamRepository;
		this.seasonService = seasonService;
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
		String teamName = teamDto.getName().replace(' ', '_');
		Team team = new Team(teamName, teamDto.getCoach(), teamDto.getSeasonId(), 0, 0, 0, 0, 0);
		return teamRepository.save(team);
	}

	@Override
	public Team getTeamById(String Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Team updateTeam(Team team) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTeamById(String Id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteStatistics(MatchDto matchDto) {
		
		String homeTeamName = matchDto.getHomeTeam().replace(" ","_");
		String awayTeamName = matchDto.getAwayTeam().replace(" ","_");
		
		Team homeTeam = teamRepository.findById(homeTeamName).get();
		Team awayTeam = teamRepository.findById(awayTeamName).get();
		
		// home win
		if (matchDto.getHomePoints() > matchDto.getAwayPoints()) {
			// result statistics
			homeTeam.setWins(homeTeam.getWins()-1);
			awayTeam.setLosses(awayTeam.getLosses()-1);
			// league points
			homeTeam.setPoints(homeTeam.getPoints()-seasonService.getPointsPerWinById(homeTeam.getSeasonId()));
			awayTeam.setPoints(awayTeam.getPoints()-seasonService.getPointsPerLossById(awayTeam.getSeasonId()));
		// home loss
		}else if(matchDto.getHomePoints() < matchDto.getAwayPoints()) {
			// result statistics
			homeTeam.setLosses(homeTeam.getLosses()-1);
			awayTeam.setWins(awayTeam.getWins()-1);
			// league points
			homeTeam.setPoints(homeTeam.getPoints()-seasonService.getPointsPerLossById(homeTeam.getSeasonId()));
			awayTeam.setPoints(awayTeam.getPoints()-seasonService.getPointsPerWinById(awayTeam.getSeasonId()));
		// draw
		}else if(matchDto.getHomePoints() == matchDto.getAwayPoints()){
			// result statistics
			homeTeam.setDraws(homeTeam.getDraws()-1);
			awayTeam.setDraws(awayTeam.getDraws()-1);
			// league points
			homeTeam.setPoints(homeTeam.getPoints()-seasonService.getPointsPerDrawById(homeTeam.getSeasonId()));
			awayTeam.setPoints(awayTeam.getPoints()-seasonService.getPointsPerWinById(awayTeam.getSeasonId()));
		}
		
		teamRepository.deleteById(homeTeamName);
		teamRepository.deleteById(awayTeamName);
		
		teamRepository.save(homeTeam);
		teamRepository.save(awayTeam);
	}

	@Override
	public void updateStatistics(MatchDto matchDto) {
		String homeTeamName = matchDto.getHomeTeam().replace(" ","_");
		String awayTeamName = matchDto.getAwayTeam().replace(" ","_");
		
		Team homeTeam = teamRepository.findById(homeTeamName).get();
		Team awayTeam = teamRepository.findById(awayTeamName).get();
		
		if (matchDto.getHomePoints() > matchDto.getAwayPoints()) {
			// result statistics
			homeTeam.setWins(homeTeam.getWins()+1);
			awayTeam.setLosses(awayTeam.getLosses()+1);
			// league points
			homeTeam.setPoints(homeTeam.getPoints()+seasonService.getPointsPerWinById(homeTeam.getSeasonId()));
			awayTeam.setPoints(awayTeam.getPoints()+seasonService.getPointsPerLossById(awayTeam.getSeasonId()));
		}else if(matchDto.getHomePoints() < matchDto.getAwayPoints()) {
			// result statistics
			homeTeam.setLosses(homeTeam.getLosses()+1);
			awayTeam.setWins(awayTeam.getWins()+1);
			// league points
			homeTeam.setPoints(homeTeam.getPoints()+seasonService.getPointsPerLossById(homeTeam.getSeasonId()));
			awayTeam.setPoints(awayTeam.getPoints()+seasonService.getPointsPerWinById(awayTeam.getSeasonId()));
		}else if(matchDto.getHomePoints() == matchDto.getAwayPoints()){
			// result statistics
			homeTeam.setDraws(homeTeam.getDraws()+1);
			awayTeam.setDraws(awayTeam.getDraws()+1);
			// league points
			homeTeam.setPoints(homeTeam.getPoints()+seasonService.getPointsPerDrawById(homeTeam.getSeasonId()));
			awayTeam.setPoints(awayTeam.getPoints()+seasonService.getPointsPerDrawById(awayTeam.getSeasonId()));
		}
		teamRepository.deleteById(homeTeamName);
		teamRepository.deleteById(awayTeamName);
		
		teamRepository.save(homeTeam);
		teamRepository.save(awayTeam);
	}

}
