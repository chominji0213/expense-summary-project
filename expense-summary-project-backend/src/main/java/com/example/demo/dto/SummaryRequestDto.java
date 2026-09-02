package com.example.demo.dto;

import java.util.List;
import com.example.demo.entity.Expense;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SummaryRequestDto {
    private List<Expense> expenses;
}