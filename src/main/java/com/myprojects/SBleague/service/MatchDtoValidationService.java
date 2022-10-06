package com.myprojects.SBleague.service;

import com.myprojects.SBleague.web.dto.MatchDto;

public interface MatchDtoValidationService {
	public String validateMatchExistence(MatchDto matchDto);
	public String validateMatchTeams(MatchDto matchDto);
}
