package com.dxc.expense.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dxc.expense.model.Notification;

public interface NotificationDao extends JpaRepository<Notification, Long> {

	List<Notification> findByUserId(Long userId);
}
