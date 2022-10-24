package com.myprojects.SBleague.web;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myprojects.SBleague.service.MatchService;
import com.myprojects.SBleague.service.SeasonService;
import com.myprojects.SBleague.service.TeamService;
import com.myprojects.SBleague.usermanagement.service.UserService;
import com.myprojects.SBleague.validation.MatchDtoValidationService;
import com.myprojects.SBleague.web.dto.MatchDto;

@Controller
public class MatchController {

	// injection
	private MatchDtoValidationService matchDtoValidationService;

	private TeamService teamService;
	private UserService userService;
	private MatchService matchService;
	private SeasonService seasonService;

	public MatchController(MatchDtoValidationService matchDtoValidationService, MatchService matchService,
			SeasonService seasonService, UserService userService, TeamService teamService) {

		this.matchDtoValidationService = matchDtoValidationService;
		this.matchService = matchService;
		this.seasonService = seasonService;
		this.userService = userService;
		this.teamService = teamService;
	}

	// input data from form will be stored in team then indirectly stored in
	// MatchDto-Object
	@ModelAttribute("match")
	public MatchDto matchDto() {
		return new MatchDto();
	}

	// Show forms
	@GetMapping("/matchregistration")
	public String showRegistrationForm() {
		return "matchregistration";
	}

	@GetMapping("/matchupdate")
	public String showUpdateForm() {
		return "matchupdate";
	}

	@GetMapping("/matchdeletion")
	public String showDeleteForm() {
		return "matchdeletion";
	}

	// registration controlling
	@PostMapping("/matchregistration")
	public String registerNewMatch(@Valid @ModelAttribute("match") MatchDto matchDto, BindingResult result) {
		if (result.hasErrors()) {
			return "matchregistration";
		}
		matchService.saveMatch(matchDto);
		return "redirect:matchregistration?success";
	}

	// show matchday table with select day/all functionality
	@GetMapping("matchdaytable")
	public String listMatchdays(@RequestParam(value = "matchday", required = false) Integer matchday, Model model) {

		if (matchday != null && matchday > 0) {
			model.addAttribute("matches", matchService.getAllMatchDtoByDay(matchday.intValue()));
		} else if (matchday == null || matchday < 0) {
			model.addAttribute("matches", matchService.getAllMatchDto());
		}

		List<Integer> matchdayNumbers = IntStream.range(1, seasonService.getActiveNumberOfMatchdays() + 1)
				.mapToObj(i -> i).collect(Collectors.toList());
		model.addAttribute("matchdaynumbers", matchdayNumbers);

		model.addAttribute("matchday", matchday);
		return "matchdaytable";
	}

	// handler method for the user specific match table
	@GetMapping("usermatches")
	public String listUserMatches(@RequestParam(value = "matchday", required = false) Integer matchday, Model model,
			Principal principal) {

		// get all teams owned by the User
		Long userId = userService.findUserByEmail(principal.getName()).getId();
		List<String> userTeamNames = teamService.getAllTeamDtoByOwnerId(userId).stream().map(team -> team.getName())
				.collect(Collectors.toList());

		// get all matches of user owned teams, filtered by matchday
		if (matchday != null && matchday > 0) {
			List<MatchDto> matches = matchService.getAllMatchDtoByDay(matchday.intValue());
			matches = matches.stream().filter(
					match -> userTeamNames.contains(match.getHomeTeam()) || userTeamNames.contains(match.getAwayTeam()))
					.collect(Collectors.toList());

			// check whether matches are updated
			matches.forEach(match -> match.setUpdatedByUser(matchService.isUpdatedByUserId(match.getId(), userId)));

			model.addAttribute("matches", matches);

		} else if (matchday == null || matchday < 0) { // default and 'all matchdays'
			List<MatchDto> matches = matchService.getAllMatchDto();
			matches = matches.stream().filter(
					match -> userTeamNames.contains(match.getHomeTeam()) || userTeamNames.contains(match.getAwayTeam()))
					.collect(Collectors.toList());

			// check whether matches are updated
			matches.forEach(match -> match.setUpdatedByUser(matchService.isUpdatedByUserId(match.getId(), userId)));

			model.addAttribute("matches", matches);
		}

		// get all matchday numbers for the select
		List<Integer> matchdayNumbers = IntStream.range(1, seasonService.getActiveNumberOfMatchdays() + 1)
				.mapToObj(i -> i).collect(Collectors.toList());
		model.addAttribute("matchdaynumbers", matchdayNumbers);

		model.addAttribute("matchday", matchday);

		return "usermatches";
	}

	// show form
	@GetMapping("/usermatches/edit/{id}") // {id} is called a template variable
	public String editResultForm(@PathVariable Long id, Model model) {
		model.addAttribute("match", matchService.getMatchDtoById(id));
		return "usereditresult";
	}

	// show form
	@GetMapping("/usermatches/edited/{id}") // {id} is called a template variable
	public String fullEditResultForm(@PathVariable Long id, Model model) {
		model.addAttribute("match", matchService.getMatchDtoById(id));
		return "userfullresult";
	}

	@PostMapping("/usermatches/{id}")
	public String editResult(@PathVariable Long id, @ModelAttribute("match") MatchDto match, Principal principal) {

		Long userId = userService.findUserByEmail(principal.getName()).getId();

		matchService.updateMatchUser(match, userId);

		return "redirect:/usermatches";
	}

	// match deletion for admins
	@PostMapping("/matchdeletion")
	public String deleteMatch(@PathVariable Long id, @Valid @ModelAttribute("match") MatchDto matchDto,
			BindingResult result) {
		String err = matchDtoValidationService.validateMatchDtoRegistration(matchDto);
		if (!err.isEmpty()) {
			ObjectError error = new ObjectError("globalError", err);
			result.addError(error);
		}

		if (result.hasErrors()) {
			return "matchdeletion";
		}

		matchService.deleteMatch(matchDto);
		return "redirect:matchdeletion?success";
	}

	// match update method for admins
	@PostMapping("/matchupdate")
	public String updateMatch(@Valid @ModelAttribute("match") MatchDto matchDto, BindingResult result) {
		String err = matchDtoValidationService.validateMatchDtoRegistration(matchDto);
		if (!err.isEmpty()) {
			ObjectError error = new ObjectError("globalError", err);
			result.addError(error);
		}

		if (result.hasErrors()) {
			return "matchupdate";
		}

		return "redirect:matchupdate?success";
	}

}
