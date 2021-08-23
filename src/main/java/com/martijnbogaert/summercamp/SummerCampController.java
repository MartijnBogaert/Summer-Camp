package com.martijnbogaert.summercamp;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import domain.Camp;
import domain.Person;
import domain.PostalCode;
import service.CampService;
import validators.PersonCodesValidator;
import validators.PostalCodeValidator;

@Controller
@RequestMapping("/summercamp")
public class SummerCampController {

	@Autowired
	private CampService campService;

	@Autowired
	private PostalCodeValidator postalCodeValidator;

	@Autowired
	private PersonCodesValidator personCodesValidator;

	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public String showEnterPostalCodePage(@RequestParam(value = "signedUp", required = false) boolean signedUp,
			@RequestParam(value = "manager_name", required = false) String managerName, Model model) {
		if (signedUp) {
			String message = messageSource.getMessage("enterPostalCode.signedup", null,
					LocaleContextHolder.getLocale());
			model.addAttribute("signedUp", message);
		}
		if (managerName != null) {
			String[] params = { managerName };
			String message = messageSource.getMessage("enterPostalCode.errors.booked", params,
					LocaleContextHolder.getLocale());
			model.addAttribute("booked", message);
		}

		PostalCode postalCode = new PostalCode();
		postalCode.setValue("8000");
		model.addAttribute("postalCode", postalCode);

		return "enterPostalCode";
	}

	@PostMapping
	public String showCampOverviewPage(@ModelAttribute PostalCode postalCode, BindingResult result, Model model) {
		postalCodeValidator.validate(postalCode, result);
		if (result.hasErrors())
			return "enterPostalCode";

		int postalCodeValue = Integer.parseInt(postalCode.getValue());
		model.addAttribute("camps", campService.findCamps(postalCodeValue));

		return "campsOverview";
	}

	@GetMapping("/add/{id}")
	public String showCampAddPage(@PathVariable int id, Model model) {
		Camp camp = campService.findCamp(id);
		if (camp == null)
			return "redirect:/summercamp";

		model.addAttribute("camp", camp);
		model.addAttribute("person", new Person());

		return "campAdd";
	}

	@PostMapping("/add/{id}")
	public String signUp(@PathVariable int id, @Valid Person person, BindingResult result, Model model) {
		Camp camp = campService.findCamp(id);
		if (camp == null)
			return "redirect:/summercamp";

		personCodesValidator.validate(new String[] { person.getCode1(), person.getCode2() }, result);
		if (result.hasErrors()) {
			model.addAttribute("camp", camp);
			return "campAdd";
		}

		if (camp.maxChildrenExceeded())
			return "redirect:/summercamp?manager_name=" + camp.getManager().getName();
		
		camp.signUpChild(person);

		return "redirect:/summercamp?signedUp=true";
	}

}
