package com.spsoft.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.spsoft.dto.APIResponse;
import com.spsoft.dto.RolesDTO;
import com.spsoft.service.RolesService;

@RestController
@RequestMapping("/roles")
public class RolesController {

    private final RolesService rolesService;

    public RolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> createRole(@RequestBody RolesDTO rolesDTO) {
        return rolesService.createRole(rolesDTO);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<APIResponse> getRoleById(@PathVariable int roleId) {
        return rolesService.getRoleById(roleId);
    }

    @GetMapping
    public ResponseEntity<APIResponse> getAllRoles() {
        return rolesService.getAllRoles();
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<APIResponse> updateRole(@PathVariable int roleId, @RequestBody RolesDTO rolesDTO) {
        return rolesService.updateRole(roleId, rolesDTO);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<APIResponse> deleteRole(@PathVariable int roleId) {
        return rolesService.deleteRole(roleId);
    }
}
