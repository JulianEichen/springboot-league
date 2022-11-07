package com.myprojects.SBleague.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.myprojects.SBleague.model.Season;
import com.myprojects.SBleague.service.SeasonService;
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

	// Active Season for users
	@GetMapping("/season/seasonrules")
	public String showActiveSeason(Model model) {

		SeasonDto activeSeason = new SeasonDto("TBA", 0, 0, 0, 0, 0);

		if (!seasonService.getAllSeasons().isEmpty()) {
			activeSeason.setSeasonName(seasonService.getActiveName());
			activeSeason.setNumberOfTeams(seasonService.getActiveNumberOfTeams());
			activeSeason.setNumberOfMatches(seasonService.getActiveNumberOfMatches());
			activeSeason.setNumberOfMatchdays(seasonService.getActiveNumberOfMatchdays());
			activeSeason.setLeaguePointsPerWin(seasonService.getActivePointsPerWin());
			activeSeason.setLeaguePointsPerDraw(seasonService.getActivePointsPerDraw());
			activeSeason.setLeaguePointsPerLoss(seasonService.getActivePointsPerLoss());
		}

		model.addAttribute("activeseason", activeSeason);
		return "seasonrules";
	}

	// Season table
	@GetMapping("/admin/seasontable")
	public String listSeasons(Model model) {
		model.addAttribute("seasons", seasonService.getAllSeasons());
		return "seasontable";
	}

	// Registration form
	@GetMapping("/admin/seasonregistration")
	public String showRegistrationForm() {
		return "seasonregistration";
	}

	@PostMapping("/admin/seasonregistration")
	public String registerNewSeason(@ModelAttribute("season") SeasonDto seasonDto) {
		List<Season> seasons = seasonService.getAllSeasons();
		if (seasons.isEmpty()) {
			seasonDto.setActive(true);
		} else {
			seasonDto.setActive(false);
		}
		seasonService.saveSeason(seasonDto);
		return "redirect:/admin/seasonregistration?success";
	}

	// handler method to handle activation request
	@GetMapping("/admin/seasontable/{id}")
	public String setActive(@PathVariable Long id) {
		seasonService.setActive(id);
		return "redirect:/admin/seasontable";
	}
	
	
	// TODO: delte Season
}
