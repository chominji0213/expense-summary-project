package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Expense;
import com.example.demo.repository.ExpenseRepository;

@Service
public class ExpenseService {
	@Autowired
	private ExpenseRepository expenseRepository;
	
	//전체 조회
	public List<Expense> getAllExpense() {
		return expenseRepository.findAll();
	}
	
	// 단건 조회
	public Expense getExpenseById(Long expenseId) {
		Optional<Expense> expense = expenseRepository.findById(expenseId);
		
		if(expense.isEmpty()) {
			throw new RuntimeException("해당 id의 지출 내역이 없습니다: " + expenseId);
		}
		
		return expense.get();
	}
	
	// 저장
	public Expense createExpense(Expense expense) {
		return expenseRepository.save(expense);
	}
	
	// 수정
	public Expense updateExpense(Long expenseId, Expense expense) {
		Expense expenseList = getExpenseById(expenseId);
		
		expenseList.setCategory(expense.getCategory());
		expenseList.setAmount(expense.getAmount());
		expenseList.setDate(expense.getDate());
		expenseList.setMemo(expense.getMemo());
		
		return expenseRepository.save(expenseList);
	}
	
	// 삭제
	public void deleteExpense(Long expenseId) {
		expenseRepository.deleteById(expenseId);
	}
}
