package com.myprojects.SBleague.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprojects.SBleague.model.Match;
import com.myprojects.SBleague.model.Team;
import com.myprojects.SBleague.repository.MatchRepository;
import com.myprojects.SBleague.repository.TeamRepository;
import com.myprojects.SBleague.service.MatchService;
import com.myprojects.SBleague.service.TeamService;
import com.myprojects.SBleague.web.dto.MatchDto;

@Service
public class MatchServiceImpl implements MatchService {

	@Autowired
	private TeamService teamService;

	private MatchRepository matchRepository;

	public MatchServiceImpl(MatchRepository matchRepository) {
		super();
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
		return null;
	}

	@Override
	public List<Match> getAllMatches() {
		return matchRepository.findAll();
	}

	@Override
	public void deleteMatch(MatchDto matchDto) {
		int matchday = matchDto.getMatchday();
		String homeTeamName = matchDto.getHomeTeam().replace(' ', '_');
		String awayTeamName = matchDto.getAwayTeam().replace(' ', '_');
		String matchName = Integer.toString(matchday) + homeTeamName + awayTeamName;

		// get existing match
		Match existingMatch = matchRepository.findById(matchName).get();

		// modify dto to send it to the teamService
		matchDto.setHomePoints(existingMatch.getHomePoints());
		matchDto.setAwayPoints(existingMatch.getAwayPoints());
		teamService.deleteResults(matchDto);

		matchRepository.deleteById(matchName);
	}

	@Override
	public Match updateMatch(MatchDto matchDto) {
		int matchday = matchDto.getMatchday();
		String homeTeamName = matchDto.getHomeTeam().replace(' ', '_');
		String awayTeamName = matchDto.getAwayTeam().replace(' ', '_');
		String matchName = Integer.toString(matchday) + homeTeamName + awayTeamName;

		// get existing match
		Match existingMatch = matchRepository.findById(matchName).get();

		// update values
		existingMatch.setHomePoints(matchDto.getHomePoints());
		existingMatch.setAwayPoints(matchDto.getAwayPoints());

		// send dto to the teamService
		teamService.updateResults(matchDto);

		matchRepository.deleteById(matchName);

		return matchRepository.save(existingMatch);
	}

}
