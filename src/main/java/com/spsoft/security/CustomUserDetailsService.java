package com.spsoft.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spsoft.model.User;
import com.spsoft.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired

	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmailId(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return new org.springframework.security.core.userdetails.User(user.getEmailId(),

				user.getPassword(),

				Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
	}
}
