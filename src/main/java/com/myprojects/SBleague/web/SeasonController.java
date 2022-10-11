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
	@GetMapping("/seasonrules")
	public String showActiveSeason(Model model) {
		SeasonDto activeSeason = new SeasonDto();
		activeSeason.setSeasonName(seasonService.getActiveName());
		activeSeason.setNumberOfTeams(seasonService.getActiveNumberOfTeams());
		activeSeason.setNumberOfMatches(seasonService.getActiveNumberOfMatches());
		activeSeason.setNumberOfMatchdays(seasonService.getActiveNumberOfMatchdays());
		activeSeason.setLeaguePointsPerWin(seasonService.getActivePointsPerWin());
		activeSeason.setLeaguePointsPerDraw(seasonService.getActivePointsPerDraw());
		activeSeason.setLeaguePointsPerLoss(seasonService.getActivePointsPerLoss());
		
		model.addAttribute("activeseason", activeSeason);
		return "seasonrules";
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
		List<Season> seasons = seasonService.getAllSeasons();
		if (seasons.isEmpty()) {
			seasonDto.setActive(true);
			System.out.println("Auto activate!");
		}else {
			seasonDto.setActive(false);
			System.out.println("Auto NOT activate!");
		}
		seasonService.saveSeason(seasonDto);
		return "redirect:seasonregistration?success";
	}

	// handler method to handle delete request
	@GetMapping("/seasontable/{id}")
	public String setActive(@PathVariable Long id) {
		seasonService.setActive(id);
		return "redirect:/seasontable";
	}

}
