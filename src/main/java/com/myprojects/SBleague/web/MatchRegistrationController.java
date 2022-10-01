package com.myprojects.SBleague.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myprojects.SBleague.service.MatchService;
import com.myprojects.SBleague.service.TeamService;
import com.myprojects.SBleague.web.dto.MatchRegistrationDTO;

@Controller
@RequestMapping("/matchregistration")
public class MatchRegistrationController {
	
	@Autowired
	private TeamService teamService;
	
	// injection
	private MatchService matchService;
	public MatchRegistrationController(MatchService matchService) {
		this.matchService = matchService;
	}
	
	@GetMapping
	public String showRegistrationForm() {
		return "matchregistration";
	}
	
	// input data from form will be stored in team then indirectly stored in URDto-Object
	@ModelAttribute("match")
	public MatchRegistrationDTO matchRegistrationDto() {
		return new MatchRegistrationDTO();
	}
	
	@PostMapping
	public String registerNewMatch(@Valid @ModelAttribute("match") MatchRegistrationDTO regDto) {
		matchService.saveMatch(regDto);
		teamService.updateHomeTeam(regDto);
		teamService.updateAwayTeam(regDto);
		return "redirect:/matchregistration?success";
	}
	
}
