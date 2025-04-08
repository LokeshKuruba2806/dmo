package com.spsoft.service_impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.spsoft.dto.APIResponse;
import com.spsoft.dto.ResponseMessage;
import com.spsoft.dto.RolesDTO;
import com.spsoft.model.Roles;
import com.spsoft.repository.RolesRepository;
import com.spsoft.service.RolesService;

@Service
public class RolesServiceImpl implements RolesService {

	private final RolesRepository rolesRepository;

	public RolesServiceImpl(RolesRepository rolesRepository) {
		this.rolesRepository = rolesRepository;
	}

	APIResponse response = new APIResponse();

	@Override
	public ResponseEntity<APIResponse> createRole(RolesDTO rolesDTO) {
		try {

			if (rolesRepository.existsByRoleName(rolesDTO.getRoleName())) {
				response.setIsError(true);
				response.setStatusCode(HttpStatus.BAD_REQUEST.value());
				response.setResult(new ResponseMessage("Role name already exists"));
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}

			Roles role = new Roles();
			role.setRoleName(rolesDTO.getRoleName());

			rolesRepository.save(role);

			response.setIsError(false);
			response.setStatusCode(HttpStatus.CREATED.value());
			response.setResult("Role created successfully");
			return new ResponseEntity<>(response, HttpStatus.CREATED);

		} catch (Exception e) {
			response.setIsError(true);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setResult(new ResponseMessage(e.getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<APIResponse> getRoleById(int roleId) {
		Optional<Roles> role = rolesRepository.findByRoleId(roleId);

		if (role.isEmpty()) {
			response.setIsError(true);
			response.setStatusCode(HttpStatus.NOT_FOUND.value());
			response.setResult(new ResponseMessage("Role not found"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		response.setIsError(false);
		response.setStatusCode(HttpStatus.OK.value());
		response.setResult(role.get());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<APIResponse> getAllRoles() {
		List<Roles> rolesList = rolesRepository.findAll();
		response.setIsError(false);
		response.setStatusCode(HttpStatus.OK.value());
		response.setResult(rolesList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<APIResponse> updateRole(int roleId, RolesDTO rolesDTO) {
		Optional<Roles> roleOpt = rolesRepository.findByRoleId(roleId);

		if (roleOpt.isEmpty()) {
			response.setIsError(true);
			response.setStatusCode(HttpStatus.NOT_FOUND.value());
			response.setResult(new ResponseMessage("Role not found"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		Roles role = roleOpt.get();
		role.setRoleName(rolesDTO.getRoleName());
		rolesRepository.save(role);

		response.setIsError(false);
		response.setStatusCode(HttpStatus.OK.value());
		response.setResult("Role updated successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<APIResponse> deleteRole(int roleId) {
		if (!rolesRepository.existsByRoleId(roleId)) {
			response.setIsError(true);
			response.setStatusCode(HttpStatus.NOT_FOUND.value());
			response.setResult(new ResponseMessage("Role not found"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		rolesRepository.deleteById(roleId);
		response.setIsError(false);
		response.setStatusCode(HttpStatus.OK.value());
		response.setResult("Role deleted successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
