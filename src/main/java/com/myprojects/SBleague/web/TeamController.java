package com.myprojects.SBleague.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.myprojects.SBleague.service.TeamService;
import com.myprojects.SBleague.service.UserService;
import com.myprojects.SBleague.web.dto.TeamDto;

@Controller
public class TeamController {

	private UserService userService;
	private TeamService teamService;

	public TeamController(UserService userService, TeamService teamService) {
		this.userService = userService;
		this.teamService = teamService;
	}

	// input data from form will be stored in team then indirectly stored in
	// URDto-Object
	@ModelAttribute("team")
	public TeamDto teamDto() {
		return new TeamDto();
	}

	// Shows all teams enrolled into the active season
	@GetMapping("/season/maintable")
	public String listTeams(Model model) {
		model.addAttribute("teams", teamService.getAllActiveTeamDtoOrdered());
		return "maintable"; // template for view teams is in resources/templates
	}
	
	// ----*----*---- User actions ----*----*----
	
	// show form for team registration for users
	@GetMapping("/user/teamregistration")
	public String showRegistrationForm(Model model, Principal principal) {
		model.addAttribute("currentuser", userService.findUserByEmail(principal.getName()));
		return "teamregistration";
	}

	// handle input for team registration
	@PostMapping("/user/teamregistration")
	public String registerNewTeam(@Valid @ModelAttribute("team") TeamDto teamDto, Principal principal,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "teamregistration";
		}
		teamService.saveTeam(teamDto, principal.getName());
		return "redirect:/user/teamregistration?success";
	}

	// show all teams of current user
	@GetMapping("/user/userteams")
	public String showUserTeams(Model model, Principal principal) {
		Long currentUserId = userService.findUserByEmail(principal.getName()).getId();
		model.addAttribute("teams", teamService.getAllTeamDtoByOwnerId(currentUserId));
		return "userteams";
	}
	
	/* ----*----*---- Admin actions ----*----*----
	 * TODO: delete team
	 * TODO: order by name
	 */
	@GetMapping("/admin/teamenrollment")
	public String showAllTeams(Model model) {
		model.addAttribute("teams", teamService.getAllTeamDtoOrdered());
		return "teamenrollment"; // template for view teams is in resources/templates
	}
	
	@GetMapping("/admin/teamenrollment/reset/{id}")
	public String resetTeamById(@PathVariable Long id){
		teamService.resetTeamById(id);
		return "redirect:/admin/teamenrollment";
	}
	
	@GetMapping("/admin/teamenrollment/enroll/{id}")
	public String enrollTeamById(@PathVariable Long id) {
		teamService.enrollmentById(id, true);
		return "redirect:/admin/teamenrollment";
	}
	
	@GetMapping("/admin/teamenrollment/unenroll/{id}")
	public String unenrollTeamById(@PathVariable Long id) {
		teamService.enrollmentById(id, false);
		return "redirect:/admin/teamenrollment";
	}
}
