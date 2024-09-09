package com.dxc.expense.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dxc.expense.dto.UserResponseDTO;

@Service
public class NotificationService {
	
	private List<String> notifications = new ArrayList<>();

    public void notifyUserBudgetExceeded(UserResponseDTO user, double budget, double totalExpenses) {
        String message = String.format("Dear %s, your expenses (%.2f) have exceeded your budget of %.2f for this month.",
                user.firstName(), totalExpenses, budget);
        saveNotification(message);
    }

    public void saveNotification(String message) {
        notifications.add(message);
    }

    public List<String> getAllNotifications() {
        return notifications;
    }

}
