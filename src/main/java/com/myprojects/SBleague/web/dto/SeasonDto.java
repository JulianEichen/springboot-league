package com.myprojects.SBleague.web.dto;

import javax.validation.constraints.NotNull;

public class SeasonDto {
	@NotNull
	private String seasonName;
	@NotNull
	private int numberOfTeams;
	@NotNull
	private int numberOfMatches;
	@NotNull
	private int leaguePointsPerWin;
	@NotNull
	private int leaguePointsPerDraw; 
	@NotNull
	private int leaguePointsPerLoss;
	
	public SeasonDto(){}

	public SeasonDto(@NotNull String seasonName, @NotNull int numberOfTeams, @NotNull int numberOfMatches,
			@NotNull int leaguePointsPerWin, @NotNull int leaguePointsPerDraw, @NotNull int leaguePointsPerLoss) {
		super();
		this.seasonName = seasonName;
		this.numberOfTeams = numberOfTeams;
		this.numberOfMatches = numberOfMatches;
		this.leaguePointsPerWin = leaguePointsPerWin;
		this.leaguePointsPerDraw = leaguePointsPerDraw;
		this.leaguePointsPerLoss = leaguePointsPerLoss;
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
