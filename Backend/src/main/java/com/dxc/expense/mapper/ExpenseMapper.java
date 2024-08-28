package com.dxc.expense.mapper;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.dxc.expense.dto.ExpenseRequestDTO;
import com.dxc.expense.dto.ExpenseResponseDTO;
import com.dxc.expense.model.Expense;
import com.dxc.expense.model.User;

@Service
public class ExpenseMapper {
	
//	public Expense toExpense(ExpenseRequestDTO request, User user) {
//        return Expense.builder()
//            .amount(request.amount())
//            .description(request.description())
//            .category(request.category())
//            .createdDate(request.createdDate())
//            .user(user)
//            .build();
//    }
//
//    public ExpenseResponseDTO toExpenseResponseDTO(Expense expense) {
//        return new ExpenseResponseDTO(
//            expense.getExpenseId(),
//            expense.getAmount(),
//            expense.getDescription(),
//            expense.getCategory(),
//            expense.getCreatedDate(),
//            expense.getUser().getId()
//        );
//    }
	
	public Expense toExpense(ExpenseRequestDTO request, User user) {
        byte[] receiptBytes = null;
        if (request.receipt() != null && !request.receipt().isEmpty()) {
            try {
                receiptBytes = request.receipt().getBytes();
            } catch (IOException e) {
                throw new RuntimeException("Error processing receipt file", e);
            }
        }
        
        return Expense.builder()
            .amount(request.amount())
            .description(request.description())
            .category(request.category())
            .createdDate(request.createdDate())
            .receipt(receiptBytes)
            .user(user)
            .build();
    }

    public ExpenseResponseDTO toExpenseResponseDTO(Expense expense) {
        return new ExpenseResponseDTO(
            expense.getExpenseId(),
            expense.getAmount(),
            expense.getDescription(),
            expense.getCategory(),
            expense.getCreatedDate(),
            expense.getUser().getId(),
            expense.getReceipt()
        );
    }

}
