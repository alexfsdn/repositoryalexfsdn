package com.apptestunitary.person.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
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

public class PersonServiceUpdateTest extends AppTests {

	@Autowired
	private PersonService personService;

	@Autowired
	private PersonRepository personRepository;

	private Person personSaved;

	@Before
	public void setUp() {
		final String NAME = "Alex Francisco";
		final int AGE = 27;

		List<Email> emails = new ArrayList<>();
		emails.add(new Email("alex@gmail.com"));
		emails.add(new Email("alex2@gmail.com"));

		Person person = new Person(NAME, AGE, emails);
		personSaved = personService.save(person);
	}

	@After
	public void end() {
		personRepository.deleteAll();
	}

	@Test
	public void mustUpdatePerson() {

		final Long ID_PERSON = personSaved.getId();
		final String NAME = "Alex NOME ALTERADO";
		final int AGE = 30;
		final List<Email> EMAILS = personSaved.getEmails();
		final Timestamp REGISTRATION_DATE = personSaved.getRegistrationDate();
		final Timestamp DATA_OF_LAST_EDITION = personSaved.getDateOfLastEdition();

		Person personToUpdate = new Person(ID_PERSON, NAME, AGE, REGISTRATION_DATE, EMAILS);

		Person personUpdated = personService.save(personToUpdate);

		assertThat(personUpdated.getEmails().size() == EMAILS.size());
		assertThat(personUpdated.getNamePerson(), is(NAME));
		assertThat(personUpdated.getAge(), is(AGE));
		assertTrue(personUpdated.getDateOfLastEdition().after(DATA_OF_LAST_EDITION));
		assertFalse(personUpdated.getDateOfLastEdition().getTime() == REGISTRATION_DATE.getTime());
	}

	@Test
	public void mustUpdatePersonWithEmailsChanged() {

		final Long ID_PERSON = personSaved.getId();
		final String NAME = personSaved.getNamePerson();
		final int AGE = personSaved.getAge();
		final Timestamp REGISTRATION_DATE = personSaved.getRegistrationDate();

		final String EMAIL_2_NAME = personSaved.getEmails().get(1).getEmailName();
		final String EMAIL_NEW = "emailNew@gmail.com";
		final String EMAIL_OLD_BUT_UPDATED = "emailOldButUpdated@gmail.com";

		final Long ID_EMAIL = personSaved.getEmails().get(0).getId();

		List<Email> emails = new ArrayList<>();
		emails.add(new Email(ID_EMAIL, EMAIL_OLD_BUT_UPDATED));
		emails.add(new Email(EMAIL_NEW));

		Person personToUpdate = new Person(ID_PERSON, NAME, AGE, REGISTRATION_DATE, emails);

		final int EMAIL_SIZE_WILL_DE_THREE = 3;

		Person personUpdated = personService.save(personToUpdate);

		assertNotNull(personUpdated);
		assertNotNull(personUpdated.getEmails());
		assertThat(personUpdated.getEmails().size(), is(EMAIL_SIZE_WILL_DE_THREE));
		personUpdated.getEmails().forEach(e -> {
			assertTrue(e.getEmailName().toLowerCase().contains(EMAIL_NEW.toLowerCase())
					|| e.getEmailName().toLowerCase().contains(EMAIL_OLD_BUT_UPDATED.toLowerCase())
					|| e.getEmailName().toLowerCase().contains(EMAIL_2_NAME.toLowerCase()));
		});

	}

}
