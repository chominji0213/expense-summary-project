package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	/**
	 * 로그인할 때 사용자 조회
	 * @param username
	 * @return
	 */
	Optional<User> findByUsername(String username);
	
	/**
	 * 회원가입시 아이디 중복체크
	 * @param username
	 * @return
	 */
	boolean existsByUsername(String username);
}
