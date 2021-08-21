package com.martijnbogaert.summercamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import domain.Camp;
import service.CampService;

@RestController
@RequestMapping("/summercamp")
public class SummerCampRestController {

	@Autowired
	private CampService campService;

	@GetMapping("/{id}")
	public Camp getCamp(@PathVariable int id) {
		return campService.findCamp(id);
	}

}
