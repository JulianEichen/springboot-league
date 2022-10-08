package com.myprojects.SBleague.usermanagement.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myprojects.SBleague.usermanagement.model.Role;
import com.myprojects.SBleague.usermanagement.model.User;
import com.myprojects.SBleague.usermanagement.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(usernameOrEmail);
		if(user != null) {
			return new org.springframework.security.core.userdetails.User(user.getEmail()
						,user.getPassword()
						,mapRolesToAuthorities(user.getRoles()));
		}else {
			throw new UsernameNotFoundException("Invalid username or password");
		}
	}
	
	private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles){
		Collection <? extends GrantedAuthority> mapRoles = roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
		return mapRoles;
	}
	
}
