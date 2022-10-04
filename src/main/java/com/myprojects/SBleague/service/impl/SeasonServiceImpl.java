package com.myprojects.SBleague.service.impl;

import org.springframework.stereotype.Service;

import com.myprojects.SBleague.model.Season;
import com.myprojects.SBleague.repository.SeasonRepository;
import com.myprojects.SBleague.service.SeasonService;
import com.myprojects.SBleague.web.dto.SeasonDto;

@Service
public class SeasonServiceImpl implements SeasonService{
	
	private SeasonRepository seasonRepository;
	
	public SeasonServiceImpl(SeasonRepository seasonRepository) {
		super();
		this.seasonRepository = seasonRepository;
	}
	
	@Override
	public Season saveSeason(SeasonDto seasonDto) {
		String seasonName = seasonDto.getSeasonName();
		int numberOfTeams = seasonDto.getNumberOfTeams();
		int numberOfMatches = seasonDto.getNumberOfMatches();
		int leaguePointsPerWin = seasonDto.getLeaguePointsPerWin();
		int leaguePointsPerDraw = seasonDto.getLeaguePointsPerDraw();
		int leaguePointsPerLoss = seasonDto.getLeaguePointsPerLoss();
		Season season = new Season(seasonName,numberOfTeams,numberOfMatches,leaguePointsPerWin,leaguePointsPerDraw,leaguePointsPerLoss);
		return seasonRepository.save(season);
	}
}
