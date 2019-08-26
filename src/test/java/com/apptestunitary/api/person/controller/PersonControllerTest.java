package com.apptestunitary.api.person.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import com.apptestunitary.enums.url.PersonURIEnum;
import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.repository.PersonRepository;
import com.apptestunitary.service.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private Person personSaved;

	@Autowired
	private PersonService personService;

	@Autowired
	private PersonRepository personRepository;

	@Before
	public void setUp() throws ParseException {

		final String NAME = "Alex";
		final int AGE = 27;

		final String EMAIL_NAME_1 = "alex@gmail.com";
		final String EMAIL_NAME_2 = "alex2@gmail.com";

		List<Email> emails = new ArrayList<>();
		emails.add(new Email(EMAIL_NAME_1));
		emails.add(new Email(EMAIL_NAME_2));

		Person personToSave = new Person(NAME, AGE, emails);
		personSaved = personService.save(personToSave);
	}

	@After
	public void clear() {
		personRepository.deleteAll();
	}

	@Test
	public void statusIsOkWhenFindPersonById() throws Exception {
		final Long ID_PERSON = personSaved.getId();

		ResultActions resultActions = mockMvc
				.perform(MockMvcRequestBuilders.get(PersonURIEnum.URL_PERSON.getUrl() + ID_PERSON));

		StatusResultMatchers status = MockMvcResultMatchers.status();

		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(status.isOk());
		resultActions.andExpect(status.is(200));
		resultActions.andExpect(status.is(Matchers.is(200)));

	}

	@Test
	public void statusIsNotConteWhenShouldNotFindPersonById() throws Exception {
		final Long ID_NONEXISTENT = 2L;

		ResultActions resultActions = mockMvc
				.perform(MockMvcRequestBuilders.get(PersonURIEnum.URL_PERSON.getUrl() + ID_NONEXISTENT));

		resultActions.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void statusIsNotAccentableWhenIdIsZero() throws Exception {
		final Long ID_WITH_VALUE_ZERO = 0L;

		ResultActions resultActions = mockMvc
				.perform(MockMvcRequestBuilders.get(PersonURIEnum.URL_PERSON.getUrl() + ID_WITH_VALUE_ZERO));

		resultActions.andExpect(MockMvcResultMatchers.status().isNotAcceptable());
	}

}
