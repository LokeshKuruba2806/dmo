package com.spsoft.validation_service_impl;

import com.spsoft.ivalidation_service.IValidationService;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class ValidationService implements IValidationService {

	@Override
	public boolean isValidPassword(String password) {
		if (password == null) {
			return false;
		}

		String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

		return Pattern.matches(passwordPattern, password);

	}

	@Override
	public boolean isValidPhoneNumber(String phoneNumber) {
	    String PHONE_REGEX = "^[6789]\\d{9}$";

	    if (phoneNumber == null) {
	        throw new IllegalArgumentException("Phone number cannot be null");
	    }

	    boolean isValid = phoneNumber.matches(PHONE_REGEX);
	    
	    if (!isValid) {
	        throw new IllegalArgumentException("Invalid phone number format");
	    }

	    return true; 
	}

}
