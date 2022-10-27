package com.myprojects.SBleague.service;

import java.util.List;

import com.myprojects.SBleague.model.Match;
import com.myprojects.SBleague.web.dto.MatchDto;

public interface MatchService {
	List<Match> getAllMatches();
	List<MatchDto> getAllMatchDto();
	
	List<Match> getAllMatchesByDay(int matchday);
	List<MatchDto> getAllMatchDtoByDay(int matchday);
	
	Match getMatchById(Long matchId);
	
	Match saveMatch(MatchDto matchDto);
	
	Match updateMatch(MatchDto matchDto);
	
	Match updateMatchUser(MatchDto matchDto, Long userId);
	
	void deleteMatch(MatchDto matchDto);
	
	void resetResult(Long id);
	
	MatchDto getMatchDtoById(Long id);
	
	boolean isUpdatedByUserId(Long matchId, Long UserId);

	MatchDto getDtoWithUserInput(Long matchId, Long userId);

}
