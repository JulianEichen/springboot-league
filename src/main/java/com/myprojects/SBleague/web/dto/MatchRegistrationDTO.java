package com.myprojects.SBleague.web.dto;

public class MatchRegistrationDTO {
	private int matchday;
	private String homeTeam;
	private String awayTeam; 
	private int homePoints;
	private int awayPoints;
	
	// default const
	public MatchRegistrationDTO() {}
	
	// param const
	public MatchRegistrationDTO(int matchday, String homeTeam, String awayTeam, int homePoints, int awayPoints) {
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
