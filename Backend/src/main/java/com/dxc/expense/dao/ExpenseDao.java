package com.dxc.expense.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.expense.model.Expense;

@Repository
public interface ExpenseDao extends JpaRepository<Expense, Integer> {
	
	List<Expense> findByUserId(Integer userId);
	
}
