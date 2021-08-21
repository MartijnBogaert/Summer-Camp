package com.martijnbogaert.summercamp;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PersonCodesValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return String[].class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		String[] codes = (String[]) target;
		Integer[] codeValues = new Integer[codes.length];
		
		for (int i = 0; i < codes.length; i++) {
			if (codes[i] == null || codes[i].isBlank()) {
				errors.rejectValue("code" + (i + 1), "campAdd.errors.novalue", "No value entered");
			} else {
				try {
					codeValues[i] = Integer.parseInt(codes[i]);
				} catch (NumberFormatException e) {
					errors.rejectValue("code" + (i + 1), "campAdd.errors.number", "Code should be a number");
				}
			}
		}
		
		if (codeValues[0] != null && codeValues[1] != null && codeValues[0] >= codeValues[1]) {
			errors.rejectValue("code1", "campAdd.errors.smaller", "Code 1 must be smaller than code 2");
		}
	}
	
}
