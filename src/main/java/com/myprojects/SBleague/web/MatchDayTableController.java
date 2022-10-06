package com.myprojects.SBleague.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myprojects.SBleague.service.MatchService;


// @Controller
// @RequestMapping("matchdaytable")
public class MatchDayTableController {
	
	private MatchService matchService;
	public MatchDayTableController(MatchService matchservice) {
		super();
		this.matchService = matchservice;
	}
	
	@GetMapping()
	public String listMatches(@RequestParam(value="matchday", required=false) Integer matchday, Model model) {
		if(matchday != null && matchday > 0) {
			model.addAttribute("matches", matchService.getAllMatchesByDay(matchday.intValue()));
		}else if(matchday == null || matchday < 0) {
			model.addAttribute("matches", matchService.getAllMatches());
		}
		
		model.addAttribute("matchday",matchday);
		return "matchdaytable";
	}
	
}
