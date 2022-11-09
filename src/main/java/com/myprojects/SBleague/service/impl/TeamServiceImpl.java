package com.myprojects.SBleague.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.myprojects.SBleague.model.Team;
import com.myprojects.SBleague.repository.TeamRepository;
import com.myprojects.SBleague.service.SeasonService;
import com.myprojects.SBleague.service.TeamService;
import com.myprojects.SBleague.usermanagement.model.User;
import com.myprojects.SBleague.usermanagement.service.UserService;
import com.myprojects.SBleague.web.dto.MatchDto;
import com.myprojects.SBleague.web.dto.TeamDto;

@Service
public class TeamServiceImpl implements TeamService {

	// inject repositories
	private TeamRepository teamRepository;
	private SeasonService seasonService;
	private UserService userService;

	public TeamServiceImpl(TeamRepository teamRepository, SeasonService seasonService, UserService userService) {
		super();
		this.teamRepository = teamRepository;
		this.seasonService = seasonService;
		this.userService = userService;
	}

	@Override
	public List<Team> getAllTeams() {
		return teamRepository.findAll();
	}

	@Override
	public List<TeamDto> getAllTeamDto() {
		List<TeamDto> dtoList = teamRepository.findAll().stream().map(team -> teamToDto(team))
				.collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public List<Team> getAllTeamsOrdered() {
		TeamChecker checker = new TeamChecker();
		List<Team> teams = teamRepository.findAll();
		teams.sort(checker);
		return teams;
	}

	@Override
	public List<TeamDto> getAllTeamDtoOrdered() {
		TeamChecker checker = new TeamChecker();
		List<Team> teams = teamRepository.findAll();
		teams.sort(checker);

		List<TeamDto> dtoList = teams.stream().map(team -> teamToDto(team)).collect(Collectors.toList());
		return dtoList;
	}
	
	@Override
	public List<TeamDto> getAllActiveTeamDtoOrdered() {
		TeamChecker checker = new TeamChecker();
		List<Team> teams = teamRepository.findAllByEnrolled(true);
		teams.sort(checker);
		
		List<TeamDto> dtoList = teams.stream().map(team -> teamToDto(team)).collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public List<Team> getAllTeamsByOwnerId(Long ownerId) {
		List<Team> teams = teamRepository.findAllByOwnerId(ownerId);
		return teams;
	}

	@Override
	public List<TeamDto> getAllTeamDtoByOwnerId(Long ownerId) {
		List<TeamDto> dtoList = teamRepository.findAllByOwnerId(ownerId).stream().map(team -> teamToDto(team))
				.collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public Team saveTeam(TeamDto teamDto, String userEmail) {
		String teamName = teamDto.getName().replace(" ", "_");
		User owner = userService.findUserByEmail(userEmail);
		Team team = new Team();
		team.setName(teamName);
		team.setOwner(owner);
		return teamRepository.save(team);
	}

	@Override
	public Team getTeamById(Long Id) {
		return null;
	}

	@Override
	public Team updateTeam(Team team) {
		return null;
	}

	@Override
	public void deleteTeamById(Long Id) {
	}

	@Override
	public void deleteStatistics(MatchDto matchDto) {

		String homeTeamName = matchDto.getHomeTeam().replace(" ", "_");
		String awayTeamName = matchDto.getAwayTeam().replace(" ", "_");

		Team homeTeam = teamRepository.findByName(homeTeamName);
		Team awayTeam = teamRepository.findByName(awayTeamName);

		// home win
		if (matchDto.getHomePoints() > matchDto.getAwayPoints()) {
			// result statistics
			homeTeam.setWins(homeTeam.getWins() - 1);
			awayTeam.setLosses(awayTeam.getLosses() - 1);
			// league points
			homeTeam.setPoints(homeTeam.getPoints() - seasonService.getActivePointsPerWin());
			awayTeam.setPoints(awayTeam.getPoints() - seasonService.getActivePointsPerLoss());
			// home loss
		} else if (matchDto.getHomePoints() < matchDto.getAwayPoints()) {
			// result statistics
			homeTeam.setLosses(homeTeam.getLosses() - 1);
			awayTeam.setWins(awayTeam.getWins() - 1);
			// league points
			homeTeam.setPoints(homeTeam.getPoints() - seasonService.getActivePointsPerLoss());
			awayTeam.setPoints(awayTeam.getPoints() - seasonService.getActivePointsPerWin());
			// draw
		} else if (matchDto.getHomePoints() == matchDto.getAwayPoints()) {
			// result statistics
			homeTeam.setDraws(homeTeam.getDraws() - 1);
			awayTeam.setDraws(awayTeam.getDraws() - 1);
			// league points
			homeTeam.setPoints(homeTeam.getPoints() - seasonService.getActivePointsPerDraw());
			awayTeam.setPoints(awayTeam.getPoints() - seasonService.getActivePointsPerDraw());
		}

		// teamRepository.deleteById(homeTeamName);
		// teamRepository.deleteById(awayTeamName);

		teamRepository.save(homeTeam);
		teamRepository.save(awayTeam);
	}

	@Override
	public void updateStatistics(MatchDto matchDto) {
		String homeTeamName = matchDto.getHomeTeam().replace(" ", "_");
		String awayTeamName = matchDto.getAwayTeam().replace(" ", "_");

		Team homeTeam = teamRepository.findByName(homeTeamName);
		Team awayTeam = teamRepository.findByName(awayTeamName);

		if (matchDto.getHomePoints() > matchDto.getAwayPoints()) {
			// meta statistics
			homeTeam.setMatches(homeTeam.getMatches() + 1);
			awayTeam.setMatches(awayTeam.getMatches() + 1);
			
			homeTeam.setWins(homeTeam.getWins() + 1);
			awayTeam.setLosses(awayTeam.getLosses() + 1);
			// league points
			homeTeam.setPoints(homeTeam.getPoints() + seasonService.getActivePointsPerWin());
			awayTeam.setPoints(awayTeam.getPoints() + seasonService.getActivePointsPerLoss());
		} else if (matchDto.getHomePoints() < matchDto.getAwayPoints()) {
			// meta statistics
			homeTeam.setMatches(homeTeam.getMatches() + 1);
			awayTeam.setMatches(awayTeam.getMatches() + 1);
			
			homeTeam.setLosses(homeTeam.getLosses() + 1);
			awayTeam.setWins(awayTeam.getWins() + 1);
			// league points
			homeTeam.setPoints(homeTeam.getPoints() + seasonService.getActivePointsPerLoss());
			awayTeam.setPoints(awayTeam.getPoints() + seasonService.getActivePointsPerWin());
		} else if (matchDto.getHomePoints() == matchDto.getAwayPoints()) {
			// meta statistics
			homeTeam.setMatches(homeTeam.getMatches() + 1);
			awayTeam.setMatches(awayTeam.getMatches() + 1);
			
			homeTeam.setDraws(homeTeam.getDraws() + 1);
			awayTeam.setDraws(awayTeam.getDraws() + 1);
			// league points
			homeTeam.setPoints(homeTeam.getPoints() + seasonService.getActivePointsPerDraw());
			awayTeam.setPoints(awayTeam.getPoints() + seasonService.getActivePointsPerDraw());
		}

		teamRepository.save(homeTeam);
		teamRepository.save(awayTeam);
	}

	private TeamDto teamToDto(Team team) {
		TeamDto dto = new TeamDto(team.getName().replace("_", " "), team.getOwner().getName().replace("_", " "),
				team.getMatches(), team.getWins(), team.getDraws(), team.getLosses(), team.getPoints());
		
		dto.setId(team.getId());
		dto.setEnrolled(team.isEnrolled());
		
		return dto;
	}

	@Override
	public String getOwnerNameByTeamName(String teamName) {
		return teamRepository.findByName(teamName).getOwner().getName();
	}

	@Override
	public void resetTeamById(Long id) {
		Team team = teamRepository.findById(id).get();
		team.reset();
		teamRepository.save(team);
	}

	@Override
	public void enrollmentById(Long id, boolean enrolled) {
		Team team = teamRepository.findById(id).get();
		team.setEnrolled(enrolled);
		teamRepository.save(team);
	}

}
