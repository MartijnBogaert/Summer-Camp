package com.martijnbogaert.summercamp;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import domain.Camp;
import domain.Person;
import service.CampService;

@Controller
public class SummerCampController {
	
	@Autowired
	private CampService campService;
	
	@Autowired
	private PostalCodeValidator postalCodeValidator;
	
	@Autowired
	private PersonCodesValidator personCodesValidator;
	
	@GetMapping("/summercamp")
	public String showEnterPostalCodePage(Model model) {
		model.addAttribute("postalCode", new PostalCode());
		return "enterPostalCode";
	}
	
	@PostMapping("/summercamp")
	public String showCampOverviewPage(@ModelAttribute PostalCode postalCode, BindingResult result, Model model) {
		postalCodeValidator.validate(postalCode, result);
		if  (result.hasErrors()) return "enterPostalCode";
		
		int postalCodeValue = Integer.parseInt(postalCode.getValue());
		model.addAttribute("camps", campService.findCamps(postalCodeValue));
		
		return "campsOverview";
	}
	
	@GetMapping("/summercamp/add/{id}")
	public String showCampAddPage(@PathVariable int id, Model model) {
		Camp camp = campService.findCamp(id);
		if (camp == null) return "redirect:/summercamp";
		
		model.addAttribute("camp", camp);
		model.addAttribute("person", new Person());
		
		return "campAdd";
	}
	
	@PostMapping("/summercamp/add/{id}")
	public String signUp(@PathVariable int id, @Valid Person person, BindingResult result, Model model) {
		Camp camp = campService.findCamp(id);
		if (camp == null) return "redirect:/summercamp";
		
		personCodesValidator.validate(new String[] {person.getCode1(), person.getCode2()}, result);
		if (result.hasErrors()) {
			model.addAttribute("camp", camp);
			return "campAdd";
		}
		
		if (camp.maxChildrenExceeded()) return "redirect:/summercamp";
		camp.signUpChild(person);
		
		return "redirect:/summercamp";
	}

}
