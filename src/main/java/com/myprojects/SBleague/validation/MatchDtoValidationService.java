package com.myprojects.SBleague.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myprojects.SBleague.repository.MatchRepository;
import com.myprojects.SBleague.web.dto.MatchDto;

@Service
public class MatchDtoValidationService {

	@Autowired
	MatchRepository matchRepository;

	public String validateMatchDtoRegistration(MatchDto matchDto) {
		String message = "";

		int matchday = matchDto.getMatchday();
		String homeTeam = matchDto.getHomeTeam();
		String awayTeam = matchDto.getAwayTeam();
		String matchId = Integer.toString(matchday) + homeTeam.replace(" ", "_") + awayTeam.replace(" ", "_");

		if (!matchRepository.existsById(matchId)) {
			message = "Match does not exist for " + homeTeam + " and " + awayTeam + " on matchday " + matchday + "!";
		}

		return message;
	}

}
