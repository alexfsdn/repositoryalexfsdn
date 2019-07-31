package com.apptestunitary.api.person.controller;

import static org.junit.Assert.assertFalse;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestClientException;

import com.apptestunitary.AppTestUnitaryApplicationTests;
import com.apptestunitary.enums.url.PersonURIEnum;
import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.service.PersonService;

public class PersonDeleteControllerTest extends AppTestUnitaryApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PersonService personService;

	private Person personSaved;

	@Before
	public void setUp() throws ParseException {

		final String NAME = "Alex";
		final int AGE = 27;

		final String EMAIL_NAME_1 = "alex@gmail.com";
		final String EMAIL_NAME_2 = "alex2@gmail.com";

		List<Email> emails = new ArrayList<>();
		emails.add(new Email(EMAIL_NAME_1));
		emails.add(new Email(EMAIL_NAME_2));

		Person personToUpdate = new Person(NAME, AGE, emails);
		personSaved = personService.save(personToUpdate);
	}

	@Test
	public void mustDeletePerson() throws RestClientException, URISyntaxException {

		final Long ID_PERSON = personSaved.getId();
		final URI URI = new URI(PersonURIEnum.URL_PERSON.getUrl());

		restTemplate.delete(URI + "" + ID_PERSON);

		Optional<Person> personRemoved = personService.findPerson(ID_PERSON);

		assertFalse(personRemoved.isPresent());
	}
}