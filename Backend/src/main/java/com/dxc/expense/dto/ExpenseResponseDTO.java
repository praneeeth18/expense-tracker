package com.dxc.expense.dto;

import java.time.LocalDateTime;

public record ExpenseResponseDTO(
	    Integer expenseId,
	    Double amount,
	    String description,
	    String category,
	    LocalDateTime createdDate,
	    Integer userId,
	    byte[] receipt
	) {}
