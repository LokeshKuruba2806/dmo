package com.spsoft.model;

import com.spsoft.audit.Auditable;

import jakarta.persistence.*;
import lombok.Data;

@Entity

@Data
public class Roles extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "role_id")
	private int roleId;

	@Column(name = "role_name")
	private String roleName;
}
