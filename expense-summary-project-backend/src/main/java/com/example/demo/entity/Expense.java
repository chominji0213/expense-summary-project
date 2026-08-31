package com.example.demo.entity;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Expense {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/**
	 * 고유 ID
	 */
	private Long id;
	
	/**
	 * 지출 항목의 분류
	 */
	private String category;
	
	/**
	 * 지출금액
	 */
	private Integer amount;
	
	@Column(name = "expense_date")
	/**
	 * 지출날짜
	 */
	private LocalDate date;
	
	/**
	 * 지출에 대한 메모
	 */
	private String memo;
}
