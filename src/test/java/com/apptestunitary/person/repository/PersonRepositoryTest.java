package com.apptestunitary.person.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.apptestunitary.AppTests;
import com.apptestunitary.model.Person;
import com.apptestunitary.repository.PersonRepository;

public class PersonRepositoryTest extends AppTests {

	@Autowired
	private PersonRepository personRepository;

	private Person person;

	@Before
	public void setUp() {
		final String NAME = "Alex Francisco";
		final int AGE = 27;

		person = new Person(NAME, AGE, null);
	}

	@After
	public void end() {
		personRepository.deleteAll();
	}

	@Test
	public void save() {
		Person personSaved = personRepository.save(person);

		assertTrue(personSaved.getId() > 0);
		assertEquals(person.getRegistrationDate().getTime(), personSaved.getRegistrationDate().getTime());
		assertEquals(person.getDateOfLastEdition().getTime(), personSaved.getDateOfLastEdition().getTime());
		assertThat(person.getNamePerson(), is(personSaved.getNamePerson()));
		assertThat(person.getAge(), is(personSaved.getAge()));
	}

}
