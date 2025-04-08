package com.spsoft.service_impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spsoft.constants.Constants;
import com.spsoft.dto.APIResponse;
import com.spsoft.dto.UpdateRequestDto;
import com.spsoft.dto.UserLoginDTO;
import com.spsoft.dto.UserRequestDto;
import com.spsoft.dto.UserResponse1DTO;
import com.spsoft.dto.UserResponseDto;
import com.spsoft.exceptions.UnauthorizedException;
import com.spsoft.exceptions.CustomException;
import com.spsoft.exceptions.ForbiddenException;
import com.spsoft.exceptions.UnauthorizedException;
import com.spsoft.model.Roles;
import com.spsoft.model.User;
import com.spsoft.repository.RolesRepository;
import com.spsoft.repository.UserRepository;
import com.spsoft.security.JwtService;
import com.spsoft.service.UserService;
import com.spsoft.validation_service_impl.ValidationService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final RolesRepository rolesRepository;
	private final ValidationService validationService;

	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
			AuthenticationManager authenticationManager, JwtService jwtService, RolesRepository rolesRepository,
			ValidationService validationService) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.rolesRepository = rolesRepository;
		this.validationService = validationService;
	}

	APIResponse response = new APIResponse();

	@Override
	public ResponseEntity<APIResponse> registerUser(UserRequestDto userRequestDto, HttpServletRequest request) {
		try {
			String authHeader = request.getHeader("Authorization");
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				throw new UnauthorizedException(Constants.INVALID_CREDENTIALS);
			}

			String token = authHeader.substring(7);
			String loggedInEmail = JwtService.extractUserName(token);
			User loggedInUser = userRepository.findByEmailId(loggedInEmail.trim().toLowerCase())
					.orElseThrow(() -> new UnauthorizedException(Constants.UNAUTHORIZED_ACCESS));

			if (!Constants.ADMIN.equalsIgnoreCase(loggedInUser.getRoleId().getRoleName())) {
				throw new ForbiddenException(Constants.ONLY_ADMIN_CAN_REGISTER);
			}

			String email = userRequestDto.getEmailId().trim().toLowerCase();
			String phone = userRequestDto.getPhoneNumber().trim();
			String username = userRequestDto.getUserName().trim();

			if (userRepository.existsByEmailId(email)) {
				throw new CustomException(Constants.EMAIL_ALREADY_IN_USE);
			}
			if (userRepository.existsByPhoneNumber(phone)) {
				throw new CustomException(Constants.PHONE_NUMBER_ALREADY_IN_USE);
			}
			if (!validationService.isValidPassword(userRequestDto.getPassword())) {
				throw new CustomException(Constants.PASSWORD_VALIDATION_FAILED);
			}
			if (!validationService.isValidPhoneNumber(phone)) {
				throw new CustomException(Constants.PHONE_NUMBER_VALIDATION_FAILED);
			}

			Roles role = rolesRepository.findByRoleId(userRequestDto.getRoleId())
					.orElseThrow(() -> new CustomException(Constants.ROLE_NOTFOUND));

			if (Constants.ADMIN.equalsIgnoreCase(role.getRoleName())) {
				throw new CustomException(Constants.ONLY_ONE_ADMIN);
			}

			User newUser = new User();
			newUser.setUserName(username);
			newUser.setEmailId(email);
			newUser.setPassword(bCryptPasswordEncoder.encode(userRequestDto.getPassword()));
			newUser.setPhoneNumber(phone);
			newUser.setRoleId(role);
			newUser.setStatus(true);
			userRepository.save(newUser);

			APIResponse successResponse = new APIResponse(HttpStatus.CREATED.value(), false,
					Constants.USER_SAVED_SUCCESS);
			return new ResponseEntity<>(successResponse, HttpStatus.CREATED);

		} catch (CustomException ce) {
			throw ce;
		} catch (UnauthorizedException uae) {
			throw uae;
		} catch (ForbiddenException fe) {
			throw fe;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ResponseEntity<APIResponse> updateUser(UpdateRequestDto updateRequestDto) {
		try {

			Optional<User> optionalUser = userRepository.findById(updateRequestDto.getId());

			if (optionalUser.isEmpty()) {
				throw new CustomException(Constants.USER_NOT_FOUND);
			}

			User user = optionalUser.get();
			if (updateRequestDto.getUserName() != null) {
				user.setUserName(updateRequestDto.getUserName());
			}
			if (updateRequestDto.getEmailId() != null) {
				user.setEmailId(updateRequestDto.getEmailId());
			}

			userRepository.save(user);

			response.setIsError(false);
			response.setStatusCode(HttpStatus.CREATED.value());
			response.setResult(Constants.USER_UPDATED_SUCCESS);
			return new ResponseEntity<>(response, HttpStatus.CREATED);

		} catch (CustomException ce) {
			throw ce;
		} catch (UnauthorizedException uae) {
			throw uae;
		} catch (ForbiddenException fe) {
			throw fe;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ResponseEntity<APIResponse> getUserById(int id) {
		try {
			Optional<User> optionalUser = userRepository.findById(id);

			if (optionalUser.isEmpty()) {
				throw new CustomException(Constants.USER_NOT_FOUND);
			}

			UserResponseDto userResponseDto = new UserResponseDto();
			BeanUtils.copyProperties(optionalUser.get(), userResponseDto);

			response.setIsError(false);
			response.setStatusCode(HttpStatus.OK.value());
			response.setResult(userResponseDto);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (CustomException ce) {
			throw ce;
		} catch (UnauthorizedException uae) {
			throw uae;
		} catch (ForbiddenException fe) {
			throw fe;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ResponseEntity<APIResponse> deleteById(int id) {
		try {
			Optional<User> user = userRepository.findById(id);
			if (user.isEmpty()) {
				throw new CustomException(Constants.USER_NOT_FOUND);
			}

			userRepository.deleteById(id);
			response.setIsError(false);
			response.setStatusCode(HttpStatus.OK.value());
			response.setResult(Constants.USER_DELETED_SUCCESS);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (CustomException ce) {
			throw ce;
		} catch (UnauthorizedException uae) {
			throw uae;
		} catch (ForbiddenException fe) {
			throw fe;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ResponseEntity<APIResponse> login(UserLoginDTO userLoginDTO) {
		try {
			String email = userLoginDTO.getEmailId().toLowerCase();

			User user = userRepository.findByEmailId(email)
					.orElseThrow(() -> new CustomException(Constants.USER_NOT_REGISTERED));

			if (!bCryptPasswordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
				throw new CustomException(Constants.INVALID_CREDENTIALS);
			}

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getEmailId(), userLoginDTO.getPassword()));

			if (!authentication.isAuthenticated()) {
				throw new UnauthorizedException(Constants.INVALID_CREDENTIALS);
			}

			Map<String, Object> claims = new HashMap<>();
			claims.put("userId", user.getUserId());
			claims.put("emailId", user.getEmailId());
			claims.put("userName", user.getUserName());
			claims.put("phoneNumber", user.getPhoneNumber());
			claims.put("roleName", user.getRoleId().getRoleName());

			String token = jwtService.generateTokenWithClaims(claims, user.getEmailId());

			UserResponse1DTO userResponseDTO = new UserResponse1DTO();
			userResponseDTO.setUserId(user.getUserId());
			userResponseDTO.setUserName(user.getUserName());
			userResponseDTO.setEmailId(user.getEmailId());
			userResponseDTO.setPhoneNumber(user.getPhoneNumber());
			userResponseDTO.setRoleName(user.getRoleId().getRoleName());
			userResponseDTO.setToken(token);

			APIResponse successResponse = new APIResponse(HttpStatus.OK.value(), false, userResponseDTO);
			return new ResponseEntity<>(successResponse, HttpStatus.OK);
		} catch (CustomException ce) {
			throw ce;
		} catch (UnauthorizedException uae) {
			throw uae;
		} catch (ForbiddenException fe) {
			throw fe;
		} catch (Exception e) {
			throw e;
		}

	}
}
