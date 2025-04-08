package com.spsoft.service;

import org.springframework.http.ResponseEntity;
import com.spsoft.dto.APIResponse;
import com.spsoft.dto.RolesDTO;

public interface RolesService {
    ResponseEntity<APIResponse> createRole(RolesDTO rolesDTO);
    ResponseEntity<APIResponse> getRoleById(int roleId);
    ResponseEntity<APIResponse> getAllRoles();
    ResponseEntity<APIResponse> updateRole(int roleId, RolesDTO rolesDTO);
    ResponseEntity<APIResponse> deleteRole(int roleId);
}
