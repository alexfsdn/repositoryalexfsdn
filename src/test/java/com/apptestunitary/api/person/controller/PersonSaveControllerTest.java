package com.apptestunitary.api.person.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import com.apptestunitary.AppTestUnitaryApplicationTests;
import com.apptestunitary.enums.url.BaseUrlEnum;
import com.apptestunitary.enums.url.PersonURIEnum;
import com.apptestunitary.model.Person;
import com.apptestunitary.vo.EmailVO;
import com.apptestunitary.vo.PersonVO;

public class PersonSaveControllerTest extends AppTestUnitaryApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	private static final String NAME = "Alex Francisco";
	private static final int AGE = 27;
	private List<EmailVO> emailsVOs;

	private URI uri;

	@Before
	public void setUp() throws URISyntaxException {
		emailsVOs = new ArrayList<>();
		uri = new URI(BaseUrlEnum.URL_BASE.getUrl() + PersonURIEnum.URL_PERSON.getUrl());
	}

	@Test
	public void savePersonWithEmails() throws RestClientException {

		emailsVOs.add(new EmailVO("alex@gmail.com"));
		emailsVOs.add(new EmailVO("alex2@gmail.com"));
		PersonVO personVO = new PersonVO(NAME, AGE, emailsVOs);

		// ACT
		ResponseEntity<PersonVO> result = restTemplate.postForEntity(uri, personVO, PersonVO.class);

		PersonVO personVOSaved = result.getBody();

		// ASSERT
		assertThat(HttpStatus.OK, is(result.getStatusCode()));
		assertNotNull(personVOSaved.getEmailsVO());
		assertThat(personVOSaved.getNamePerson(), is(NAME));
		assertThat(personVOSaved.getAge(), is(AGE));
		assertNotNull(personVOSaved.getEmailsVO().get(0));
		assertThat(personVOSaved.getEmailsVO().get(0).getEmailName(), is(emailsVOs.get(0).getEmailName()));
		assertThat(personVOSaved.getEmailsVO().get(1).getEmailName(), is(emailsVOs.get(1).getEmailName()));
	}

	@Test
	public void savePersonInvalid() throws RestClientException, URISyntaxException {

		emailsVOs.add(new EmailVO("alexfsdn@gmail.com"));
		emailsVOs.add(new EmailVO(""));
		PersonVO personVO = new PersonVO(NAME, AGE, emailsVOs);

		ResponseEntity<Person> result = restTemplate.postForEntity(uri, personVO, Person.class);

		assertEquals(result.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
	}

}