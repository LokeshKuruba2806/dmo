package com.spsoft.model;

import com.spsoft.audit.Auditable;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_users")
public class User  extends Auditable<String>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

	@Column(name = "user_name")
	private String userName;

	@Column(unique = true, name = "email_id")
	private String emailId;

	@Column(unique = true, name = "phone_number")
	private String phoneNumber;

	@Column(name = "password")
	private String password;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Roles roleId;

	@Column(name = "status")
	private Boolean status;
}
