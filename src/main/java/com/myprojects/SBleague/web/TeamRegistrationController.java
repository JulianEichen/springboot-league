package com.myprojects.SBleague.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myprojects.SBleague.service.TeamService;
import com.myprojects.SBleague.usermanagement.service.UserService;
import com.myprojects.SBleague.web.dto.TeamDto;

@Controller
@RequestMapping("/teamregistration")
public class TeamRegistrationController {

	// injection
	private TeamService teamService;
	private UserService userService;

	public TeamRegistrationController(TeamService teamService,UserService userService) {
		super();
		this.teamService = teamService;
		this.userService = userService;
	}

	// handler method for http-get-request
	@GetMapping
	public String showRegistrationForm(Model model, Principal principal) {
		model.addAttribute("currentuser",userService.findUserByEmail(principal.getName()));
		return "teamregistration";
	}
	
	// input data from form will be stored in team then indirectly stored in URDto-Object
	@ModelAttribute("team")
	public TeamDto teamDto() {
		return new TeamDto();
	}

	@PostMapping
	public String registerNewTeam(@Valid @ModelAttribute("team") TeamDto teamDto,
			Principal principal,
			BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "teamregistration";
		}
		teamService.saveTeam(teamDto,principal.getName());
		return "redirect:/teamregistration?success";
	}

}
