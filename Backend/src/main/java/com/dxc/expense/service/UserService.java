package com.dxc.expense.service;

import java.util.List;
import java.util.Optional;

import com.dxc.expense.dto.UserDTO;
import com.dxc.expense.dto.UserResponseDTO;
import com.dxc.expense.model.User;

public interface UserService {

	public Integer createUser(UserDTO request);
	public List<UserResponseDTO> findAllUsers();
	public Optional<User> findById(Integer userId);
}
