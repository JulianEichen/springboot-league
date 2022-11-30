package com.myprojects.SBleague.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myprojects.SBleague.service.MatchService;
import com.myprojects.SBleague.service.SeasonService;
import com.myprojects.SBleague.service.TeamService;
import com.myprojects.SBleague.service.UserService;
import com.myprojects.SBleague.validation.MatchDtoValidationService;
import com.myprojects.SBleague.web.dto.MatchDto;

@Controller
public class MatchController {

	// injection
	// private MatchDtoValidationService matchDtoValidationService;

	private TeamService teamService;
	private UserService userService;
	private MatchService matchService;
	private SeasonService seasonService;

	public MatchController(MatchDtoValidationService matchDtoValidationService, MatchService matchService,
			SeasonService seasonService, UserService userService, TeamService teamService) {

		// this.matchDtoValidationService = matchDtoValidationService;
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

	// show matchday table with select day/all functionality
	@GetMapping("/season/matchdaytable")
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

	// ----*----*---- User actions ----*----*----
	
	// handler method for the user specific match table
	@GetMapping("/user/usermatches")
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
	@GetMapping("/user/usermatches/edit/{id}") // {id} is called a template variable
	public String showUserEditMatchForm(@PathVariable Long id, Model model) {
		model.addAttribute("match", matchService.getMatchDtoById(id));
		return "usereditmatch";
	}

	@PostMapping("/user/usermatches/edit/{id}")
	public String userEditMatch(@PathVariable Long id, @ModelAttribute("match") MatchDto match, Principal principal) {

		Long userId = userService.findUserByEmail(principal.getName()).getId();

		matchService.updateMatchUser(match, userId);

		return "redirect:/user/usermatches?success";
	}

	// show former input
	@GetMapping("/user/usermatches/input/{id}") // {id} is called a template variable
	public String showMatchInput(@PathVariable Long id, Model model, Principal principal) {
		Long userId = userService.findUserByEmail(principal.getName()).getId();
		MatchDto matchDto = matchService.getDtoWithUserInput(id, userId);

		model.addAttribute("match", matchDto);
		return "userinput";
	}

	// ----*----*---- Admin actions ----*----*----

	// match registration form
	@GetMapping("/admin/matchregistration")
	public String showRegistrationForm() {
		return "matchregistration";
	}

	// registration controlling
	@PostMapping("/admin/matchregistration")
	public String registerNewMatch(@Valid @ModelAttribute("match") MatchDto matchDto, BindingResult result) {
		if (result.hasErrors()) {
			return "redirect:/admin/matchregistration";
		}
		matchService.saveMatch(matchDto);
		return "redirect:/admin/matchregistration?success";
	}

	// show all matches
	@GetMapping("/admin/adminmatches")
	public String listMatchdaysAdmin(@RequestParam(value = "matchday", required = false) Integer matchday,
			Model model) {

		List<MatchDto> matches = new ArrayList<>();

		if (matchday != null && matchday > 0) { // default
			matches = matchService.getAllMatchDtoByDay(matchday.intValue());
		} else if (matchday == null || matchday < 0) {
			matches = matchService.getAllMatchDto();
		}

		matches.forEach(match -> {
			match.setResultHasInputConflict(matchService.getMatchById(match.getId()).getResult().hasInputConflict());
			match.setResultIsValid(matchService.getMatchById(match.getId()).getResult().isValid());
		});

		// numbers for the select
		List<Integer> matchdayNumbers = IntStream.range(1, seasonService.getActiveNumberOfMatchdays() + 1)
				.mapToObj(i -> i).collect(Collectors.toList());
		model.addAttribute("matchdaynumbers", matchdayNumbers);

		model.addAttribute("matches", matches);
		model.addAttribute("matchday", matchday);
		return "adminmatches";
	}

	// show user input of a specific match
	@GetMapping("/admin/adminmatches/input/{id}") // {id} is called a template variable
	public String showUserInputAdmin(@PathVariable Long id, Model model) {
		MatchDto matchDto = matchService.getMatchDtoById(id);
		String homeOwner = teamService.getOwnerNameByTeamName(matchDto.getHomeTeam().replace(" ", "_"));
		String awayOwner = teamService.getOwnerNameByTeamName(matchDto.getAwayTeam().replace(" ", "_"));
		int homePointsH = matchService.getMatchById(id).getResult().getHomePointsH();
		int awayPointsH = matchService.getMatchById(id).getResult().getAwayPointsH();
		int homePointsA = matchService.getMatchById(id).getResult().getHomePointsA();
		int awayPointsA = matchService.getMatchById(id).getResult().getAwayPointsA();

		model.addAttribute("match", matchDto);
		model.addAttribute("homeOwner", homeOwner);
		model.addAttribute("awayOwner", awayOwner);
		model.addAttribute("homePointsH", homePointsH);
		model.addAttribute("awayPointsH", awayPointsH);
		model.addAttribute("homePointsA", homePointsA);
		model.addAttribute("awayPointsA", awayPointsA);

		return "adminmatchdetails";
	}

	// handler method to handle activation request
	@GetMapping("/admin/adminmatches/details/reset/{id}")
	public String setActive(@PathVariable Long id) {
		matchService.resetResult(id);
		return "redirect:/admin/adminmatches?reset";
	}

	// match deletion for admins
	@GetMapping("/admin/adminmatches/details/delete/{id}")
	public String deleteMatch(@PathVariable Long id) {

		matchService.resetResult(id);
		matchService.deleteById(id);

		return "redirect:/admin/adminmatches?delete";
	}
	
	// show the edit form
	@GetMapping("/admin/adminmatches/details/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model) {
		MatchDto matchDto = matchService.getMatchDtoById(id);

		model.addAttribute("match", matchDto);

		return "admineditmatch";
	}

	// handle edit input
	@PostMapping("/admin/adminmatches/details/edit/{id}")
	public String updateMatch(@PathVariable Long id, @ModelAttribute("match") MatchDto matchDto) {

		matchService.updateMatchAdmin(matchDto);

		return "redirect:/admin/adminmatches?edit";
	}

}
