package com.martijnbogaert.summercamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import service.CampService;
import service.SummerCampServiceImpl;

@SpringBootApplication
public class SummerCampApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SummerCampApplication.class, args);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("resources/css/");
	}
	
	@Bean
	public CampService campService() {
		return new SummerCampServiceImpl();
	}

}
