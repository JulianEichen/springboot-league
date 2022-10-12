package com.myprojects.SBleague.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.myprojects.SBleague.annotations.UniqueTeam;

public class TeamDto {
	
	@UniqueTeam
	@NotNull
	@Size(min=3, max=50)
	private String name;
	
	@NotNull
	@Size(min=3, max=50)
	private String owner;

	
	// default const
	public TeamDto(){}
	
	// param const
	public TeamDto(String name, String owner) {
		super();
		this.name = name;
		this.owner = owner;
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
	
}

