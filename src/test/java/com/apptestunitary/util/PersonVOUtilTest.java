package com.apptestunitary.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.vo.PersonVO;

public class PersonVOUtilTest {

	private static final Long ID_PERSON = 1L;
	private static final Timestamp REGISTRATION_DATE = new Timestamp(Calendar.getInstance().getTimeInMillis());

	private static final String EMAIL_NAME_1 = "alex@gmail.com";
	private static final String EMAIL_NAME_2 = "alex2@gmail.com";

	private static final String NAME = "Alex Francisco";
	private static final int AGE = 27;
	private static final Long ID_EMAIL_1 = 1L;
	private static final Long ID_EMAIL_2 = 2L;

	private Person person;

	private List<Email> emails;

	List<Person> people;

	@Test
	public void mustBuildPersonVOWithFullAttibutes() {
		mockEmails();
		mockPerson();

		PersonVO personVO = PersonVOUtil.buildPersonVO(person);

		assertThat(person.getId(), is(personVO.getId()));
		assertThat(person.getNamePerson(), is(personVO.getNamePerson()));
		assertThat(person.getAge(), is(personVO.getAge()));
		assertThat(person.getRegistrationDate().getTime(), is(personVO.getRegistrationDate().getTime()));
		assertThat(person.getDateOfLastEdition().getTime(), is(personVO.getDateOfLastEdition().getTime()));
		assertNotNull(personVO.getEmailsVO());
		assertThat(person.getEmails().size(), is(personVO.getEmailsVO().size()));
		personVO.getEmailsVO().forEach(e -> {
			assertTrue(e.getEmailName().toLowerCase().equals(EMAIL_NAME_1.toLowerCase())
					|| e.getEmailName().toLowerCase().equals(EMAIL_NAME_2.toLowerCase()));

			assertTrue(e.getId().equals(ID_EMAIL_1) || e.getId().equals(ID_EMAIL_2));

		});

	}

	@Test
	public void mustBuildListPersonVOWithFullAttibutes() {
		mockEmails();
		mockListPerson();

		List<PersonVO> peopleVO = PersonVOUtil.buildPeopleVO(people);
		assertNotNull(peopleVO);
		assertThat(peopleVO.size(), is(people.size()));

		peopleVO.forEach(personVO -> {
			assertNotNull(personVO.getId());
			assertNotNull(personVO.getNamePerson());
			assertNotNull(personVO.getAge());
			assertNotNull(personVO.getRegistrationDate());
			assertNotNull(personVO.getDateOfLastEdition());
			assertNotNull(personVO.getEmailsVO());
			personVO.getEmailsVO().forEach(e -> {
				assertTrue(e.getEmailName().toLowerCase().equals(EMAIL_NAME_1.toLowerCase())
						|| e.getEmailName().toLowerCase().equals(EMAIL_NAME_2.toLowerCase()));
				assertTrue(e.getId().equals(ID_EMAIL_1) || e.getId().equals(ID_EMAIL_2));
			});

		});
	}

	private void mockListPerson() {
		people = new ArrayList<Person>();

		people.add(new Person(1L, "Alex Francisco", 27, REGISTRATION_DATE, emails));
		people.add(new Person(1L, "Bruna", 28, REGISTRATION_DATE, emails));
		people.add(new Person(1L, "Carlos Silva", 29, REGISTRATION_DATE, emails));
		people.add(new Person(1L, "Daine Souza", 30, REGISTRATION_DATE, emails));

	}

	public void mockPerson() {
		person = new Person(ID_PERSON, NAME, AGE, REGISTRATION_DATE, emails);
	}

	public void mockEmails() {
		emails = new ArrayList<>();
		emails.add(new Email(ID_EMAIL_1, EMAIL_NAME_1));
		emails.add(new Email(ID_EMAIL_2, EMAIL_NAME_2));
	}

}