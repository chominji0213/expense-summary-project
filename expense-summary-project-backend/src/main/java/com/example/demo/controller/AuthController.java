package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.dto.SignupRequestDto;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 회원가입
	 * @param signupRequestDto
	 * @return
	 */
	@PostMapping("/signup")
	public String signup(@RequestBody SignupRequestDto signupRequestDto) {
		userService.signup(signupRequestDto);
		
		return "회원가입 완료";
	}
	
	/**
	 * 로그인
	 * @param loginRequestDto
	 * @return
	 */
	@PostMapping("/login")
	public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
		return userService.login(loginRequestDto);
	}
}
