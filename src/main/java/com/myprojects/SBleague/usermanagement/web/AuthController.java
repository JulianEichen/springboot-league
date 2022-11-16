package com.myprojects.SBleague.usermanagement.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.myprojects.SBleague.service.TeamService;
import com.myprojects.SBleague.usermanagement.model.User;
import com.myprojects.SBleague.usermanagement.service.UserService;
import com.myprojects.SBleague.usermanagement.web.dto.UserDto;

@Controller
public class AuthController {

	private UserService userService;

	public AuthController(UserService userService, TeamService teamService) {
		this.userService = userService;
	}
	
	// handler for login
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	// handler for user table
	@GetMapping("/usertable")
	public String userTable(Model model) {
		List<UserDto> users = userService.findAllUsers();
		model.addAttribute("users", users);
		return "/usertable";
	}

	// handler for user registration requests
	@GetMapping("/userregistration")
	public String showRegistrationForm(Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("user", userDto);
		return "userregistration";
	}

	// handler for user registration form submit
	@PostMapping("/userregistration/save")
	public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {

		User existingUser = userService.findUserByEmail(userDto.getEmail());

		if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
			result.rejectValue("email", null, "There is already an account registered with the same email");
		}

		if (result.hasErrors()) {
			model.addAttribute("user", userDto);
			return "/userregistration";
		}

		userService.saveUser(userDto);
		return "redirect:/userregistration?success";
	}
	
}
