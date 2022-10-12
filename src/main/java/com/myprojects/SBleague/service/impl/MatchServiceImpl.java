package com.myprojects.SBleague.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.myprojects.SBleague.model.Match;
import com.myprojects.SBleague.repository.MatchRepository;
import com.myprojects.SBleague.service.MatchService;
import com.myprojects.SBleague.service.TeamService;
import com.myprojects.SBleague.web.dto.MatchDto;

@Service
public class MatchServiceImpl implements MatchService {


	private TeamService teamService;
	private MatchRepository matchRepository;

	public MatchServiceImpl(TeamService teamService,MatchRepository matchRepository) {
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

		String matchId = Integer.toString(matchday) + homeTeam + awayTeam;

		Match match = new Match(matchId, matchday, homeTeam, awayTeam, homePoints, awayPoints);

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
		return filteredMatches.stream()
				.map(match -> matchToDto(match))
				.collect(Collectors.toList());
	}

	@Override
	public List<Match> getAllMatches() {
		return matchRepository.findAll();
	}
	
	@Override
	public List<MatchDto> getAllMatchDto() {
		return matchRepository.findAll().stream()
				.map(match -> matchToDto(match))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteMatch(MatchDto matchDto) {
		int matchday = matchDto.getMatchday();
		String homeTeamName = matchDto.getHomeTeam().replace(' ', '_');
		String awayTeamName = matchDto.getAwayTeam().replace(' ', '_');
		String matchId = Integer.toString(matchday) + homeTeamName + awayTeamName;

		// get existing match
		Match existingMatch = matchRepository.findById(matchId).get();

		// if the match has been played, delete its results
		if (existingMatch.getResult().getValue() >= 0) {
			// modify dto to send it to the teamService
			matchDto.setHomePoints(existingMatch.getHomePoints());
			matchDto.setAwayPoints(existingMatch.getAwayPoints());
			teamService.deleteStatistics(matchDto);
		}

		matchRepository.deleteById(matchId);
	}

	@Override
	public Match updateMatch(MatchDto matchDto) {
		int matchday = matchDto.getMatchday();
		String homeTeamName = matchDto.getHomeTeam().replace(' ', '_');
		String awayTeamName = matchDto.getAwayTeam().replace(' ', '_');
		String matchId = Integer.toString(matchday) + homeTeamName + awayTeamName;

		// get existing match
		Match existingMatch = matchRepository.findById(matchId).get();

		// if match has been played, delete old results
		if (existingMatch.getResult().getValue() >= 0) {
			// delete results of existing match
			MatchDto existingMatchDto = new MatchDto(existingMatch.getMatchday(), existingMatch.getHomeTeam(),
					existingMatch.getAwayTeam(), existingMatch.getHomePoints(), existingMatch.getAwayPoints());
			teamService.deleteStatistics(existingMatchDto);
		}

		// write new statistics
		if (matchDto.getHomePoints() > matchDto.getAwayPoints()) {
			existingMatch.getResult().setValue(2);
		} else if (matchDto.getHomePoints() > matchDto.getAwayPoints()) {
			existingMatch.getResult().setValue(0);
		} else if (matchDto.getHomePoints() == matchDto.getAwayPoints()) {
			existingMatch.getResult().setValue(1);
		}
		teamService.updateStatistics(matchDto);

		// delete old match
		matchRepository.deleteById(matchId);

		// update values
		existingMatch.setHomePoints(matchDto.getHomePoints());
		existingMatch.setAwayPoints(matchDto.getAwayPoints());

		return matchRepository.save(existingMatch);
	}
	
	private MatchDto matchToDto(Match match) {
		return new MatchDto(match.getMatchday()
				,match.getHomeTeam().replace("_", " ")
				,match.getAwayTeam().replace("_", " ")
				,match.getHomePoints()
				,match.getAwayPoints()
				,match.getResult().getValue());
	}

}
