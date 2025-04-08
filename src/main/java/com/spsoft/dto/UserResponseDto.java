package com.spsoft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

	private int userId;
	private String userName;
	private String emailId;
	private String phoneNumber;

	
}
