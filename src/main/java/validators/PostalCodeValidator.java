package validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import domain.PostalCode;

public class PostalCodeValidator implements Validator {
	
	@Autowired
	private MessageSource messageSource;
	
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
				
				Integer min = 1000;
				if (postalCodeValue < min) {
					String[] params = {min.toString()};
					String message = messageSource.getMessage("enterPostalCode.errors.larger", params, LocaleContextHolder.getLocale());
					errors.rejectValue("value", null, message);
				}
				
				Integer max = 9990;
				if (postalCodeValue > max) {
					String[] params = {max.toString()};
					String message = messageSource.getMessage("enterPostalCode.errors.smaller", params, LocaleContextHolder.getLocale());
					errors.rejectValue("value", null, message);
				}
			} catch (NumberFormatException e) {
				errors.rejectValue("value", "enterPostalCode.errors.number", "Postal code should be a number");
			}
			
		}
	}
	
}
