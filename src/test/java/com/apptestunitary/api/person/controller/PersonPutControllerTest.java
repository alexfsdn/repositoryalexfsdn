package com.apptestunitary.api.person.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestClientException;

import com.apptestunitary.AppTestUnitaryApplicationTests;
import com.apptestunitary.enums.url.PersonURIEnum;
import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.repository.PersonRepository;
import com.apptestunitary.service.PersonService;
import com.apptestunitary.util.EmailVOUtil;
import com.apptestunitary.vo.EmailVO;
import com.apptestunitary.vo.PersonVO;

public class PersonPutControllerTest extends AppTestUnitaryApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PersonService personService;

	private Person personSaved;

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

		Person personToUpdate = new Person(NAME, AGE, emails);
		personSaved = personService.save(personToUpdate);
	}

	@After
	public void end() {
		personRepository.deleteAll();
	}

	@Test
	public void mustUpdatePersonWithEmails() throws RestClientException, URISyntaxException {

		final Long ID_PERSON = personSaved.getId();
		final String NAME = "Alex Francisco Atualizado API REST";
		final int AGE = 30;
		final Timestamp REGISTRATION_DATE = personSaved.getRegistrationDate();
		final Timestamp DATA_OF_LAST_EDITION = personSaved.getDateOfLastEdition();

		List<EmailVO> emailsVOs = EmailVOUtil.buildEmailVO(personSaved.getEmails());

		final String EMAIL_2_NAME = personSaved.getEmails().get(1).getEmailName();
		final String EMAIL_NEW = "emailNew@gmail.com";
		final String EMAIL_OLD_BUT_UPDATED = "emailOldButUpdated@gmail.com";

		emailsVOs.add(new EmailVO(EMAIL_NEW));
		emailsVOs.get(0).setEmailName(EMAIL_OLD_BUT_UPDATED);

		final int EMAIL_SIZE_WILL_DE_THREE = 3;

		final PersonVO PERSON_VO = new PersonVO(ID_PERSON, NAME, AGE, REGISTRATION_DATE, emailsVOs);
		final URI URI = new URI(PersonURIEnum.URL_PERSON.getUrl());

		restTemplate.put(URI, PERSON_VO);

		Optional<Person> personUpdated = personService.findPerson(ID_PERSON);

		assertTrue(personUpdated.isPresent());
		assertThat(NAME, is(personUpdated.get().getNamePerson()));
		assertThat(AGE, is(personUpdated.get().getAge()));
		assertTrue(personUpdated.get().getDateOfLastEdition().after(DATA_OF_LAST_EDITION));
		assertFalse(personUpdated.get().getDateOfLastEdition().getTime() == REGISTRATION_DATE.getTime());
		assertNotNull(personUpdated.get().getEmails());
		assertThat(EMAIL_SIZE_WILL_DE_THREE, is(personUpdated.get().getEmails().size()));
		personUpdated.get().getEmails().forEach(e -> {
			assertTrue(e.getEmailName().toLowerCase().contains(EMAIL_NEW.toLowerCase())
					|| e.getEmailName().toLowerCase().contains(EMAIL_OLD_BUT_UPDATED.toLowerCase())
					|| e.getEmailName().toLowerCase().contains(EMAIL_2_NAME.toLowerCase()));
		});

	}

}