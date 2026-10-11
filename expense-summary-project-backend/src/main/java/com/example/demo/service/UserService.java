package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SignupRequestDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void signup(SignupRequestDto signupRequestDto) {
		if(userRepository.existsByUsername(signupRequestDto.getUsername())) {
			throw new RuntimeException("동일한 아이디가 존재합니다.: " + signupRequestDto.getUsername());
		}
		
		User user = new User();
		user.setUsername(signupRequestDto.getUsername());
		user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
		
		userRepository.save(user);
			
	}
}
