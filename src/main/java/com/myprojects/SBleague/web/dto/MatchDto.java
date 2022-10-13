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
	
	private int result;
	private Long id;

	// default const
	public MatchDto() {
	}

	// param const
	public MatchDto(Long id, int matchday, String homeTeam, String awayTeam, int homePoints, int awayPoints) {
		super();
		this.id = id;
		this.matchday = matchday;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.homePoints = homePoints;
		this.awayPoints = awayPoints;
	}
	
	// constructor for table
	public MatchDto(Long id, int matchday, String homeTeam, String awayTeam, int homePoints, int awayPoints, int result) {
		super();
		this.id = id;
		this.matchday = matchday;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.homePoints = homePoints;
		this.awayPoints = awayPoints;
		this.result = result;
	}
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	
}
