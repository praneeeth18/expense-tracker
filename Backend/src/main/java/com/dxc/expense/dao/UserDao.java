package com.dxc.expense.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dxc.expense.model.User;

public interface UserDao extends JpaRepository<User, Integer>{

}
