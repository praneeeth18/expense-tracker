package com.dxc.expense.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dxc.expense.model.Budget;

@Repository
public interface BudgetDao extends JpaRepository<Budget, Integer> {

}
