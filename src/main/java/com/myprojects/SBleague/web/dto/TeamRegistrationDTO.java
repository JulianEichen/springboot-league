package com.myprojects.SBleague.web.dto;

public class TeamRegistrationDTO {
	
	private String name;
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
