package com.spsoft.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spsoft.dto.APIResponse;
import com.spsoft.dto.UserLoginDTO;
import com.spsoft.dto.UserRequestDto;
import com.spsoft.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {
	private UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<APIResponse> registerUser(@RequestBody UserRequestDto userRequestDto,
			HttpServletRequest request) {
		System.out.println("Received UserRequestDto: " + userRequestDto);

		return userService.registerUser(userRequestDto, request);

	}

	@PostMapping("/login")
	public ResponseEntity<APIResponse> login(@RequestBody UserLoginDTO userLoginDTO) {
		return userService.login(userLoginDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<APIResponse> getUserById(@PathVariable int id) {
		return userService.getUserById(id);
	}

}
