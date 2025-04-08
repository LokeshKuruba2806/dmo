package com.spsoft.audit;

import java.util.Objects;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Primary
public class AuditorAwareImpl implements AuditorAware<String> {
	@Override
	public Optional<String> getCurrentAuditor() {
		return (Objects.isNull(SecurityContextHolder.getContext().getAuthentication()) ? Optional.of("System")
				: Optional.of((SecurityContextHolder.getContext().getAuthentication().getName())));
	}
}
