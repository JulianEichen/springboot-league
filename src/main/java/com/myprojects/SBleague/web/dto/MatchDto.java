package com.myprojects.SBleague.web.dto;

import javax.validation.constraints.NotNull;

public class MatchDto {
	@NotNull
	private int matchday;
	@NotNull
	private String homeTeam;
	@NotNull
	private String awayTeam; 
	@NotNull
	private int homePoints;
	@NotNull
	private int awayPoints;
	
	// default const
	public MatchDto() {}
	
	// param const
	public MatchDto(int matchday, String homeTeam, String awayTeam, int homePoints, int awayPoints) {
		super();
		this.matchday = matchday;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.homePoints = homePoints;
		this.awayPoints = awayPoints;
	}
	
	public int getMatchday() {
		return matchday;
	}
	public void setMatchday(int matchday) {
		this.matchday = matchday;
	}
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}
	public int getHomePoints() {
		return homePoints;
	}
	public void setHomePoints(int homePoints) {
		this.homePoints = homePoints;
	}
	public int getAwayPoints() {
		return awayPoints;
	}
	public void setAwayPoints(int awayPoints) {
		this.awayPoints = awayPoints;
	}

}
