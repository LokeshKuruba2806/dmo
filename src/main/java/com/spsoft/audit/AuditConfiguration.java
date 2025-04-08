package com.spsoft.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditConfiguration {
	@Bean
	 AuditorAware<String> auditorProvider() {
		return new AuditorAwareImpl();
	}
}
