package com.myprojects.SBleague.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.myprojects.SBleague.repository.TeamRepository;

public class UniqueTeamValidator implements ConstraintValidator<UniqueTeam, String> {
	
	@Autowired
	TeamRepository teamRepository;
	
	@Override
	public boolean isValid(String teamName, ConstraintValidatorContext context) {
		teamName = teamName.replace(' ', '_');
		return !teamRepository.existsByName(teamName);
	}

}
