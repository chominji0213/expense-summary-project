package com.example.demo.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	
	// application.properties의 값을 읽어옴 (jwt.secret은 환경변수 JWT_SECRET에서 옴)
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration-ms}")
	private long expirationMs;
	
	// 비밀키 문자열 -> 서명에 쓰는 Key 객체로 변환
	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}
	
	// 로그인 성공 시 호출: username을 담은 토큰 문자열을 만들어 반환
	public String createToken(String username) {
		Date now =new Date();
		Date expiry = new Date(now.getTime() + expirationMs);	// 지금 + 1시간
		
		return Jwts.builder()
                .subject(username)        // 누구의 토큰인지
                .issuedAt(now)            // 발급 시각
                .expiration(expiry)       // 만료 시각
                .signWith(getKey())       // 비밀키로 서명
                .compact();               // 문자열로 완성
	}
}
