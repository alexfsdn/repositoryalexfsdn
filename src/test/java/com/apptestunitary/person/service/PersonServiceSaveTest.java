package com.apptestunitary.person.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;

import com.apptestunitary.AppTestUnitaryApplicationTests;
import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.service.PersonService;

public class PersonServiceSaveTest extends AppTestUnitaryApplicationTests {

	@Autowired
	private PersonService personService;

	private List<Email> emails;

	@Before
	public void setUp() {
		emails = new ArrayList<Email>();
	}

	@Test
	public void mustSavePersonWithEmails() {

		final String EMAIL_NAME_1 = "alex@gmail.com";
		final String EMAIL_NAME_2 = "alex2@gmail.com";

		final String NAME = "Alex Francisco";
		final int AGE = 27;

		emails.add(new Email(EMAIL_NAME_1));
		emails.add(new Email(EMAIL_NAME_2));

		final int EMAIL_SIZE_WILL_DE_THREE = 2;

		Person person = new Person(NAME, AGE, emails);

		Person personSaved = personService.save(person);

		assertNotNull(personSaved.getEmails());
		assertEquals(personSaved.getRegistrationDate().getTime(), person.getRegistrationDate().getTime());
		assertEquals(personSaved.getDateOfLastEdition().getTime(), person.getDateOfLastEdition().getTime());
		assertThat(personSaved.getNamePerson(), is(NAME));
		assertThat(personSaved.getAge(), is(AGE));
		assertThat(personSaved.getEmails().size(), is(EMAIL_SIZE_WILL_DE_THREE));
		personSaved.getEmails().forEach(email -> {
			assertNotNull(email.getId());
			assertNotNull(email.getEmailName());
			assertTrue(email.getEmailName().toLowerCase().contains(EMAIL_NAME_1.toLowerCase())
					|| email.getEmailName().toLowerCase().contains(EMAIL_NAME_2.toLowerCase()));
		});
	}

	@Test(expected = TransactionSystemException.class)
	public void shouldNotSavePersonWithValuesNull() {
		final Person PERSON_WITH_VALUES_NULL = new Person();
		personService.save(PERSON_WITH_VALUES_NULL);
	}
}
