package com.dxc.expense.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BudgetCheckScheduler {

	private final BudgetServiceImpl budgetService;

    public BudgetCheckScheduler(BudgetServiceImpl budgetService) {
        this.budgetService = budgetService;
    }

    @Scheduled(cron = "*/30 * * * * ?")
    public void checkUserBudgets() {
        budgetService.checkExpenseWithBudget();
    }


}
