package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.LoginResponseDto;
import com.example.demo.dto.SignupRequestDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtTokenProvider;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	/**
	 * 회원가입 저장
	 * @param signupRequestDto
	 */
	public void signup(SignupRequestDto signupRequestDto) {
		if(userRepository.existsByUsername(signupRequestDto.getUsername())) {
			throw new RuntimeException("동일한 아이디가 존재합니다.: " + signupRequestDto.getUsername());
		}
		
		User user = new User();
		user.setUsername(signupRequestDto.getUsername());
		user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
		
		userRepository.save(user);
			
	}
	
	public LoginResponseDto login(LoginRequestDto loginRequestDto) {
		// findByUsername(아이디) 로 사용자 조회		
		User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(() -> new RuntimeException("아이디 또는 비밀번호가 올바르지 않습니다."));
		
		//비밀번호 비교
		if(!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
			throw new RuntimeException("아이디 또는 비밀번호가 올바르지 않습니다.");		
		}
		
		String token = jwtTokenProvider.createToken(loginRequestDto.getUsername());
		
		return new LoginResponseDto(token);
	}
}
