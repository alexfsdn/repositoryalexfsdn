package com.apptestunitary.person.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.apptestunitary.AppTestUnitaryApplicationTests;
import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.service.EmailService;
import com.apptestunitary.service.PersonService;

public class PersonServiceDeleteTest extends AppTestUnitaryApplicationTests {

	@Autowired
	private PersonService personService;

	@Autowired
	private EmailService emailService;

	private Person personSaved;

	@Before
	public void setUp() {
		List<Email> emails = new ArrayList<>();
		emails.add(new Email("alex@gmail.com"));
		emails.add(new Email("alex2@gmail.com"));

		Person person = new Person("Alex", 21, emails);
		personSaved = personService.save(person);
		personSaved.getEmails().addAll(emailService.findEmailsByIdPerson(personSaved.getId()));
	}

	@Test
	public void mustDeletePersonByIdPerson() {

		final Long ID_PERSON = personSaved.getId();

		personService.deletePerson(ID_PERSON);
		Optional<Person> personRemoved = personService.findPerson(ID_PERSON);
		List<Email> emails = emailService.findEmailsByIdPerson(ID_PERSON);

		assertFalse(personRemoved.isPresent());
		assertThat(emails.size(), is(0));
	}
}
