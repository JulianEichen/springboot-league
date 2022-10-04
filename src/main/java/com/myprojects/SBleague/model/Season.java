package com.myprojects.SBleague.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="season")
public class Season {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "season name")
	private String seasonName;
	
	@NotNull(message="Number of teams cannot be null.")
	@Min(value=2)
	@Column(name = "number of Teams")
	private int numberOfTeams;
	
	@NotNull(message="Number of matches cannot be null.")
	@Min(value=1)
	@Column(name = "number of matches")
	private int numberOfMatches;
	
	@Column(name = "matchdays")
	private int numberOfMatchdays;
	
	// private int numberOfGamesPerMatch;
	@NotNull(message="Points per win cannot be null.")
	@Column(name = "points per win")
	private int leaguePointsPerWin;
	
	@NotNull(message="Points per draw cannot be null.")
	@Column(name = "points per draw")
	private int leaguePointsPerDraw;
	
	@NotNull(message="Points per loss cannot be null.")
	@Column(name = "points per loss")
	private int leaguePointsPerLoss;

	// default const.
	public Season(){}
	
	// param. const.
	public Season(Long id, String seasonName,
			@NotNull(message = "Number of teams cannot be null.") @Min(2) int numberOfTeams,
			@NotNull(message = "Number of matches cannot be null.") @Min(1) int numberOfMatches,
			@NotNull(message = "Points per win cannot be null.") int leaguePointsPerWin,
			@NotNull(message = "Points per draw cannot be null.") int leaguePointsPerDraw,
			@NotNull(message = "Points per loss cannot be null.") int leaguePointsPerLoss) {
		super();
		this.id = id;
		this.seasonName = seasonName;
		this.numberOfTeams = numberOfTeams;
		this.numberOfMatches = numberOfMatches;
		this.leaguePointsPerWin = leaguePointsPerWin;
		this.leaguePointsPerDraw = leaguePointsPerDraw;
		this.leaguePointsPerLoss = leaguePointsPerLoss;
		this.numberOfMatchdays = (numberOfTeams-1)*numberOfMatches;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSeasonName() {
		return seasonName;
	}

	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
	}

	public int getNumberOfTeams() {
		return numberOfTeams;
	}

	public void setNumberOfTeams(int numberOfTeams) {
		this.numberOfTeams = numberOfTeams;
	}

	public int getNumberOfMatches() {
		return numberOfMatches;
	}

	public void setNumberOfMatches(int numberOfMatches) {
		this.numberOfMatches = numberOfMatches;
	}

	public int getNumberOfMatchdays() {
		return numberOfMatchdays;
	}

	public void setNumberOfMatchdays(int numberOfMatchdays) {
		this.numberOfMatchdays = numberOfMatchdays;
	}

	public int getLeaguePointsPerWin() {
		return leaguePointsPerWin;
	}

	public void setLeaguePointsPerWin(int leaguePointsPerWin) {
		this.leaguePointsPerWin = leaguePointsPerWin;
	}

	public int getLeaguePointsPerDraw() {
		return leaguePointsPerDraw;
	}

	public void setLeaguePointsPerDraw(int leaguePointsPerDraw) {
		this.leaguePointsPerDraw = leaguePointsPerDraw;
	}

	public int getLeaguePointsPerLoss() {
		return leaguePointsPerLoss;
	}

	public void setLeaguePointsPerLoss(int leaguePointsPerLoss) {
		this.leaguePointsPerLoss = leaguePointsPerLoss;
	}
	
	
	
}

