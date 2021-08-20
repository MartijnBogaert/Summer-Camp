package com.martijnbogaert.summercamp;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PostalCodeValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return PostalCode.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		PostalCode postalCode = (PostalCode) target;
		
		if (postalCode.getValue() == null || postalCode.getValue().isBlank()) {
			errors.rejectValue("value", "enterPostalCode.errors.novalue", "No value entered");
		} else {
			
			try {
				int postalCodeValue = Integer.parseInt(postalCode.getValue());
				
				if (postalCodeValue < 1000) {
					errors.rejectValue("value", "enterPostalCode.errors.larger", "Postal code should be larger or equal to 1000");
				}
				
				if (postalCodeValue > 9990) {
					errors.rejectValue("value", "enterPostalCode.errors.smaller", "Postal code should smaller or equal to 9990");
				}
			} catch (NumberFormatException e) {
				errors.rejectValue("value", "enterPostalCode.errors.number", "Postal code should be a number");
			}
			
		}
	}
	
}
