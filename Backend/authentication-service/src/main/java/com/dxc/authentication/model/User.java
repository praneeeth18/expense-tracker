package com.dxc.authentication.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_users")
public class User{
	
	@Id
	@GeneratedValue
	private Integer userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
}
