package com.myprojects.SBleague.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="teams",uniqueConstraints = @UniqueConstraint(columnNames="name"))
public class Team {
	
	@Id
	@Column(name = "team_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name",unique=true)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="owner", nullable=false)
	private User owner;

	@Column(name="matches")
	private int matches = 0;
	
	@Column(name="wins")
	private int wins = 0;
	
	@Column(name="draws")
	private int draws = 0;
	
	@Column(name="losses")
	private int losses = 0;
	
	@Column(name="points")
	private int points = 0;
	
	@Column(name="enrollment")
	private boolean enrolled = false;

	// default const
	public Team() {}
	
	// param const
	public Team(String name, User owner, int matches, int wins, int draws, int losses, int points) {
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
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
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

	public String showTeamName() {
		return name.replace("_", " ");
	}
	
	public String showOwnerName() {
		return this.owner.getName().replace("_", " ");
	}
	
	public void reset() {
		this.matches = 0;
		this.wins = 0;
		this.draws = 0;
		this.losses = 0;
		this.points = 0;
	}
}
