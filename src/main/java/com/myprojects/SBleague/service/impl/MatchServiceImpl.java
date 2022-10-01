package com.myprojects.SBleague.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.myprojects.SBleague.model.Match;
import com.myprojects.SBleague.repository.MatchRepository;
import com.myprojects.SBleague.service.MatchService;
import com.myprojects.SBleague.web.dto.MatchRegistrationDTO;

@Service
public class MatchServiceImpl implements MatchService {

	private MatchRepository matchRepository;

	public MatchServiceImpl(MatchRepository matchRepository) {
		super();
		this.matchRepository = matchRepository;
	}

	@Override
	public Match saveMatch(MatchRegistrationDTO matchDto) {
		
		int matchday = matchDto.getMatchday();
		String homeTeam = matchDto.getHomeTeam();
		String awayTeam = matchDto.getAwayTeam();
		int homePoints = matchDto.getHomePoints();
		int awayPoints = matchDto.getAwayPoints();
		int result = 0;

		// determine result
		if (homePoints > awayPoints) {
			result = 1;
		} else if (homePoints < awayPoints) {
			result = -1;
		}
		
		String matchName = Integer.toString(matchday)+homeTeam+awayTeam;
		
		Match match = new Match(matchName, matchday, homeTeam, awayTeam, homePoints,
				awayPoints, result);

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
	public void deleteMatch(Long Id) {
		// TODO Auto-generated method stub

	}
}
