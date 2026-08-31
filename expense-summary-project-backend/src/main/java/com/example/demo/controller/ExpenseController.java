package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Expense;
import com.example.demo.service.ExpenseService;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
	
	@Autowired
	private ExpenseService expenseService;
	
	/**
	 * 전체조회
	 * @return
	 */
	@GetMapping
	public List<Expense> getAllExpense() {
		return expenseService.getAllExpense();
	}
	
	/**
	 * 단건조회
	 * @param expenseId
	 * @return
	 */
	@GetMapping("/{expenseId}")
	public Expense getExpense(@PathVariable Long expenseId) {
		return expenseService.getExpenseById(expenseId);
	}
	
	/**
	 * 신규등록
	 * @param expense
	 * @return
	 */
	@PostMapping
	public Expense createExpense(@RequestBody Expense expense) {
		return expenseService.createExpense(expense);
	}
	
	/**
	 * 수정
	 * @param expenseId
	 * @param expense
	 * @return
	 */
	@PutMapping("/{expenseId}")
	public Expense updateExpense(@PathVariable Long expenseId, @RequestBody Expense expense) {
		return expenseService.updateExpense(expenseId, expense);
	}
	
	/**
	 * 삭제
	 * @param expenseId
	 */
	@DeleteMapping("/{expenseId}")
	public void deleteExpense(@PathVariable Long expenseId) {
		expenseService.deleteExpense(expenseId);
	}
}
