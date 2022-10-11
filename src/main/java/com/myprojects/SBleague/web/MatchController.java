package com.myprojects.SBleague.web;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myprojects.SBleague.service.MatchService;
import com.myprojects.SBleague.service.SeasonService;
import com.myprojects.SBleague.validation.MatchDtoValidationService;
import com.myprojects.SBleague.web.dto.MatchDto;

@Controller
public class MatchController {

	// injection
	private MatchDtoValidationService matchDtoValidationService;

	private MatchService matchService;
	private SeasonService seasonService;

	public MatchController(MatchDtoValidationService matchDtoValidationService,
			MatchService matchService,
			SeasonService seasonService) {
		
		this.matchDtoValidationService = matchDtoValidationService;
		this.matchService = matchService;
		this.seasonService = seasonService;
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
	
	// show matchdaytable with select functionality
	@GetMapping("matchdaytable")
	public String listMatches(@RequestParam(value="matchday", required=false) Integer matchday, Model model) {
		if(matchday != null && matchday > 0) {
			model.addAttribute("matches", matchService.getAllMatchesByDay(matchday.intValue()));
		}else if(matchday == null || matchday < 0) {
			model.addAttribute("matches", matchService.getAllMatches());
		}
		
		List<Integer> matchdayNumbers = IntStream.range(1, seasonService.getActiveNumberOfMatchdays()+1)
				.mapToObj(i->i).collect(Collectors.toList());
		model.addAttribute("matchdaynumbers", matchdayNumbers);
		
		model.addAttribute("matchday",matchday);
		return "matchdaytable";
	}

	@PostMapping("/matchdeletion")
	public String deleteMatch(@Valid @ModelAttribute("match") MatchDto matchDto, BindingResult result) {
		String err = matchDtoValidationService.validateMatchDto(matchDto);
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

	@PostMapping("/matchupdate")
	public String updateMatch(@Valid @ModelAttribute("match") MatchDto matchDto, BindingResult result) {
		String err = matchDtoValidationService.validateMatchDto(matchDto);
		if (!err.isEmpty()) {
			ObjectError error = new ObjectError("globalError", err);
			result.addError(error);
		}

		if (result.hasErrors()) {
			return "matchupdate";
		}

		matchService.updateMatch(matchDto);
		return "redirect:matchupdate?success";
	}
}
