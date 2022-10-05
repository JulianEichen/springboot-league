package com.myprojects.SBleague.service;

import java.util.List;

import com.myprojects.SBleague.model.Season;
import com.myprojects.SBleague.web.dto.SeasonDto;

public interface SeasonService {
	List<Season> getAllSeasons();

	Season saveSeason(SeasonDto seasonDto);

	int getPointsPerWinById(Long seasonId);

	int getPointsPerDrawById(Long seasonId);

	int getPointsPerLossById(Long seasonId);
	
	int getActivePointsPerWin();
	int getActivePointsPerDraw();
	int getActivePointsPerLoss();
	int getActiveNumberOfTeams();
	
	void setActive(Long seasonId);
}
