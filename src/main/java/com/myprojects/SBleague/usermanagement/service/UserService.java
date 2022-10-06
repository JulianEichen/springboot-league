package com.myprojects.SBleague.usermanagement.service;

import java.util.List;

import com.myprojects.SBleague.usermanagement.model.User;
import com.myprojects.SBleague.usermanagement.web.dto.UserDto;

public interface UserService {
	void saveUser(UserDto userDto);
	
	User findUserByEmail(String email);
	
	List <UserDto> findAllUsers();
}
