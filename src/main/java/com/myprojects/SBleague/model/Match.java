package com.myprojects.SBleague.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "matches")
public class Match {

	@Id
	@Column(name = "matchName", unique = true)
	private String matchName;
	
	@NotNull(message="Matchday cannot be null.")
	@Min(value=1)
	@Column(name = "matchday")
	private int matchday;

	@NotEmpty(message="Home team cannot be empty")
	@Column(name = "homeTeam")
	private String homeTeam;

	@NotEmpty(message="Away team cannot be empty")
	@Column(name = "awayTeam")
	private String awayTeam;

	@Column(name = "homePoints")
	private int homePoints;

	@Column(name = "awayPoints")
	private int awayPoints;
	
	@Column(name = "result")
	private int result; // -1, match not played, 0 home loss, 1 draw, 2 home win

	public Match(){};
	
	public Match(String matchName, int matchday, String homeTeam, String awayTeam, int homePoints, int awayPoints) {
		super();
		this.matchName = matchName;
		this.matchday = matchday;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.homePoints = homePoints;
		this.awayPoints = awayPoints;
		this.result = -1;
	}

	public String getMatchName() {
		return matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
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
	
	public String showHomeTeamName() {
		return homeTeam.replace("_", " ");
	}
	
	public String showAwayTeamName() {
		return awayTeam.replace("_", " ");
	}
	
}
