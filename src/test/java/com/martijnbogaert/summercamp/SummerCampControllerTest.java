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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest
public class SummerCampControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	private MultiValueMap<String, String> person = new LinkedMultiValueMap<>();
	
	@BeforeEach
	public void before() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		person.set("name", "Martijn");
		person.set("code1", "1");
		person.set("code2", "2");
	}
	
	@Test
	public void showEnterPostalCodePage_GET_NoRequestParams() throws Exception {
		mockMvc.perform(get("/summercamp"))
			.andExpect(status().isOk())
			.andExpect(view().name("enterPostalCode"))
			.andExpect(model().attributeDoesNotExist("signedUp"))
			.andExpect(model().attributeDoesNotExist("booked"))
			.andExpect(model().attributeExists("postalCode"));
	}
	
	@Test
	public void showEnterPostalCodePage_GET_SignedUpFalseRequestParam() throws Exception {
		mockMvc.perform(get("/summercamp?signedUp=false"))
			.andExpect(status().isOk())
			.andExpect(view().name("enterPostalCode"))
			.andExpect(model().attributeDoesNotExist("signedUp"))
			.andExpect(model().attributeDoesNotExist("booked"))
			.andExpect(model().attributeExists("postalCode"));
	}
	
	@Test
	public void showEnterPostalCodePage_GET_SignedUpTrueRequestParam() throws Exception {
		String message = wac.getMessage("enterPostalCode.signedup", null, LocaleContextHolder.getLocale());
		
		mockMvc.perform(get("/summercamp?signedUp=true"))
			.andExpect(status().isOk())
			.andExpect(view().name("enterPostalCode"))
			.andExpect(model().attribute("signedUp", message))
			.andExpect(model().attributeDoesNotExist("booked"))
			.andExpect(model().attributeExists("postalCode"));
	}
	
	@Test
	public void showEnterPostalCodePage_GET_ManagerNameRequestParam() throws Exception {
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
	public void showCampOverviewPage_POST_ValidPostalCode() throws Exception {
		mockMvc.perform(post("/summercamp").param("value", "8310"))
			.andExpect(status().isOk())
			.andExpect(view().name("campsOverview"))
			.andExpect(model().hasNoErrors())
			.andExpect(model().attributeHasNoErrors("postalCode"))
			.andExpect(model().attributeExists("camps"));
	}
	
	@Test
	public void showCampOverviewPage_POST_PostalCodeOnLeftBoundary() throws Exception {
		mockMvc.perform(post("/summercamp").param("value", "1000"))
			.andExpect(status().isOk())
			.andExpect(view().name("campsOverview"))
			.andExpect(model().hasNoErrors())
			.andExpect(model().attributeHasNoErrors("postalCode"))
			.andExpect(model().attributeDoesNotExist("camps")); // No camps for 1000
	}
	
	@Test
	public void showCampOverviewPage_POST_PostalCodeOnRightBoundary() throws Exception {
		mockMvc.perform(post("/summercamp").param("value", "9990"))
			.andExpect(status().isOk())
			.andExpect(view().name("campsOverview"))
			.andExpect(model().hasNoErrors())
			.andExpect(model().attributeHasNoErrors("postalCode"))
			.andExpect(model().attributeDoesNotExist("camps")); // No camps for 9990
	}
	
	@Test
	public void showCampOverviewPage_POST_EmptyPostalCode() throws Exception {
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
	public void showCampOverviewPage_POST_NoIntegerPostalCode() throws Exception {
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
	public void showCampOverviewPage_POST_PostalCodeOutsideLeftBoundary() throws Exception {
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
	public void showCampOverviewPage_POST_PostalCodeOutsideRightBoundary() throws Exception {
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
	public void showCampAddPage_GET_ValidCampId() throws Exception {
		mockMvc.perform(get("/summercamp/add/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("campAdd"))
			.andExpect(model().attributeExists("camp"))
			.andExpect(model().attributeExists("person"));
	}
	
	@Test
	public void showCampAddPage_GET_InvalidCampId() throws Exception {
		mockMvc.perform(get("/summercamp/add/100"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/summercamp"))
			.andExpect(model().attributeDoesNotExist("camp"))
			.andExpect(model().attributeDoesNotExist("person"));
	}
	
	@Test
	public void signUp_POST_ValidCampIdAndValidPersonAndMaxChildrenNotExceeded() throws Exception {
		mockMvc.perform(post("/summercamp/add/1").params(person))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/summercamp?signedUp=true"));
	}
	
	@Test
	public void signUp_POST_InvalidCampId() throws Exception {
		mockMvc.perform(post("/summercamp/add/100").params(person))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/summercamp"));
	}
	
	@Test
	public void signUp_POST_EmptyNamePerson() throws Exception {
		person.set("name", "");
		
		mockMvc.perform(post("/summercamp/add/1").params(person))
			.andExpect(status().isOk())
			.andExpect(view().name("campAdd"))
			.andExpect(model().hasErrors())
			.andExpect(model().attributeHasFieldErrors("person", "name"))
			.andExpect(model().attributeExists("camp"))
			.andExpect(model().attributeExists("person"));
	}
	
	@Test
	public void signUp_POST_EmptyCode1Person() throws Exception {
		person.set("code1", "");
		
		mockMvc.perform(post("/summercamp/add/1").params(person))
			.andExpect(status().isOk())
			.andExpect(view().name("campAdd"))
			.andExpect(model().hasErrors())
			.andExpect(model().attributeHasFieldErrorCode("person", "code1", "campAdd.errors.novalue"))
			.andExpect(model().attributeExists("camp"))
			.andExpect(model().attributeExists("person"));
	}
	
	@Test
	public void signUp_POST_NoIntegerCode1Person() throws Exception {
		person.set("code1", "test");
		
		mockMvc.perform(post("/summercamp/add/1").params(person))
			.andExpect(status().isOk())
			.andExpect(view().name("campAdd"))
			.andExpect(model().hasErrors())
			.andExpect(model().attributeHasFieldErrorCode("person", "code1", "campAdd.errors.number"))
			.andExpect(model().attributeExists("camp"))
			.andExpect(model().attributeExists("person"));
	}
	
	@Test
	public void signUp_POST_EmptyCode2Person() throws Exception {
		person.set("code2", "");
		
		mockMvc.perform(post("/summercamp/add/1").params(person))
			.andExpect(status().isOk())
			.andExpect(view().name("campAdd"))
			.andExpect(model().hasErrors())
			.andExpect(model().attributeHasFieldErrorCode("person", "code2", "campAdd.errors.novalue"))
			.andExpect(model().attributeExists("camp"))
			.andExpect(model().attributeExists("person"));
	}
	
	@Test
	public void signUp_POST_NoIntegerCode2Person() throws Exception {
		person.set("code2", "test");
		
		mockMvc.perform(post("/summercamp/add/1").params(person))
			.andExpect(status().isOk())
			.andExpect(view().name("campAdd"))
			.andExpect(model().hasErrors())
			.andExpect(model().attributeHasFieldErrorCode("person", "code2", "campAdd.errors.number"))
			.andExpect(model().attributeExists("camp"))
			.andExpect(model().attributeExists("person"));
	}
	
	@Test
	public void signUp_POST_Code1EqualToCode2Person() throws Exception {
		person.set("code1", person.get("code2").get(0));
		
		mockMvc.perform(post("/summercamp/add/1").params(person))
			.andExpect(status().isOk())
			.andExpect(view().name("campAdd"))
			.andExpect(model().hasErrors())
			.andExpect(model().attributeHasFieldErrorCode("person", "code1", "campAdd.errors.smaller"))
			.andExpect(model().attributeExists("camp"))
			.andExpect(model().attributeExists("person"));
	}
	
	@Test
	public void signUp_POST_Code1LargerThanCode2Person() throws Exception {
		person.set("code1", "2");
		person.set("code2", "1");
		
		mockMvc.perform(post("/summercamp/add/1").params(person))
			.andExpect(status().isOk())
			.andExpect(view().name("campAdd"))
			.andExpect(model().hasErrors())
			.andExpect(model().attributeHasFieldErrorCode("person", "code1", "campAdd.errors.smaller"))
			.andExpect(model().attributeExists("camp"))
			.andExpect(model().attributeExists("person"));
	}
	
	@Test
	public void signUp_POST_MaxChildrenExceeded() throws Exception {
		// By default, camp 2 (Dean) has 1 available place (maxChildren = 1).
		
		mockMvc.perform(post("/summercamp/add/2").params(person)); // maxChildren = 0
		
		mockMvc.perform(post("/summercamp/add/2").params(person))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/summercamp?manager_name=Dean"));
	}
	
}
