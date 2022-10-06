package com.myprojects.SBleague.web;

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
import com.myprojects.SBleague.validation.MatchDtoValidationService;
import com.myprojects.SBleague.web.dto.MatchDto;

@Controller
public class MatchController {

	// injection
	private MatchDtoValidationService matchDtoValidationService;

	private MatchService matchService;

	public MatchController(MatchService matchService,MatchDtoValidationService matchDtoValidationService) {
		this.matchService = matchService;
		this.matchDtoValidationService = matchDtoValidationService;
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

	@PostMapping("/matchregistration")
	public String registerNewMatch(@Valid @ModelAttribute("match") MatchDto matchDto, BindingResult result) {
		if (result.hasErrors()) {
			return "matchregistration";
		}
		matchService.saveMatch(matchDto);
		return "redirect:matchregistration?success";
	}
	
	@GetMapping("matchdaytable")
	public String listMatches(@RequestParam(value="matchday", required=false) Integer matchday, Model model) {
		if(matchday != null && matchday > 0) {
			model.addAttribute("matches", matchService.getAllMatchesByDay(matchday.intValue()));
		}else if(matchday == null || matchday < 0) {
			model.addAttribute("matches", matchService.getAllMatches());
		}
		
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
