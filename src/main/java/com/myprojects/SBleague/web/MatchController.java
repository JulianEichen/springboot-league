package com.myprojects.SBleague.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.myprojects.SBleague.service.MatchService;
import com.myprojects.SBleague.web.dto.MatchDto;

@Controller
public class MatchController {
	
	// injection
	private MatchService matchService;

	public MatchController(MatchService matchService) {
		this.matchService = matchService;
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

	// input data from form will be stored in team then indirectly stored in MatchDto-Object
	@ModelAttribute("match")
	public MatchDto matchDto() {
		return new MatchDto();
	}
	
	@PostMapping("/matchregistration")
	public String registerNewMatch(@Valid @ModelAttribute("match") MatchDto matchDto) {
		matchService.saveMatch(matchDto);
		return "redirect:matchregistration?success";
	}
	
	@PostMapping("/matchdeletion")
	public String deleteMatch(@Valid @ModelAttribute("match") MatchDto matchDto) {
		matchService.deleteMatch(matchDto);
		return "redirect:matchdeletion?success";
	}
	
	@PostMapping("/matchupdate")
	public String updateMatch(@Valid @ModelAttribute("match") MatchDto matchDto) {
		matchService.updateMatch(matchDto);
		return "redirect:matchupdate?success";
	}
}
