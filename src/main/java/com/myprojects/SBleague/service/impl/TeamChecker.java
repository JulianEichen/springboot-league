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
		}
		return 0;
	}
}
