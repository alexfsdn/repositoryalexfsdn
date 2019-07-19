package com.apptestunitary.api.person.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import com.apptestunitary.AppTestUnitaryApplicationTests;
import com.apptestunitary.enums.DataFormatoEnum;
import com.apptestunitary.enums.url.BaseUrlEnum;
import com.apptestunitary.enums.url.PersonURIEnum;
import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.service.PersonService;
import com.apptestunitary.vo.PersonVO;

public class PersonGetControllerTest extends AppTestUnitaryApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PersonService personService;

	private static final String NAME_LABEL = "namePerson";
	private static final String FIRST_PERIOD_LABEL = "firstPeriod";
	private static final String SECOND_PERIOD_LABEL = "secondPeriod";

	private static final String NAME_TO_SEARCH = "Ale";

	private static final String DATE_FOR_FIRST_PERIOD = "2018-01-01 00:00:11.763";
	private static final String DATE_FOR_SECOND_PERIOD = "2020-07-13 00:00:11.763";

	private static Timestamp FIRST_PERIOD = null;
	private static Timestamp SECOND_PERIOD = null;

	private Person personSaved;

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

	@Before
	public void setUp2() throws ParseException {
		SimpleDateFormat simpleDateFormatForFirstPeriod = new SimpleDateFormat(
				DataFormatoEnum.YEAR_DAY_HOUR_MINUTES_SECOND_SSS.getLabel());
		SimpleDateFormat simpleDateFormatForSecondPeriod = new SimpleDateFormat(
				DataFormatoEnum.YEAR_DAY_HOUR_MINUTES_SECOND_SSS.getLabel());

		FIRST_PERIOD = new Timestamp(simpleDateFormatForFirstPeriod.parse(DATE_FOR_FIRST_PERIOD).getTime());
		SECOND_PERIOD = new Timestamp(simpleDateFormatForSecondPeriod.parse(DATE_FOR_SECOND_PERIOD).getTime());
	}

	@Test
	public void mustFindPersonById() throws RestClientException, URISyntaxException {

		final Long ID_PERSON = personSaved.getId();
		final URI URI = new URI(BaseUrlEnum.URL_BASE.getUrl() + PersonURIEnum.URL_PERSON.getUrl());

		ResponseEntity<PersonVO> result = restTemplate.getForEntity(URI + "" + ID_PERSON, PersonVO.class);

		PersonVO personSaved = result.getBody();

		assertNotNull(personSaved);
		assertThat(personSaved.getId(), is(ID_PERSON));
	}

	@Test
	public void mustFindPersonByNamePerson() throws RestClientException, URISyntaxException {

		final List<Person> PEOPLE = personService.findSearchingNamePerson(NAME_TO_SEARCH).get();
		final URI URI = new URI(BaseUrlEnum.URL_BASE.getUrl() + PersonURIEnum.URL_GET_BY_NAME_PERSON.getUrl());

		HttpEntity<PersonVO> personVO = null;

		ResponseEntity<List<PersonVO>> result = restTemplate.exchange(URI + "" + NAME_TO_SEARCH, HttpMethod.GET,
				personVO, new ParameterizedTypeReference<List<PersonVO>>() {
				});

		List<PersonVO> peopleVO = result.getBody();

		assertNotNull(peopleVO);
		assertThat(peopleVO.size(), is(PEOPLE.size()));
	}

	@Test
	public void mustFindPersonByNamePersonAndDateOfLastEdition()
			throws RestClientException, URISyntaxException, ParseException {

		final List<Person> PEOPLE = personService
				.findPersonByNameAndDateOfLastEdition(NAME_TO_SEARCH, FIRST_PERIOD, SECOND_PERIOD).get();

		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(
				BaseUrlEnum.URL_BASE.getUrl() + PersonURIEnum.URL_GET_BY_NAME_PERSON_AND_DATE_OF_LAST_EDITION.getUrl());

		uriComponentsBuilder.queryParam(NAME_LABEL, NAME_TO_SEARCH);
		uriComponentsBuilder.queryParam(FIRST_PERIOD_LABEL, FIRST_PERIOD.getTime());
		uriComponentsBuilder.queryParam(SECOND_PERIOD_LABEL, SECOND_PERIOD.getTime());

		final URI URI = uriComponentsBuilder.build().toUri();
		final HttpEntity<PersonVO> PERSON_VO = null;

		ResponseEntity<List<PersonVO>> result = restTemplate.exchange(URI, HttpMethod.GET, PERSON_VO,
				new ParameterizedTypeReference<List<PersonVO>>() {
				});

		List<PersonVO> peopleVO = result.getBody();

		assertThat(HttpStatus.OK, is(result.getStatusCode()));
		assertNotNull(peopleVO);
		assertThat(PEOPLE.size(), is(peopleVO.size()));
	}

	@Test
	public void mustFindPersonByNamePersonAndRegistrationDate()
			throws RestClientException, URISyntaxException, ParseException {

		final List<Person> PEOPLE = personService
				.findPersonByNameAndRegistrationDate(NAME_TO_SEARCH, FIRST_PERIOD, SECOND_PERIOD).get();

		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(
				BaseUrlEnum.URL_BASE.getUrl() + PersonURIEnum.URL_GET_BY_NAME_PERSON_AND_REGISTRATION_DATE.getUrl());

		uriComponentsBuilder.queryParam(NAME_LABEL, NAME_TO_SEARCH);
		uriComponentsBuilder.queryParam(FIRST_PERIOD_LABEL, FIRST_PERIOD.getTime());
		uriComponentsBuilder.queryParam(SECOND_PERIOD_LABEL, SECOND_PERIOD.getTime());

		final URI URI = uriComponentsBuilder.build().toUri();

		HttpEntity<PersonVO> PERSON_VO = null;

		ResponseEntity<List<PersonVO>> result = restTemplate.exchange(URI, HttpMethod.GET, PERSON_VO,
				new ParameterizedTypeReference<List<PersonVO>>() {
				});

		List<PersonVO> peopleVO = result.getBody();

		assertThat(HttpStatus.OK, is(result.getStatusCode()));
		assertNotNull(peopleVO);
		assertThat(PEOPLE.size(), is(peopleVO.size()));
	}

}