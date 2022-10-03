package com.myprojects.SBleague.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myprojects.SBleague.model.Team;
import com.myprojects.SBleague.repository.TeamRepository;
import com.myprojects.SBleague.service.TeamService;
import com.myprojects.SBleague.web.dto.MatchDto;
import com.myprojects.SBleague.web.dto.MatchRegistrationDTO;
import com.myprojects.SBleague.web.dto.TeamRegistrationDTO;

@Service
public class TeamServiceImpl implements TeamService {

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
		String teamName = teamDto.getName().replace(' ', '_');
		Team team = new Team(teamName, teamDto.getCoach(), 0, 0, 0, 0, 0);
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
	public void deleteResults(MatchDto matchDto) {
		
		String homeTeamName = matchDto.getHomeTeam().replace(" ","_");
		String awayTeamName = matchDto.getAwayTeam().replace(" ","_");
		
		Team homeTeam = teamRepository.findById(homeTeamName).get();
		Team awayTeam = teamRepository.findById(awayTeamName).get();
		
		if (matchDto.getHomePoints() > matchDto.getAwayPoints()) {
			// result statistics
			homeTeam.setWins(homeTeam.getWins()-1);
			awayTeam.setLosses(awayTeam.getLosses()-1);
			// league points
			homeTeam.setPoints(homeTeam.getPoints()-2);
		}else if(matchDto.getHomePoints() < matchDto.getAwayPoints()) {
			// result statistics
			homeTeam.setLosses(homeTeam.getLosses()-1);
			awayTeam.setWins(awayTeam.getWins()-1);
			// league points
			awayTeam.setPoints(awayTeam.getPoints()-2);
		}else if(matchDto.getHomePoints() == matchDto.getAwayPoints()){
			// result statistics
			homeTeam.setDraws(homeTeam.getDraws()-1);
			awayTeam.setDraws(awayTeam.getDraws()-1);
			// league points
			homeTeam.setPoints(homeTeam.getPoints()-1);
			awayTeam.setPoints(awayTeam.getPoints()-1);
		}
		
		teamRepository.deleteById(homeTeamName);
		teamRepository.deleteById(awayTeamName);
		
		teamRepository.save(homeTeam);
		teamRepository.save(awayTeam);
	}

	@Override
	public void updateResults(MatchDto matchDto) {
		String homeTeamName = matchDto.getHomeTeam().replace(" ","_");
		String awayTeamName = matchDto.getAwayTeam().replace(" ","_");
		
		Team homeTeam = teamRepository.findById(homeTeamName).get();
		Team awayTeam = teamRepository.findById(awayTeamName).get();

		if (matchDto.getHomePoints() > matchDto.getAwayPoints()) {
			// result statistics
			homeTeam.setWins(homeTeam.getWins()+1);
			awayTeam.setLosses(awayTeam.getLosses()+1);
			// league points
			homeTeam.setPoints(homeTeam.getPoints()+2);
		}else if(matchDto.getHomePoints() < matchDto.getAwayPoints()) {
			// result statistics
			homeTeam.setLosses(homeTeam.getLosses()+1);
			awayTeam.setWins(awayTeam.getWins()+1);
			// league points
			awayTeam.setPoints(awayTeam.getPoints()+2);
		}else if(matchDto.getHomePoints() == matchDto.getAwayPoints()){
			// result statistics
			homeTeam.setDraws(homeTeam.getDraws()+1);
			awayTeam.setDraws(awayTeam.getDraws()+1);
			// league points
			homeTeam.setPoints(homeTeam.getPoints()+1);
			awayTeam.setPoints(awayTeam.getPoints()+1);
		}
		teamRepository.deleteById(homeTeamName);
		teamRepository.deleteById(awayTeamName);
		
		teamRepository.save(homeTeam);
		teamRepository.save(awayTeam);
	}

}
