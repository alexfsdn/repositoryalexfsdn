package com.apptestunitary.mock.person.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.repository.PersonRepository;
import com.apptestunitary.service.EmailService;
import com.apptestunitary.service.PersonService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

	@MockBean
	private PersonRepository personRepository;

	@MockBean
	private EmailService emailService;

	@Autowired
	private PersonService personService;

	private Person person;
	private Person personSaved;
	private List<Email> emails;
	private List<Email> emailsSaved;

	private static final Long ID_PERSON = 1L;
	private static final Timestamp REGISTRATION_DATE = new Timestamp(Calendar.getInstance().getTimeInMillis());

	private static final String EMAIL_NAME_1 = "alex@gmail.com";
	private static final String EMAIL_NAME_2 = "alex2@gmail.com";

	private static final String NAME = "Alex Francisco";
	private static final int AGE = 27;

	private final Person PERSON_WITH_VALUES_NULL = new Person();

	@Before
	public void setUp() {
		mockEmailsToSave();
		mockPersonToSave();
		mockEmailsSaved();
		mockPersonSaved();

		when(personRepository.save(person)).thenReturn(personSaved);
		when(personRepository.save(PERSON_WITH_VALUES_NULL)).thenThrow(TransactionSystemException.class);
		when(personRepository.save(personSaved)).thenReturn(personSaved);

		when(emailService.saveList(emails)).thenReturn(emailsSaved);
	}

	@Test
	public void mustSavePersonWithEmails() {

		Person personSaved = personService.save(person);

		assertNotNull(personSaved);
		assertNotNull(personSaved.getEmails());
		verify(personRepository, times(1)).save(any());
		verify(emailService, times(1)).saveList(any());
		verify(emailService, times(0)).findEmailsByIdPerson(any());
	}

	@Test
	public void mustSavePersonWithoutEmails() {

		person.getEmails().clear();

		personService.save(person);

		verify(personRepository, times(1)).save(any());
		verify(emailService, times(0)).saveList(any());
		verify(emailService, times(0)).findEmailsByIdPerson(any());
	}

	@Test(expected = TransactionSystemException.class)
	public void shouldNotSavePersonWithValuesNull() {

		personService.save(PERSON_WITH_VALUES_NULL);

		verify(personRepository, times(1)).save(any());
		verify(emailService, times(0)).saveList(any());
		verify(emailService, times(0)).findEmailsByIdPerson(any());
	}

	@Test
	public void mustRunEmailSericeTwiceInWhenUpdatePerson() {

		personService.save(personSaved);

		verify(personRepository, times(1)).save(any());
		verify(emailService, times(1)).saveList(any());
		verify(emailService, times(1)).findEmailsByIdPerson(any());
	}

	@Test
	public void shouldOnlyPassInFindSearchingNamePersonWithValuesFirstPeriodAndSecondPeriodNullInFindPersonByNameAndDateOfLastEdition() {

		final Timestamp FIRST_PERIOD_NULL = null;
		final Timestamp SECOND_PERIOD_NULL = null;
		final String NAME_PERSON_TO_SEARCH = "Ale";

		personService.findPersonByNameAndDateOfLastEdition(NAME_PERSON_TO_SEARCH, FIRST_PERIOD_NULL,
				SECOND_PERIOD_NULL);

		verify(personRepository, times(0)).findPersonByNameAndDateOfLastEdition(
				NAME_PERSON_TO_SEARCH.toLowerCase().trim(), FIRST_PERIOD_NULL, SECOND_PERIOD_NULL);
		verify(personRepository, times(1)).findSearchingNamePerson(NAME_PERSON_TO_SEARCH.toLowerCase().trim());
	}

	@Test
	public void shouldOnlyPassInFindPersonByNameAndDateOfLastEditionWithValuesFirstPeriodAndSecondPeriodFull() {

		final Timestamp FIRST_PERIOD = new Timestamp(Calendar.getInstance().getTimeInMillis());
		final Timestamp SECOND_PERIOD = new Timestamp(Calendar.getInstance().getTimeInMillis());
		final String NAME_PERSON_TO_SEARCH = "Ale";

		personService.findPersonByNameAndDateOfLastEdition(NAME_PERSON_TO_SEARCH, FIRST_PERIOD, SECOND_PERIOD);

		verify(personRepository, times(1)).findPersonByNameAndDateOfLastEdition(
				NAME_PERSON_TO_SEARCH.toLowerCase().trim(), FIRST_PERIOD, SECOND_PERIOD);
		verify(personRepository, times(0)).findSearchingNamePerson(NAME_PERSON_TO_SEARCH.toLowerCase().trim());
	}

	@Test
	public void shouldOnlyPassInFindSearchingNamePersonWithValuesFirstPeriodAndSecondPeriodNullSerachInFindPersonByNameAndDateOfLastEdition() {

		final Timestamp FIRST_PERIOD_NULL = null;
		final Timestamp SECOND_PERIOD_NULL = null;
		final String NAME_PERSON_TO_SEARCH = "Ale";

		personService.findPersonByNameAndRegistrationDate(NAME_PERSON_TO_SEARCH, FIRST_PERIOD_NULL, SECOND_PERIOD_NULL);

		verify(personRepository, times(0)).findPersonByNameAndDateOfLastEdition(
				NAME_PERSON_TO_SEARCH.toLowerCase().trim(), FIRST_PERIOD_NULL, SECOND_PERIOD_NULL);
		verify(personRepository, times(1)).findSearchingNamePerson(NAME_PERSON_TO_SEARCH.toLowerCase().trim());
	}

	@Test
	public void shouldOnlyPassInFindPersonByNameAndRegistrationDateWithValuesFirstPeriodAndSecondPeriodFull() {

		final Timestamp FIRST_PERIOD_NULL = new Timestamp(Calendar.getInstance().getTimeInMillis());
		final Timestamp SECOND_PERIOD_NULL = new Timestamp(Calendar.getInstance().getTimeInMillis());
		final String NAME_PERSON_TO_SEARCH = "Ale";

		personService.findPersonByNameAndDateOfLastEdition(NAME_PERSON_TO_SEARCH, FIRST_PERIOD_NULL,
				SECOND_PERIOD_NULL);

		verify(personRepository, times(1)).findPersonByNameAndDateOfLastEdition(
				NAME_PERSON_TO_SEARCH.toLowerCase().trim(), FIRST_PERIOD_NULL, SECOND_PERIOD_NULL);
		verify(personRepository, times(0)).findSearchingNamePerson(NAME_PERSON_TO_SEARCH.toLowerCase().trim());
	}

	public void mockEmailsToSave() {
		emails = new ArrayList<>();
		emails.add(new Email(EMAIL_NAME_1));
		emails.add(new Email(EMAIL_NAME_2));
	}

	public void mockPersonToSave() {
		person = new Person(NAME, AGE, emails);
	}

	public void mockPersonSaved() {
		personSaved = new Person(ID_PERSON, NAME, AGE, REGISTRATION_DATE, emailsSaved);
	}

	public void mockEmailsSaved() {
		final Long ID_EMAIL_1 = 1L;
		final Long ID_EMAIL_2 = 2L;
		emailsSaved = new ArrayList<>();
		emailsSaved.add(new Email(ID_EMAIL_1, EMAIL_NAME_1));
		emailsSaved.add(new Email(ID_EMAIL_2, EMAIL_NAME_2));
	}

}