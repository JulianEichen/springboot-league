package com.myprojects.SBleague.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myprojects.SBleague.model.Season;
import com.myprojects.SBleague.repository.SeasonRepository;
import com.myprojects.SBleague.service.SeasonService;
import com.myprojects.SBleague.web.dto.SeasonDto;

@Service
public class SeasonServiceImpl implements SeasonService {

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
		boolean active = seasonDto.isActive();

		Season season = new Season(seasonName, numberOfTeams, numberOfMatches, leaguePointsPerWin, leaguePointsPerDraw,
				leaguePointsPerLoss, active);
		return seasonRepository.save(season);
	}

	@Override
	public List<Season> getAllSeasons() {
		return seasonRepository.findAll();
	}

	@Override
	public int getPointsPerWinById(Long seasonId) {
		return seasonRepository.findById(seasonId).get().getLeaguePointsPerWin();
	}

	@Override
	public int getPointsPerDrawById(Long seasonId) {
		return seasonRepository.findById(seasonId).get().getLeaguePointsPerDraw();
	}

	@Override
	public int getPointsPerLossById(Long seasonId) {
		return seasonRepository.findById(seasonId).get().getLeaguePointsPerLoss();
	}

	@Override
	public void setActive(Long seasonId) {
		List<Season> seasons = seasonRepository.findAll();
		seasons.forEach(season -> season.setActive(false));
		seasons.forEach(season -> seasonRepository.save(season));

		Season activeSeason = seasonRepository.findById(seasonId).get();
		activeSeason.setActive(true);
		seasonRepository.save(activeSeason);
	}

	@Override
	public int getActivePointsPerWin() {
		Season activeSeason = seasonRepository.findByActive(true);
		if (activeSeason == null) {
			return 0;
		} else {
			return seasonRepository.findByActive(true).getLeaguePointsPerWin();
		}
	}

	@Override
	public int getActivePointsPerDraw() {
		Season activeSeason = seasonRepository.findByActive(true);
		if (activeSeason == null) {
			return 0;
		} else {
			return seasonRepository.findByActive(true).getLeaguePointsPerDraw();
		}
	}

	@Override
	public int getActivePointsPerLoss() {
		Season activeSeason = seasonRepository.findByActive(true);
		if (activeSeason == null) {
			return 0;
		} else {
			return seasonRepository.findByActive(true).getLeaguePointsPerLoss();
		}
	}

	@Override
	public int getActiveNumberOfTeams() {
		Season activeSeason = seasonRepository.findByActive(true);
		if (activeSeason == null) {
			return 0;
		} else {
			return seasonRepository.findByActive(true).getNumberOfTeams();
		}
	}

	@Override
	public int getActiveNumberOfMatchdays() {
		Season activeSeason = seasonRepository.findByActive(true);
		if (activeSeason == null) {
			return 0;
		} else {
			return seasonRepository.findByActive(true).getNumberOfMatchdays();
		}
	}

	@Override
	public String getActiveName() {
		Season activeSeason = seasonRepository.findByActive(true);
		if (activeSeason == null) {
			return "No Season available";
		} else {
			return seasonRepository.findByActive(true).getSeasonName();
		}
	}

	@Override
	public int getActiveNumberOfMatches() {
		Season activeSeason = seasonRepository.findByActive(true);
		if (activeSeason == null) {
			return 0;
		} else {
			return seasonRepository.findByActive(true).getNumberOfMatches();
		}
	}

}
