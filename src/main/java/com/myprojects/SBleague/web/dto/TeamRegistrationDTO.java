package com.myprojects.SBleague.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.myprojects.SBleague.annotations.UniqueTeam;

public class TeamRegistrationDTO {
	
	@UniqueTeam
	@NotNull
	@Size(min=3, max=50)
	private String name;
	
	@NotNull
	@Size(min=3, max=50)
	private String coach;
	
	@NotNull
	private Long seasonId;
	
	// default const
	public TeamRegistrationDTO(){}
	
	// param const
	public TeamRegistrationDTO(String name, String coach, Long seasonId) {
		super();
		this.name = name;
		this.coach = coach;
		this.seasonId = seasonId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCoach() {
		return coach;
	}
	public void setCoach(String coach) {
		this.coach = coach;
	}

	public Long getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(Long seasonId) {
		this.seasonId = seasonId;
	}
	
	
}
