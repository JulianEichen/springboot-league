package com.myprojects.SBleague.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myprojects.SBleague.service.MatchService;

@Controller
@RequestMapping("matchdaytable")
public class MatchDayTableController {
	
	private MatchService matchservice;
	public MatchDayTableController(MatchService matchservice) {
		super();
		this.matchservice = matchservice;
	}
	
	@GetMapping()
	public String listMatches(Model model) {
		return null;
	}
}
