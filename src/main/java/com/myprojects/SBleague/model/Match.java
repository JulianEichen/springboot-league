package com.myprojects.SBleague.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "matches")
public class Match {

	@Id
	@Column(name = "match_id", unique = true)
	private String id;
	
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
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "result_id", referencedColumnName = "result_id")
	private Result result;
	
	public Match(){};
	
	public Match(String id, int matchday, String homeTeam, String awayTeam, int homePoints, int awayPoints) {
		super();
		this.id = id;
		this.matchday = matchday;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.homePoints = homePoints;
		this.awayPoints = awayPoints;
		this.result = new Result();
		this.result.setValue(-1);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
	public String showHomeTeamName() {
		return homeTeam.replace("_", " ");
	}
	
	public String showAwayTeamName() {
		return awayTeam.replace("_", " ");
	}
	
}
