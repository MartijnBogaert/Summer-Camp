package com.martijnbogaert.summercamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
		String message = wac.getMessage("enterPostalCode.signedup", null, LocaleContextHolder.getLocale());
		
		mockMvc.perform(get("/summercamp?signedUp=true"))
			.andExpect(status().isOk())
			.andExpect(view().name("enterPostalCode"))
			.andExpect(model().attribute("signedUp", message))
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
	
	@Test
	public void showCampOverviewPage_Post_ValidPostalCode() throws Exception {
		mockMvc.perform(post("/summercamp").param("value", "8310"))
			.andExpect(status().isOk())
			.andExpect(view().name("campsOverview"))
			.andExpect(model().hasNoErrors())
			.andExpect(model().attributeHasNoErrors("postalCode"))
			.andExpect(model().attributeExists("camps"));
	}
	
	@Test
	public void showCampOverviewPage_Post_PostalCodeOnLeftBoundary() throws Exception {
		mockMvc.perform(post("/summercamp").param("value", "1000"))
			.andExpect(status().isOk())
			.andExpect(view().name("campsOverview"))
			.andExpect(model().hasNoErrors())
			.andExpect(model().attributeHasNoErrors("postalCode"))
			.andExpect(model().attributeDoesNotExist("camps")); // No camps for 1000
	}
	
	@Test
	public void showCampOverviewPage_Post_PostalCodeOnRightBoundary() throws Exception {
		mockMvc.perform(post("/summercamp").param("value", "9990"))
			.andExpect(status().isOk())
			.andExpect(view().name("campsOverview"))
			.andExpect(model().hasNoErrors())
			.andExpect(model().attributeHasNoErrors("postalCode"))
			.andExpect(model().attributeDoesNotExist("camps")); // No camps for 9990
	}
	
	@Test
	public void showCampOverviewPage_Post_NoPostalCode() throws Exception {
		mockMvc.perform(post("/summercamp").param("value", ""))
			.andExpect(status().isOk())
			.andExpect(view().name("enterPostalCode"))
			.andExpect(model().hasErrors())
			.andExpect(model().attributeHasFieldErrorCode("postalCode", "value", "enterPostalCode.errors.novalue"))
			.andExpect(model().attributeDoesNotExist("camps"))
			.andExpect(model().attributeDoesNotExist("signedUp"))
			.andExpect(model().attributeDoesNotExist("booked"))
			.andExpect(model().attributeExists("postalCode"));
	}
	
	@Test
	public void showCampOverviewPage_Post_NoIntegerPostalCode() throws Exception {
		mockMvc.perform(post("/summercamp").param("value", "test"))
			.andExpect(status().isOk())
			.andExpect(view().name("enterPostalCode"))
			.andExpect(model().hasErrors())
			.andExpect(model().attributeHasFieldErrorCode("postalCode", "value", "enterPostalCode.errors.number"))
			.andExpect(model().attributeDoesNotExist("camps"))
			.andExpect(model().attributeDoesNotExist("signedUp"))
			.andExpect(model().attributeDoesNotExist("booked"))
			.andExpect(model().attributeExists("postalCode"));
	}
	
	@Test
	public void showCampOverviewPage_Post_PostalCodeOutsideLeftBoundary() throws Exception {
		mockMvc.perform(post("/summercamp").param("value", "999"))
			.andExpect(status().isOk())
			.andExpect(view().name("enterPostalCode"))
			.andExpect(model().hasErrors())
			.andExpect(model().attributeHasFieldErrors("postalCode", "value"))
			.andExpect(model().attributeDoesNotExist("camps"))
			.andExpect(model().attributeDoesNotExist("signedUp"))
			.andExpect(model().attributeDoesNotExist("booked"))
			.andExpect(model().attributeExists("postalCode"));
	}
	
	@Test
	public void showCampOverviewPage_Post_PostalCodeOutsideRightBoundary() throws Exception {
		mockMvc.perform(post("/summercamp").param("value", "999"))
			.andExpect(status().isOk())
			.andExpect(view().name("enterPostalCode"))
			.andExpect(model().hasErrors())
			.andExpect(model().attributeHasFieldErrors("postalCode", "value"))
			.andExpect(model().attributeDoesNotExist("camps"))
			.andExpect(model().attributeDoesNotExist("signedUp"))
			.andExpect(model().attributeDoesNotExist("booked"))
			.andExpect(model().attributeExists("postalCode"));
	}
	
	@Test
	public void showCampAddPage_Get_ValidCampId() throws Exception {
		mockMvc.perform(get("/summercamp/add/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("campAdd"))
			.andExpect(model().attributeExists("camp"))
			.andExpect(model().attributeExists("person"));
	}
	
	@Test
	public void showCampAddPage_Get_NonExistentCampId() throws Exception {
		mockMvc.perform(get("/summercamp/add/100"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/summercamp"))
			.andExpect(model().attributeDoesNotExist("camp"))
			.andExpect(model().attributeDoesNotExist("person"));
	}
	
}
