package com.dxc.expense.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dxc.expense.dao.NotificationDao;
import com.dxc.expense.dto.UserResponseDTO;
import com.dxc.expense.model.Notification;
import com.dxc.expense.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
	
	private final NotificationDao notificationDao;
	private final UserServiceImpl userService;

	public void notifyUserBudgetExceeded(UserResponseDTO userDTO, double budget, double totalExpenses) {
        String message = String.format("Dear %s, your expenses (%.2f) have exceeded your budget of %.2f for this month.",
                userDTO.firstName(), totalExpenses, budget);

        User user = userService.findById(userDTO.id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUser(user); 
        notification.setCreatedAt(LocalDateTime.now()); 
        saveNotification(notification);
    }

    public void saveNotification(Notification notification) {
        notificationDao.save(notification);
    }

    public List<Notification> getAllNotificationsForUser(Long userId) {
        return notificationDao.findByUserId(userId);
    }

}
