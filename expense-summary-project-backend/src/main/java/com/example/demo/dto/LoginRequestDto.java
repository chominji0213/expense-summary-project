package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;


/**
 * 로그인 요청 JSON을 받는 용도
 */
@Getter
@Setter
public class LoginRequestDto {
	private String username;
	private String password;
}
