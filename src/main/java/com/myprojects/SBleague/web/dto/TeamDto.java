package com.myprojects.SBleague.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.myprojects.SBleague.annotations.UniqueTeam;

public class TeamDto {
	
	private Long id;
	
	@UniqueTeam
	@NotNull
	@Size(min=3, max=50)
	private String name;
	
	@NotNull
	@Size(min=3, max=50)
	private String owner;
	
	private int matches = 0;
	private int wins = 0;
	private int draws = 0;
	private int losses = 0;
	private int points = 0;
	
	private boolean enrolled = false;

	
	// default const
	public TeamDto(){}
	
	// registration constructor
	public TeamDto(String name, String owner) {
		super();
		this.name = name;
		this.owner = owner;
	}
	
	// table constructor
	public TeamDto(String name, String owner, int matches, int wins, int draws, int losses, int points) {
		super();
		this.name = name;
		this.owner = owner;
		this.matches = matches;
		this.wins = wins;
		this.draws = draws;
		this.losses = losses;
		this.points = points;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public int getMatches() {
		return matches;
	}
	public void setMatches(int matches) {
		this.matches = matches;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getDraws() {
		return draws;
	}
	public void setDraws(int draws) {
		this.draws = draws;
	}
	public int getLosses() {
		return losses;
	}
	public void setLosses(int losses) {
		this.losses = losses;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public boolean isEnrolled() {
		return enrolled;
	}
	public void setEnrolled(boolean enrolled) {
		this.enrolled = enrolled;
	}
	
	
	
}

