package com.myprojects.SBleague.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myprojects.SBleague.service.TeamService;

@Controller
@RequestMapping("/maintable")
public class MainTableController {
	
	private TeamService teamService;
	
	public MainTableController(TeamService teamService) {
		super();
		this.teamService = teamService;
	}
	
	// handler method to handle list teams request and return mode and view
	@GetMapping()
	public String listTeams(Model model) {
		model.addAttribute("teams", teamService.getAllTeamDtoOrdered());
		return "maintable"; // template for view teams is in resources/templates
	}
}
