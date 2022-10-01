package com.myprojects.SBleague.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "matches")
public class Match {

	@Id
	@Column(name = "matchName", unique = true)
	private String matchName;

	@Column(name = "matchday")
	private int matchday;

	@Column(name = "homeTeam")
	private String homeTeam;

	@Column(name = "awayTeam")
	private String awayTeam;

	@Column(name = "homePoints")
	private int homePoints;

	@Column(name = "awayPoints")
	private int awayPoints;

	@Column(name = "result")
	private int result; // 1, -1, 0 -> home win, loss, draw

	public Match(){};
	
	public Match(String matchName, int matchday, String homeTeam, String awayTeam, int homePoints, int awayPoints, int result) {
		super();
		this.matchName = matchName;
		this.matchday = matchday;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.homePoints = homePoints;
		this.awayPoints = awayPoints;
		this.result = result;
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
}
