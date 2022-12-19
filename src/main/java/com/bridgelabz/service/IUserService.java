package com.bridgelabz.service;


import java.util.List;

import com.bridgelabz.ResponseEntity;
import com.bridgelabz.dto.LoginDto;
import com.bridgelabz.dto.UserDto;

public interface IUserService {

	ResponseEntity add(UserDto userDto);

	List<UserDto> findAll();

	UserDto findById(Long Id);

	UserDto getUserByEmail(String email);

	UserDto editUserByEmail(String email_address, UserDto userDto);

	String loginUser(LoginDto loginDto);

	UserDto logout(String token);
	
	UserDto getUserByLogin(String token);

//	String forgotPassword(UserDto userDto);

	String resetPassword(UserDto userDto);

}