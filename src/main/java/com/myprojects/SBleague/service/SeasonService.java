package com.myprojects.SBleague.service;

import com.myprojects.SBleague.model.Season;
import com.myprojects.SBleague.web.dto.SeasonDto;

public interface SeasonService {
	Season saveSeason(SeasonDto seasonDto);
}
