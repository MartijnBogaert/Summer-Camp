package com.martijnbogaert.summercamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import service.CampService;

@Controller
public class SummerCampController {
	
	@Autowired
	private CampService campService;
	
	@GetMapping("/summercamp")
	public String showEnterPostalCodePage(Model model) {
		model.addAttribute("postalCode", new PostalCode());
		return "enterPostalCode";
	}
	
	@PostMapping("/summercamp")
	public String showCampOverviewPage(@ModelAttribute PostalCode postalCode, Model model) {
		model.addAttribute("camps", campService.findCamps(postalCode.getValue()));
		return "campsOverview";
	}

}
