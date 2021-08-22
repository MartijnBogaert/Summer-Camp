package com.martijnbogaert.summercamp;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		auth.inMemoryAuthentication()
			.withUser("user")
			.password(encoder.encode("user"))
			.roles("USER")
			.and()
			.withUser("admin")
			.password(encoder.encode("admin"))
			.roles("USER", "ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().defaultSuccessUrl("/summercamp", true).loginPage("/login");
		
		http.authorizeRequests()
			.antMatchers("/css/**").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/summercamp/*").permitAll()
			.antMatchers("/summercamp/add/**").hasRole("ADMIN")
			.antMatchers("/**").hasRole("USER")
			.and()
			.exceptionHandling().accessDeniedPage("/403")
			.and()
			.csrf();
	}

}
