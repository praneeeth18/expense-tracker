package com.dxc.expense.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record BudgetDTO(
	    
	    @NotNull(message = "Amount is required.")
	    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0.")
	    Double amount,

	    @NotNull(message = "Start date is required.")
	    LocalDate startDate,

	    @NotNull(message = "End date is required.")
	    @Future(message = "End date must be in the future.")
	    LocalDate endDate,

	    @NotNull(message = "User ID is required.")
	    Integer userId
	) {}