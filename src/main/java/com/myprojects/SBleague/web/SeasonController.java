package com.myprojects.SBleague.web;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.myprojects.SBleague.service.SeasonService;
import com.myprojects.SBleague.web.dto.MatchDto;
import com.myprojects.SBleague.web.dto.SeasonDto;

@Controller
public class SeasonController {

	private SeasonService seasonService;

	public SeasonController(SeasonService seasonService) {
		super();
		this.seasonService = seasonService;
	}

	// input data from form will be stored in team then indirectly stored in
	// MatchDto-Object
	@ModelAttribute("season")
	public SeasonDto seasonDto() {
		return new SeasonDto();
	}

	// Season table
	@GetMapping("/seasontable")
	public String listSeasons(Model model) {
		model.addAttribute("seasons", seasonService.getAllSeasons());
		return "seasontable";
	}
	
	// Registration form
	@GetMapping("/seasonregistration")
	public String showRegistrationForm() {
		return "seasonregistration";
	}

	@PostMapping("/seasonregistration")
	public String registerNewSeason(@ModelAttribute("season") SeasonDto seasonDto) {
		seasonService.saveSeason(seasonDto);
		return "redirect:seasonregistration?success";
	}

}
