package com.bridgelabz.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.ResponseEntity;
import com.bridgelabz.dto.LoginDto;
import com.bridgelabz.dto.UserDto;
import com.bridgelabz.exception.BookException;

import com.bridgelabz.model.UserModel;
import com.bridgelabz.repository.IUserRepo;
import com.bridgelabz.utility.JavaEmailService;
import com.bridgelabz.utility.JwtTokenUtil;

@Service
public class UserService implements IUserService {

	@Autowired
	IUserRepo userRepo;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Autowired
	JavaEmailService javaEmailService;

	@Override
	public ResponseEntity add(UserDto userDto) {
		
			if (userRepo.findByEmail(userDto.getEmail()).isPresent()) {
			throw new BookException("User already exist");
		} else {
			String token = jwtTokenUtil.generateToken(userDto.getEmail(), userDto.getPassword());
			javaEmailService.sendSimpleMail(userDto.getEmail(), token, "Verification");
			UserModel addUser = modelMapper.map(userDto, UserModel.class);
			userRepo.save(addUser);
			return new ResponseEntity(userDto, "One user added");
			}
		}

	@Override
	public List<UserDto> findAll() {
		return userRepo.findAll().stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
	}

	@Override
	public UserDto findById(Long Id) {
		Optional<UserModel> user = userRepo.findById(Id);
		if (user.isEmpty()) {
			throw new BookException(" User Id does not exist");
		}
		UserDto userDto = modelMapper.map(user.get(), UserDto.class);
		return userDto;
	}

	@Override
	public UserDto getUserByEmail(String email) {
		Optional<UserModel> user = userRepo.findByEmail(email);
		if (user.isEmpty()) {
			throw new BookException(" User Email does not exist");
		}
		UserDto userDto = modelMapper.map(user.get(), UserDto.class);
		return userDto;
	}

	@Override
	public UserDto editUserByEmail(String email_address, UserDto userDto) {
		UserModel editUser = userRepo.findByEmail(email_address).orElse(null);
		if (editUser != null) {
			UserModel user = modelMapper.map(userDto, UserModel.class);
			user.setEmail(email_address);
			userRepo.save(editUser);
			return userDto;
		} else {
			throw new BookException("Email:" + email_address + " is not present");
		}
	}

	@Override
	public String loginUser(LoginDto loginDto) {
		Optional<UserModel> user = userRepo.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
		if (user.isEmpty()) {
			Optional<UserModel> userEmail = userRepo.findByEmail(loginDto.getEmail());
			Optional<UserModel> userPassword = userRepo.findByPassword(loginDto.getPassword());
			if (userEmail.isEmpty()) {
				throw new BookException("Email is incorrect,Give correct Email");
			} else if (userPassword.isEmpty()) {
				throw new BookException("Password is incorrect,Give correct password");
			}
		}
		String token = jwtTokenUtil.generateToken(loginDto);
//		user.get().setIsLogin(true);
		userRepo.save(user.get());
//		System.out.println("Check the user is login or not " + user.get().getIsLogin());
		return token;
	}


	@Override
	public UserDto logout(String token) {
		UserDto userDto = jwtTokenUtil.decode(token);
		Optional<UserModel> checkUserDetails = userRepo.findByEmailAndPassword(userDto.getEmail(),
				userDto.getPassword());
		UserDto logout = modelMapper.map(checkUserDetails, UserDto.class);
//		checkUserDetails.get().setIsLogin(false);
		userRepo.save(checkUserDetails.get());
		return logout;
	}

//	@Override
//	public String forgotPassword(UserDto userDto) {
//		Optional<UserModel> user = userRepo.findByEmail(userDto.getEmail());
//		if(user != null) {
//			javaEmailService.sendSimpleMail(user.getEmail, "Login", "http");
//		}
//		return null;
//	}



	@Override
	public String resetPassword(UserDto userDto) {
		Optional<UserModel> user = userRepo.findByEmail(userDto.getEmail());
		String password = userDto.getPassword();
		if(user.isPresent()) {
			user.get().setPassword(password);
			userRepo.save(user.get());
			return "Password Changed";
		}else {
		return "Password is not Valid";
		}
	}
}
