package com.myprojects.SBleague.service;

import java.util.List;

import com.myprojects.SBleague.model.Match;
import com.myprojects.SBleague.web.dto.MatchRegistrationDTO;

public interface MatchService {
	List<Match> getAllMatches();
	
	List<Match> getAllMatchesByDay(int matchday);
	
	Match saveMatch(MatchRegistrationDTO matchRegistrationDTO);
	
	void deleteMatch(Long Id);
}
