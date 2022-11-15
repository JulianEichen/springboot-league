package com.myprojects.SBleague.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.myprojects.SBleague.model.Match;
import com.myprojects.SBleague.model.Result;
import com.myprojects.SBleague.repository.MatchRepository;
import com.myprojects.SBleague.service.MatchService;
import com.myprojects.SBleague.service.TeamService;
import com.myprojects.SBleague.web.dto.MatchDto;

@Service
public class MatchServiceImpl implements MatchService {

	private TeamService teamService;
	private MatchRepository matchRepository;

	public MatchServiceImpl(TeamService teamService, MatchRepository matchRepository) {
		super();
		this.teamService = teamService;
		this.matchRepository = matchRepository;
	}

	@Override
	public Match saveMatch(MatchDto matchDto) {

		int matchday = matchDto.getMatchday();
		String homeTeam = matchDto.getHomeTeam().replace(' ', '_');
		String awayTeam = matchDto.getAwayTeam().replace(' ', '_');
		int homePoints = matchDto.getHomePoints();
		int awayPoints = matchDto.getAwayPoints();

		String matchName = Integer.toString(matchday) + homeTeam + awayTeam;

		Match match = new Match(matchName, matchday, homeTeam, awayTeam, homePoints, awayPoints);

		return matchRepository.save(match);
	}

	@Override
	public List<Match> getAllMatchesByDay(int matchday) {
		List<Match> filteredMatches = matchRepository.findAll();
		filteredMatches = filteredMatches.stream().filter(match -> match.getMatchday() == matchday)
				.collect(Collectors.toList());
		return filteredMatches;
	}

	@Override
	public List<MatchDto> getAllMatchDtoByDay(int matchday) {
		List<Match> filteredMatches = matchRepository.findAll();
		filteredMatches = filteredMatches.stream().filter(match -> match.getMatchday() == matchday)
				.collect(Collectors.toList());
		return filteredMatches.stream().map(match -> matchToDto(match)).collect(Collectors.toList());
	}

	@Override
	public List<Match> getAllMatches() {
		return matchRepository.findAll();
	}

	@Override
	public List<MatchDto> getAllMatchDto() {
		return matchRepository.findAll().stream().map(match -> matchToDto(match)).collect(Collectors.toList());
	}

	@Override
	public void deleteMatch(MatchDto matchDto) {
		int matchday = matchDto.getMatchday();
		String homeTeamName = matchDto.getHomeTeam().replace(' ', '_');
		String awayTeamName = matchDto.getAwayTeam().replace(' ', '_');
		String matchName = Integer.toString(matchday) + homeTeamName + awayTeamName;

		// get existing match
		Match existingMatch = matchRepository.findByName(matchName);

		// if the match has been played, delete its results
		if (existingMatch.getResult().getValue() >= 0) {
			// modify dto to send it to the teamService
			matchDto.setHomePoints(existingMatch.getHomePoints());
			matchDto.setAwayPoints(existingMatch.getAwayPoints());
			teamService.deleteStatistics(matchDto);
		}

		matchRepository.deleteByName(matchName);
	}

	@Override
	public MatchDto getMatchDtoById(Long id) {
		Match match = matchRepository.findById(id);
		return this.matchToDto(match);
	}

	@Override
	public Match updateMatchUser(MatchDto matchDto, Long userId) {
		Long matchId = matchDto.getId();
		// get existing match
		Match existingMatch = matchRepository.findById(matchId);
		Result existingResult = existingMatch.getResult();

		// we don't want users to update a valid result again
		if (existingResult.isValid()) {
			return existingMatch;
		}

		// identify which team is owned by the user
		List<String> userTeams = teamService.getAllTeamDtoByOwnerId(userId).stream().map(team -> team.getName())
				.collect(Collectors.toList());

		String userTeam = userTeams.contains(existingMatch.showHomeTeamName()) ? existingMatch.showHomeTeamName()
				: userTeams.contains(existingMatch.showAwayTeamName()) ? existingMatch.showAwayTeamName() : "";

		if (userTeam.equals(existingMatch.showHomeTeamName())) { // user owns home team
			// set points in result
			existingResult.setAwayPointsH(matchDto.getAwayPoints());
			existingResult.setHomePointsH(matchDto.getHomePoints());
			// update result value
			existingResult.updateValue();
			// update stats in teams
			if (existingResult.isValid()) {
				teamService.updateStatistics(matchDto);
			}

		} else if (userTeam.equals(existingMatch.showAwayTeamName())) { // user owns away team
			// set points in result
			existingResult.setAwayPointsA(matchDto.getAwayPoints());
			existingResult.setHomePointsA(matchDto.getHomePoints());
			// update result value
			existingResult.updateValue();
			// update stats in teams
			if (existingResult.isValid()) {
				teamService.updateStatistics(matchDto);
			}

		} else {
			// error
		}

		existingMatch.setResult(existingResult);
		existingMatch.updatePoints();

		matchRepository.save(existingMatch);

		return null;
	}

	@Override
	public Match updateMatchAdmin(MatchDto matchDto) {
		Long matchId = matchDto.getId();
		// get existing match
		Match existingMatch = matchRepository.findById(matchId);
		Result existingResult = existingMatch.getResult();

		// reverse old resulting league stats
		if (existingResult.isValid()) { // if result is valid, it lead to modified stas
			teamService.deleteStatistics(matchDto);
		}
		
		// update the result
		existingResult.setAwayPointsH(matchDto.getAwayPoints());
		existingResult.setHomePointsH(matchDto.getHomePoints());
		existingResult.setAwayPointsA(matchDto.getAwayPoints());
		existingResult.setHomePointsA(matchDto.getHomePoints());
		// update result value
		existingResult.updateValue();
		// update league stats
		existingMatch.setResult(existingResult);
		existingMatch.updatePoints();
		
		teamService.updateStatistics(matchDto);
		
		return matchRepository.save(existingMatch);
	}

	@Override
	public Match getMatchById(Long matchId) {
		return matchRepository.findById(matchId);
	}

	@Override
	public boolean isUpdatedByUserId(Long matchId, Long userId) {
		List<String> userTeams = teamService.getAllTeamsByOwnerId(userId).stream().map(team -> team.showTeamName())
				.collect(Collectors.toList());
		Match match = matchRepository.findById(matchId);

		if (userTeams.contains(match.showHomeTeamName())) { // user owns home team
			if (match.getResult().getAwayPointsH() >= 0 || match.getResult().getHomePointsH() >= 0) {
				// home team owner already registered valid points values
				return true;
			}
		} else if (userTeams.contains(match.showAwayTeamName())) {
			if (match.getResult().getAwayPointsA() >= 0 || match.getResult().getHomePointsA() >= 0) {
				return true;
			}
		} else {
			// error
		}
		return false;
	}

	@Override
	public MatchDto getDtoWithUserInput(Long matchId, Long userId) {
		List<String> userTeams = teamService.getAllTeamsByOwnerId(userId).stream().map(team -> team.showTeamName())
				.collect(Collectors.toList());
		Match match = matchRepository.findById(matchId);
		MatchDto matchDto = this.matchToDto(match);

		// set default values
		matchDto.setAwayPoints(-1);
		matchDto.setHomePoints(-1);

		if (userTeams.contains(match.showHomeTeamName())) { // user owns home team
			if (match.getResult().getAwayPointsH() >= 0 || match.getResult().getHomePointsH() >= 0) {
				matchDto.setAwayPoints(match.getResult().getAwayPointsH());
				matchDto.setHomePoints(match.getResult().getHomePointsH());
			}
		} else if (userTeams.contains(match.showAwayTeamName())) {
			if (match.getResult().getAwayPointsA() >= 0 || match.getResult().getHomePointsA() >= 0) {
				matchDto.setAwayPoints(match.getResult().getAwayPointsA());
				matchDto.setHomePoints(match.getResult().getHomePointsA());
			} else {
				// error
			}

		}
		return matchDto;
	}

	@Override
	public void resetResult(Long id) {
		Match match = matchRepository.findById(id);

		if (!match.getResult().isValid()) {
			return;
		}
		teamService.deleteStatistics(this.matchToDto(match));
		match.reset();
		matchRepository.save(match);
	}

	private MatchDto matchToDto(Match match) {
		return new MatchDto(match.getId(), match.getMatchday(), match.getHomeTeam().replace("_", " "),
				match.getAwayTeam().replace("_", " "), match.getHomePoints(), match.getAwayPoints(),
				match.getResult().getValue());
	}

	@Override
	public void deleteById(Long id) {
		matchRepository.delete(matchRepository.findById(id));
	}

}
