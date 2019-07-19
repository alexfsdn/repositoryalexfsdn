package com.apptestunitary.person.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.apptestunitary.AppTestUnitaryApplicationTests;
import com.apptestunitary.enums.DataFormatoEnum;
import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.service.PersonService;

public class PersonServiceFindTest extends AppTestUnitaryApplicationTests {

	@Autowired
	private PersonService personService;

	private Person personSaved;

	private final String NAME_TO_SEARCH = "Ale";

	@Before
	public void setUp() {
		List<Email> emails = new ArrayList<>();
		emails.add(new Email("alex@gmail.com"));
		emails.add(new Email("alex2@gmail.com"));

		Person person = new Person("Alex", 21, emails);
		personSaved = personService.save(person);
	}

	@Test
	public void mustFindPersonById() {

		final Long ID_PERSON = personSaved.getId();
		Optional<Person> person = personService.findPerson(ID_PERSON);

		assertTrue(person.isPresent());
		assertNotNull(person.get().getEmails());
		assertThat(person.get().getEmails().size() > 0);
	}

	@Test
	public void mustFindPersonByNamePerson() {

		List<Person> people = personService.findSearchingNamePerson(NAME_TO_SEARCH).get();

		assertNotNull(people);
		assertThat(people.size() > 0);
	}

	@Test
	public void mustFindPersonByNamePersonAndDateOfLastEdition() throws ParseException {

		final String DATE_FOR_FIRST_PERIOD = "2018-01-01 00:00:11.763";
		final String DATE_FOR_SECOND_PERIOD = "2019-09-01 00:00:11.763";
		Timestamp firstPeriod = null;
		Timestamp secondPeriod = null;

		SimpleDateFormat simpleDateFormatForFirstPeriod = new SimpleDateFormat(
				DataFormatoEnum.YEAR_DAY_HOUR_MINUTES_SECOND_SSS.getLabel());
		SimpleDateFormat simpleDateFormatForSecondPeriod = new SimpleDateFormat(
				DataFormatoEnum.YEAR_DAY_HOUR_MINUTES_SECOND_SSS.getLabel());

		firstPeriod = new Timestamp(simpleDateFormatForFirstPeriod.parse(DATE_FOR_FIRST_PERIOD).getTime());
		secondPeriod = new Timestamp(simpleDateFormatForSecondPeriod.parse(DATE_FOR_SECOND_PERIOD).getTime());

		List<Person> people = personService
				.findPersonByNameAndDateOfLastEdition(NAME_TO_SEARCH, firstPeriod, secondPeriod).get();

		assertNotNull(people);
		assertThat(people.size() > 0);
	}

}