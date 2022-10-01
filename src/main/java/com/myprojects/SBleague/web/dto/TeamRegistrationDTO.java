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
	
	// default const
	public TeamRegistrationDTO(){}
	
	// param const
	public TeamRegistrationDTO(String name, String coach) {
		super();
		this.name = name;
		this.coach = coach;
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
}
