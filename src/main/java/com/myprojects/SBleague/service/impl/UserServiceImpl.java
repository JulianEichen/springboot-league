package com.myprojects.SBleague.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.myprojects.SBleague.model.Role;
import com.myprojects.SBleague.model.User;
import com.myprojects.SBleague.repository.RoleRepository;
import com.myprojects.SBleague.repository.UserRepository;
import com.myprojects.SBleague.service.UserService;
import com.myprojects.SBleague.web.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void saveUser(UserDto userDto) {
		User user = new User();
		user.setName(userDto.getFirstName() + " " + userDto.getLastName());
		user.setEmail(userDto.getEmail());
		// encrypt PW using spring security
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));

		/*
		 * Role role = roleRepository.findByName("ROLE_ADMIN"); if( role== null) { role
		 * = createAdminRole(); }else
		 */
		Role role = roleRepository.findByName("ROLE_USER");

		if (role == null && !roleRepository.existsByName("ROLE_ADMIN")) {
			role = createRole("ROLE_ADMIN");
		} else if (role == null) {
			role = createRole("ROLE_USER");
		}

		user.setRoles(Arrays.asList(role));
		userRepository.save(user);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<UserDto> findAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
	}

	private UserDto mapToUserDto(User user) {
		UserDto userDto = new UserDto();
		String[] fullName = user.getName().split(" ");
		userDto.setFirstName(fullName[0]);
		userDto.setLastName(fullName[1]);
		userDto.setEmail(user.getEmail());
		return userDto;
	}

	private Role createRole(String roleName) {
		Role role = new Role();
		role.setName(roleName);
		return roleRepository.save(role);
	}
}
