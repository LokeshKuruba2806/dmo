package com.spsoft.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spsoft.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	boolean existsByEmailId(String emailId);

	boolean existsByPhoneNumber(String phoneNumber);

	boolean existsByUserName(String userName);

	Optional<User> findByEmailId(String email);

	Optional<User> findByUserId(int roleId);
}
