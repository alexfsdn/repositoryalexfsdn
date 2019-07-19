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

import com.apptestunitary.model.Person;
import com.apptestunitary.vo.EmailVO;
import com.apptestunitary.vo.PersonVO;

public class PersonUtilTest {

	private static final Long ID_PERSON = 1L;
	private static final Timestamp AGORA = new Timestamp(Calendar.getInstance().getTimeInMillis());

	private static final String EMAIL_NAME_1 = "alex@gmail.com";
	private static final String EMAIL_NAME_2 = "alex2@gmail.com";

	private static final String NAME = "Alex Francisco";
	private static final int AGE = 27;
	private static final Long ID_EMAIL_1 = 1L;
	private static final Long ID_EMAIL_2 = 2L;

	private PersonVO personVO;

	private List<EmailVO> emailsVOs;
	private List<PersonVO> peopleVO;

	@Test
	public void mustBuildPersonWithFullAttibutes() {
		mockEmailsVOWithId();
		mockPersonVOWithId();

		Person person = PersonUtil.buildPerson(personVO);

		assertNotNull(person);
		assertThat(person.getId(), is(personVO.getId()));
		assertThat(person.getNamePerson(), is(personVO.getNamePerson()));
		assertThat(person.getAge(), is(personVO.getAge()));
		assertThat(person.getRegistrationDate().getTime(), is(personVO.getRegistrationDate().getTime()));
		assertTrue(person.getDateOfLastEdition().after(AGORA));
		assertThat(person.getEmails().size(), is(personVO.getEmailsVO().size()));
		person.getEmails().forEach(e -> {
			assertTrue(e.getEmailName().toLowerCase().equals(EMAIL_NAME_1.toLowerCase())
					|| e.getEmailName().toLowerCase().equals(EMAIL_NAME_2.toLowerCase()));

			assertTrue(e.getId().equals(ID_EMAIL_1) || e.getId().equals(ID_EMAIL_2));

		});

	}

	@Test
	public void mustBuildListPersonVOWithFullAttibutes() {
		mockEmailsVOWithId();
		mockListPersonVOWithId();

		List<Person> people = PersonUtil.buildPeople(peopleVO);

		assertNotNull(people);
		assertThat(people.size(), is(peopleVO.size()));

		people.forEach(person -> {
			assertNotNull(person.getId());
			assertNotNull(person.getNamePerson());
			assertTrue(person.getAge() > 0);
			assertTrue(person.getRegistrationDate().after(AGORA));
			assertTrue(person.getDateOfLastEdition().after(AGORA));
			assertTrue(person.getEmails().size() > 0);

			person.getEmails().forEach(e -> {
				assertTrue(e.getEmailName().toLowerCase().equals(EMAIL_NAME_1.toLowerCase())
						|| e.getEmailName().toLowerCase().equals(EMAIL_NAME_2.toLowerCase()));
				assertTrue(e.getId().equals(ID_EMAIL_1) || e.getId().equals(ID_EMAIL_2));
			});

		});
	}

	@Test
	public void mustBuildPersonWithFullAttibutesWithoutId() {
		mockEmailsVOsWithoutId();
		mockPersonVOWithoutId();

		Person person = PersonUtil.buildPerson(personVO);

		assertNotNull(person);
		assertThat(person.getNamePerson(), is(personVO.getNamePerson()));
		assertThat(person.getAge(), is(personVO.getAge()));
		assertTrue(person.getRegistrationDate().after(AGORA));
		assertTrue(person.getDateOfLastEdition().after(AGORA));
		assertThat(person.getEmails().size(), is(personVO.getEmailsVO().size()));
		person.getEmails().forEach(e -> {
			assertTrue(e.getEmailName().toLowerCase().equals(EMAIL_NAME_1.toLowerCase())
					|| e.getEmailName().toLowerCase().equals(EMAIL_NAME_2.toLowerCase()));
		});

	}

	@Test
	public void mustBuildListPersonVOWithFullAttibutesWithoutId() {
		mockEmailsVOsWithoutId();
		mockListPersonVOWithoutId();

		List<Person> people = PersonUtil.buildPeople(peopleVO);

		assertNotNull(people);
		assertThat(people.size(), is(peopleVO.size()));

		people.forEach(person -> {
			assertNotNull(person.getNamePerson());
			assertTrue(person.getAge() > 0);
			assertTrue(person.getRegistrationDate().after(AGORA));
			assertTrue(person.getDateOfLastEdition().after(AGORA));
			assertTrue(person.getEmails().size() > 0);

			person.getEmails().forEach(e -> {
				assertTrue(e.getEmailName().toLowerCase().equals(EMAIL_NAME_1.toLowerCase())
						|| e.getEmailName().toLowerCase().equals(EMAIL_NAME_2.toLowerCase()));
			});

		});
	}

	private void mockListPersonVOWithId() {
		peopleVO = new ArrayList<PersonVO>();

		final Timestamp REGISTRATION_DATE = new Timestamp(Calendar.getInstance().getTimeInMillis());
		peopleVO.add(new PersonVO(1L, "Alex Francisco", 27, REGISTRATION_DATE, emailsVOs));
		peopleVO.add(new PersonVO(1L, "Bruna", 28, REGISTRATION_DATE, emailsVOs));
		peopleVO.add(new PersonVO(1L, "Carlos Silva", 29, REGISTRATION_DATE, emailsVOs));
		peopleVO.add(new PersonVO(1L, "Daine Souza", 30, REGISTRATION_DATE, emailsVOs));

	}

	public void mockPersonVOWithId() {
		final Timestamp REGISTRATION_DATE = new Timestamp(Calendar.getInstance().getTimeInMillis());
		personVO = new PersonVO(ID_PERSON, NAME, AGE, REGISTRATION_DATE, emailsVOs);
	}

	public void mockEmailsVOWithId() {
		emailsVOs = new ArrayList<>();
		emailsVOs.add(new EmailVO(ID_EMAIL_1, EMAIL_NAME_1));
		emailsVOs.add(new EmailVO(ID_EMAIL_2, EMAIL_NAME_2));
	}

	private void mockListPersonVOWithoutId() {
		peopleVO = new ArrayList<PersonVO>();

		peopleVO.add(new PersonVO("Alex Francisco", 27, emailsVOs));
		peopleVO.add(new PersonVO("Bruna", 28, emailsVOs));
		peopleVO.add(new PersonVO("Carlos Silva", 29, emailsVOs));
		peopleVO.add(new PersonVO("Daine Souza", 30, emailsVOs));

	}

	public void mockPersonVOWithoutId() {
		personVO = new PersonVO(NAME, AGE, emailsVOs);
	}

	public void mockEmailsVOsWithoutId() {
		emailsVOs = new ArrayList<>();
		emailsVOs.add(new EmailVO(EMAIL_NAME_1));
		emailsVOs.add(new EmailVO(EMAIL_NAME_2));
	}

}