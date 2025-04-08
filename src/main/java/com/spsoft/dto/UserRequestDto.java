package com.spsoft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
	private String userName;
	private String emailId;
	private String password;
	private String phoneNumber;
	private int roleId;
}
