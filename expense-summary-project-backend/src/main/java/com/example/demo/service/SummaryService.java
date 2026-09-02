package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.demo.dto.SummaryRequestDto;
import com.example.demo.dto.SummaryResponseDto;
import com.example.demo.entity.Expense;

@Service
public class SummaryService {
	private final RestClient restClient = RestClient.create("http://localhost:8000");
	
	public String getSummary(List<Expense> expense) {
		try {
			SummaryRequestDto summaryRequestDto = new SummaryRequestDto();
			summaryRequestDto.setExpenses(expense);
			
			SummaryResponseDto summaryResponseDto = restClient.post()
					.uri("/summary")
					.body(summaryRequestDto)	// 요청을 보낼때
					.retrieve()
					.body(SummaryResponseDto.class);	// 응답을 받을때
		
			return summaryResponseDto.getSummary();
		} catch (Exception e) {
			// TODO: handle exception
			return "서버와 연결하지 못했습니다. 다시 시도해주세요";
		}
	}
}
