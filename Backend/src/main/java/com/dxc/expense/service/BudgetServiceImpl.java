package com.dxc.expense.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dxc.expense.dao.BudgetDao;
import com.dxc.expense.dto.BudgetDTO;
import com.dxc.expense.dto.UserResponseDTO;
import com.dxc.expense.mapper.BudgetMapper;
import com.dxc.expense.model.Budget;
import com.dxc.expense.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService{
	
    private final BudgetDao budgetDao;
    private final UserServiceImpl userService;
    private final ExpenseServiceImpl expenseService;
    private final NotificationService notificationService;
    private final BudgetMapper budgetMapper;

	@Override
	public BudgetDTO createBudget(BudgetDTO budgetDTO) {
        User user = userService.findById(budgetDTO.userId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        Budget budget = budgetMapper.toBudget(budgetDTO, user);
        Budget savedBudget = budgetDao.save(budget);
        return budgetMapper.toBudgetResponseDTO(savedBudget);
    }


	@Override
	public List<BudgetDTO> getBudgetsByUserId(Integer userId) {
        return budgetDao.findBudgetByUserId(userId).stream()
            .map(budgetMapper::toBudgetResponseDTO)
            .toList();
    }
	
	public void checkExpenseWithBudget() {
        List<UserResponseDTO> users = userService.findAllUsers();

        for (UserResponseDTO user : users) {
            List<Budget> budgets = getBudgetsForCurrentMonth(user.id());
            Double totalExpenses = expenseService.getTotalExpensesForCurrentMonth(user.id());

            for (Budget budget : budgets) {
                if (totalExpenses > budget.getAmount()) {
                    notificationService.notifyUserBudgetExceeded(user, budget.getAmount(), totalExpenses);
                }
            }
        }
    }
	
	public List<Budget> getBudgetsForCurrentMonth(Integer userId) {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        return budgetDao.findByUserIdAndStartDateBetween(userId, startOfMonth, endOfMonth);
    }


	@Override
	public List<BudgetDTO> getAllBudget() {
	    
	    List<Budget> budgets = budgetDao.findAll();
	    
	    List<BudgetDTO> budgetDTOs = budgets.stream()
	        .map(budgetMapper::toBudgetResponseDTO)
	        .toList();

	    return budgetDTOs;
	}

	
	

}
