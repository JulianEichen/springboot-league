package com.myprojects.SBleague.service.impl;

import java.util.Comparator;

import com.myprojects.SBleague.model.Team;

public class TeamChecker implements Comparator<Team> {

	@Override
	public int compare(Team team1, Team team2) {
		// compare by points -> wins -> name -> coach -> id
		if (team1.getPoints() > team2.getPoints()) {
			return -1;
		}else if(team1.getWins() > team2.getWins()) {
			return -1;
		}else if(team1.getName() != team2.getName()) {
			team2.getName().compareTo(team1.getName());
		}else if(team1.getCoach() != team2.getCoach()) {
			team2.getCoach().compareTo(team1.getCoach());
		}else if(team1.getId() > team2.getId()) {
			return 1;
		}
		return 0;
	}
}
