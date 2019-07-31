package com.apptestunitary.api.project.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.apptestunitary.AppTestUnitaryApplicationTests;
import com.apptestunitary.enums.DataFormatoEnum;
import com.apptestunitary.enums.url.ProjectURIEnum;
import com.apptestunitary.model.Project;
import com.apptestunitary.repository.PersonProjectRepository;
import com.apptestunitary.repository.ProjectRepository;
import com.apptestunitary.service.ProjectService;
import com.apptestunitary.vo.ProjectVO;

public class ProjectGetControllerTest extends AppTestUnitaryApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ProjectService projectService;

	private static final String NAME_TO_SEARCH = "ONE";
	private static final String NAME_TO_SEARCH_NOT_FOUND = "TWO";

	private static final String DATE_FOR_FIRST_PERIOD = "2018-01-01 00:00:11.763";
	private static final String DATE_FOR_SECOND_PERIOD = "2019-09-01 00:00:11.763";

	private static Timestamp FIRST_PERIOD = null;
	private static Timestamp SECOND_PERIOD = null;

	private static final String NAME_LABEL = "name";
	private static final String FIRST_PERIOD_LABEL = "firstPeriod";
	private static final String SECOND_PERIOD_LABEL = "secondPeriod";

	private Project projectSaved;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private PersonProjectRepository personProjectRepository;

	@Before
	public void setUp() {
		final Project PROJECT_ONE = new Project("Project One");
		projectSaved = projectService.save(PROJECT_ONE);
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

	@After
	public void end() {
		personProjectRepository.deleteAll();
		projectRepository.deleteAll();
	}

	@Test
	public void mustFindProjectById() throws URISyntaxException {

		final Long ID_PROJECT = projectSaved.getId();
		final URI uri = new URI(ProjectURIEnum.URL_PROJECT.getUrl());

		ResponseEntity<ProjectVO> result = restTemplate.getForEntity(uri + "" + ID_PROJECT, ProjectVO.class);
		ProjectVO projectVO = result.getBody();

		assertThat(HttpStatus.OK, is(result.getStatusCode()));
		assertNotNull(projectVO);
		assertThat(projectVO.getId(), is(ID_PROJECT));
	}

	@Test
	public void mustFindProjectByName() throws URISyntaxException {

		final URI uri = new URI(ProjectURIEnum.URL_GET_BY_NAME.getUrl());
		final HttpEntity<ProjectVO> projectVO = null;

		ResponseEntity<List<ProjectVO>> result = restTemplate.exchange(uri + "" + NAME_TO_SEARCH, HttpMethod.GET,
				projectVO, new ParameterizedTypeReference<List<ProjectVO>>() {
				});

		List<ProjectVO> projectsVOs = result.getBody();

		assertThat(HttpStatus.OK, is(result.getStatusCode()));
		assertNotNull(projectsVOs);
		projectsVOs.forEach(p -> {
			assertTrue(p.getName().toLowerCase().contains(NAME_TO_SEARCH.toLowerCase()));
			assertFalse(p.getName().toLowerCase().contains(NAME_TO_SEARCH_NOT_FOUND.toLowerCase()));
		});
	}

	@Test
	public void mustFindProjectByNameAndDateOfLastEdition() throws ParseException {

		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
				.fromUriString(ProjectURIEnum.URL_GET_BY_NAME_AND_DATE_OF_LAST_EDITION.getUrl());

		uriComponentsBuilder.queryParam(NAME_LABEL, NAME_TO_SEARCH);
		uriComponentsBuilder.queryParam(FIRST_PERIOD_LABEL, FIRST_PERIOD.getTime());
		uriComponentsBuilder.queryParam(SECOND_PERIOD_LABEL, SECOND_PERIOD.getTime());

		final URI uri = uriComponentsBuilder.build().toUri();

		final HttpEntity<ProjectVO> projectVO = null;

		ResponseEntity<List<ProjectVO>> result = restTemplate.exchange(uri, HttpMethod.GET, projectVO,
				new ParameterizedTypeReference<List<ProjectVO>>() {
				});

		List<ProjectVO> projectsVOs = result.getBody();

		assertThat(HttpStatus.OK, is(result.getStatusCode()));
		assertNotNull(projectsVOs);
		projectsVOs.forEach(p -> {
			assertTrue(p.getName().toLowerCase().contains(NAME_TO_SEARCH.toLowerCase()));
			assertFalse(p.getName().toLowerCase().contains(NAME_TO_SEARCH_NOT_FOUND.toLowerCase()));
		});
	}

	@Test
	public void mustFindProjectByNameAndDateOfRegistration() throws ParseException {

		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
				.fromUriString(ProjectURIEnum.URL_GET_BY_NAME_AND_REGISTRATION_DATE.getUrl());

		uriComponentsBuilder.queryParam(NAME_LABEL, NAME_TO_SEARCH);
		uriComponentsBuilder.queryParam(FIRST_PERIOD_LABEL, FIRST_PERIOD.getTime());
		uriComponentsBuilder.queryParam(SECOND_PERIOD_LABEL, SECOND_PERIOD.getTime());

		final URI uri = uriComponentsBuilder.build().toUri();
		final HttpEntity<ProjectVO> projectVO = null;

		ResponseEntity<List<ProjectVO>> result = restTemplate.exchange(uri, HttpMethod.GET, projectVO,
				new ParameterizedTypeReference<List<ProjectVO>>() {
				});

		List<ProjectVO> projectsVOs = result.getBody();

		assertThat(HttpStatus.OK, is(result.getStatusCode()));
		assertNotNull(projectsVOs);
		projectsVOs.forEach(p -> {
			assertTrue(p.getName().toLowerCase().contains(NAME_TO_SEARCH.toLowerCase()));
			assertFalse(p.getName().toLowerCase().contains(NAME_TO_SEARCH_NOT_FOUND.toLowerCase()));
		});
	}

}