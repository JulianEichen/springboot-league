package com.myprojects.SBleague.service;

import java.util.List;

import com.myprojects.SBleague.model.Match;
import com.myprojects.SBleague.web.dto.MatchDto;

public interface MatchService {
	List<Match> getAllMatches();
	
	List<Match> getAllMatchesByDay(int matchday);
	
	Match saveMatch(MatchDto matchDto);
	
	void deleteMatch(MatchDto matchDto);
}
