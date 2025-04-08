package com.spsoft.service;

import org.springframework.http.ResponseEntity;

import com.spsoft.dto.APIResponse;
import com.spsoft.dto.UpdateRequestDto;
import com.spsoft.dto.UserLoginDTO;
import com.spsoft.dto.UserRequestDto;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

	ResponseEntity<APIResponse> registerUser(UserRequestDto userRequestDto, HttpServletRequest request);

	ResponseEntity<APIResponse> login(UserLoginDTO userLoginDTO);

	ResponseEntity<APIResponse> updateUser(UpdateRequestDto updateRequestDto);

	ResponseEntity<APIResponse> getUserById(int id);

	ResponseEntity<APIResponse> deleteById(int id);

}
