package com.martijnbogaert.summercamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import service.CampService;
import service.SummerCampServiceImpl;
import validators.PersonCodesValidator;
import validators.PostalCodeValidator;

@SpringBootApplication
public class SummerCampApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SummerCampApplication.class, args);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("resources/css/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/403").setViewName("403");
	}

	@Bean
	public CampService campService() {
		return new SummerCampServiceImpl();
	}

	@Bean
	public PostalCodeValidator postalCodeValidator() {
		return new PostalCodeValidator();
	}

	@Bean
	public PersonCodesValidator personCodesValidator() {
		return new PersonCodesValidator();
	}

}
