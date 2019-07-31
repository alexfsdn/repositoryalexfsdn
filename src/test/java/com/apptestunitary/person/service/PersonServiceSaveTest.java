package com.apptestunitary.person.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.apptestunitary.AppTests;
import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.repository.PersonRepository;
import com.apptestunitary.service.PersonService;

public class PersonServiceSaveTest extends AppTests {

	@Autowired
	private PersonService personService;

	@Autowired
	private PersonRepository personRepository;

	private Person person;

	@Before
	public void setUp() {
		List<Email> emails = new ArrayList<Email>();

		final String EMAIL_NAME_1 = "alex@gmail.com";
		final String EMAIL_NAME_2 = "alex2@gmail.com";

		final String NAME = "Alex Francisco";
		final int AGE = 27;

		emails.add(new Email(EMAIL_NAME_1));
		emails.add(new Email(EMAIL_NAME_2));

		person = new Person(NAME, AGE, emails);
	}

	@After
	public void end() {
		personRepository.deleteAll();
	}

	@Test
	public void mustSavePersonWithEmails() {

		final String EMAIL_NAME_1 = person.getEmails().get(0).getEmailName();
		final String EMAIL_NAME_2 = person.getEmails().get(1).getEmailName();

		final int EMAIL_SIZE_WILL_DE_TWO = 2;

		Person personSaved = personService.save(person);

		assertTrue(personSaved.getId() > 0);
		assertNotNull(personSaved.getEmails());
		assertEquals(person.getRegistrationDate().getTime(), personSaved.getRegistrationDate().getTime());
		assertEquals(person.getDateOfLastEdition().getTime(), personSaved.getDateOfLastEdition().getTime());
		assertThat(person.getNamePerson(), is(personSaved.getNamePerson()));
		assertThat(person.getAge(), is(personSaved.getAge()));
		assertThat(EMAIL_SIZE_WILL_DE_TWO, is(personSaved.getEmails().size()));
		personSaved.getEmails().forEach(email -> {
			assertNotNull(email.getId());
			assertNotNull(email.getEmailName());
			assertTrue(email.getEmailName().toLowerCase().contains(EMAIL_NAME_1.toLowerCase())
					|| email.getEmailName().toLowerCase().contains(EMAIL_NAME_2.toLowerCase()));
		});
	}

}
