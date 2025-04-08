package com.spsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.spsoft.model.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
	Optional<Roles> findByRoleId(int roleId);

	boolean existsByRoleId(Integer roleId);

	boolean existsByRoleName(String roleName);

	
}
