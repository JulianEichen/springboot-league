package com.myprojects.SBleague.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myprojects.SBleague.service.TeamService;
import com.myprojects.SBleague.web.dto.TeamRegistrationDTO;

@Controller
@RequestMapping("/registration")
public class TeamRegistrationController {
	
	// injection
	private TeamService teamService;
	public TeamRegistrationController(TeamService teamService) {
		super();
		this.teamService = teamService;
	}
	
	@PostMapping
	public String registerNewTeam(@ModelAttribute("team") TeamRegistrationDTO regDto) {
		teamService.saveTeam(regDto);
		return "redirect:/registration?success";
	}
	
}
