package com.myfund.models.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class BudgetDTO {

    private Long id;

    private String name;

    private BigDecimal balance;

    private BigDecimal totalIncome;

    private BigDecimal totalExpense;

    private List<ExpenseDTO> expenses;

    private List<IncomeDTO> incomes;
}
