package com.martijnbogaert.summercamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import service.CampService;
import service.SummerCampServiceImpl;

@SpringBootApplication
public class SummerCampApplication {

	public static void main(String[] args) {
		SpringApplication.run(SummerCampApplication.class, args);
	}
	
	@Bean
	public CampService campService() {
		return new SummerCampServiceImpl();
	}

}
