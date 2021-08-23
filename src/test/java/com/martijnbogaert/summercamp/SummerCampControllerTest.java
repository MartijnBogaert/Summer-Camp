package com.martijnbogaert.summercamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import domain.PostalCode;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest
public class SummerCampControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void before() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void showEnterPostalCodePage_Get_NoRequestParams() throws Exception {
		mockMvc.perform(get("/summercamp"))
			.andExpect(status().isOk())
			.andExpect(view().name("enterPostalCode"))
			.andExpect(model().attributeDoesNotExist("signedUp"))
			.andExpect(model().attributeDoesNotExist("booked"))
			.andExpect(model().attributeExists("postalCode"));
	}
	
	@Test
	public void showEnterPostalCodePage_Get_SignedUpFalseRequestParam() throws Exception {
		mockMvc.perform(get("/summercamp?signedUp=false"))
			.andExpect(status().isOk())
			.andExpect(view().name("enterPostalCode"))
			.andExpect(model().attributeDoesNotExist("signedUp"))
			.andExpect(model().attributeDoesNotExist("booked"))
			.andExpect(model().attributeExists("postalCode"));
	}
	
	@Test
	public void showEnterPostalCodePage_Get_SignedUpTrueRequestParam() throws Exception {
		mockMvc.perform(get("/summercamp?signedUp=true"))
			.andExpect(status().isOk())
			.andExpect(view().name("enterPostalCode"))
			.andExpect(model().attributeExists("signedUp"))
			.andExpect(model().attributeDoesNotExist("booked"))
			.andExpect(model().attributeExists("postalCode"));
	}
	
	@Test
	public void showEnterPostalCodePage_Get_ManagerNameRequestParam() throws Exception {
		String managerName = "Martijn";
		String[] params = { managerName };
		String message = wac.getMessage("enterPostalCode.errors.booked", params, LocaleContextHolder.getLocale());
		
		mockMvc.perform(get("/summercamp?manager_name=" + managerName))
			.andExpect(status().isOk())
			.andExpect(view().name("enterPostalCode"))
			.andExpect(model().attributeDoesNotExist("signedUp"))
			.andExpect(model().attribute("booked", message))
			.andExpect(model().attributeExists("postalCode"));
	}
	
}
