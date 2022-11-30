package com.myprojects.SBleague.service;

import java.util.List;

import com.myprojects.SBleague.model.User;
import com.myprojects.SBleague.web.dto.UserDto;

public interface UserService {
	void saveUser(UserDto userDto);
	
	User findUserByEmail(String email);
	
	List <UserDto> findAllUsers();
	
	
	
}
