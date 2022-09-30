package com.myprojects.SBleague.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myprojects.SBleague.model.Match;
import com.myprojects.SBleague.repository.MatchRepository;
import com.myprojects.SBleague.service.MatchService;
import com.myprojects.SBleague.web.dto.MatchRegistrationDTO;

@Service
public class MatchServiceImpl implements MatchService{
	
	private MatchRepository matchRepository;
	public MatchServiceImpl(MatchRepository matchRepository) {
		super();
		this.matchRepository = matchRepository;
	}
	
	
	@Override
	public Match saveMatch(MatchRegistrationDTO matchDTO) {

		int homePoints = matchDTO.getHomePoints();
		int awayPoints = matchDTO.getAwayPoints();
		int result = 0;
		
		// determine result
		if(homePoints>awayPoints){
			result = 1;
		}else if(homePoints<awayPoints) {
			result = -1;
		}

		Match match = new Match(matchDTO.getMatchday(),
				matchDTO.getHomeTeam(),
				matchDTO.getAwayTeam(),
				homePoints,
				awayPoints,
				result);

		return matchRepository.save(match);
	}
	
	@Override
	public List<Match> getAllMatchesByDay(int matchday) {
		List<Match> filteredMatches = matchRepository.findAll()
		List<Movie> filteredMovies = getMovieData()
                .stream()
                .filter(movie -> movie.getLength() < 150)
                .collect(Collectors.toList());
		return null;
	}
	
	@Override
	public List<Match> getAllMatches() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteMatch(Long Id) {
		// TODO Auto-generated method stub
		
	}
}
