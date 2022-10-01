package com.myprojects.SBleague.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/")
	public String homeA() {
		return "index";
	}
	
	@GetMapping("/index")
	public String homeB() {
		return "index";
	}
}
