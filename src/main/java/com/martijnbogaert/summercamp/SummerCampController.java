package com.martijnbogaert.summercamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import service.CampService;

@Controller
public class SummerCampController {
	
	@Autowired
	private CampService campService;
	
	@Autowired
	private PostalCodeValidator postalCodeValidator;
	
	@GetMapping("/summercamp")
	public String showEnterPostalCodePage(Model model) {
		model.addAttribute("postalCode", new PostalCode());
		return "enterPostalCode";
	}
	
	@PostMapping("/summercamp")
	public String showCampOverviewPage(@ModelAttribute PostalCode postalCode, BindingResult result, Model model) {
		postalCodeValidator.validate(postalCode, result);
		
		if  (result.hasErrors()) {
			return "enterPostalCode";
		}
		
		int postalCodeValue = Integer.parseInt(postalCode.getValue());
		model.addAttribute("camps", campService.findCamps(postalCodeValue));
		
		return "campsOverview";
	}

}
