package com.apptestunitary.api.project.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.client.RestClientException;

import com.apptestunitary.AppTestUnitaryApplicationTests;
import com.apptestunitary.enums.url.ProjectURIEnum;
import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.repository.PersonProjectRepository;
import com.apptestunitary.repository.ProjectRepository;
import com.apptestunitary.service.PersonService;
import com.apptestunitary.util.PersonVOUtil;
import com.apptestunitary.vo.PersonVO;
import com.apptestunitary.vo.ProjectVO;

public class ProjectSaveControllerTest extends AppTestUnitaryApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PersonService personService;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private PersonProjectRepository personProjectRepository;

	private URI uri;

	private PersonVO person1;
	private PersonVO person2;
	private PersonVO person3;
	private PersonVO person4;
	private PersonVO person5;
	private PersonVO person6;

	@Before
	public void setUp() throws URISyntaxException {
		getReady();
		uri = new URI(ProjectURIEnum.URL_PROJECT.getUrl());
	}

	@After
	public void end() {
		personProjectRepository.deleteAll();
		projectRepository.deleteAll();
	}

	@Test
	public void saveProject() throws RestClientException {

		final String NAME_PROJECT = "Project One API Rest";
		final ProjectVO PROJECT_VO = new ProjectVO(NAME_PROJECT);

		ResponseEntity<ProjectVO> result = restTemplate.postForEntity(uri, PROJECT_VO, ProjectVO.class);

		ProjectVO projectVOSaved = result.getBody();

		assertThat(HttpStatus.OK, is(result.getStatusCode()));
		assertThat(projectVOSaved.getName(), is(NAME_PROJECT));
	}

	@Test
	public void saveProjectInvalid() throws RestClientException, URISyntaxException {

		final ProjectVO PROJECT_VO = new ProjectVO("");

		ResponseEntity<ProjectVO> result = restTemplate.postForEntity(uri, PROJECT_VO, ProjectVO.class);

		assertEquals(result.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
	}

	@Test
	public void saveProjectWithPerson() throws RestClientException {

		final String NAME_PROJECT = "Project One API Rest";
		final List<PersonVO> PEOPLE_VO = Arrays.asList(person1, person2, person3, person4, person5, person6);
		final ProjectVO PROJECT_VO = new ProjectVO(NAME_PROJECT, PEOPLE_VO);

		ResponseEntity<ProjectVO> result = restTemplate.postForEntity(uri, PROJECT_VO, ProjectVO.class);

		ProjectVO projectVOSaved = result.getBody();

		assertThat(HttpStatus.OK, is(result.getStatusCode()));
		assertThat(PEOPLE_VO.size(), is(projectVOSaved.getPeopleVOs().size()));
	}

	@Test
	public void saveProjects() throws URISyntaxException {
		final URI URI = new URI(ProjectURIEnum.URL_PROJECT_SAVE_LIST.getUrl());
		final ProjectVO P1 = new ProjectVO("Project 1 API Rest");
		final ProjectVO P2 = new ProjectVO("Project 2 API Rest");
		final ProjectVO P3 = new ProjectVO("Project 3 API Rest");
		final ProjectVO P4 = new ProjectVO("Project 4 API Rest");

		List<ProjectVO> projects = new ArrayList<>();
		projects.add(P1);
		projects.add(P2);
		projects.add(P3);
		projects.add(P4);

		final HttpEntity<List<ProjectVO>> PROJECTS_VO = new HttpEntity<>(projects);

		ResponseEntity<List<ProjectVO>> result = restTemplate.exchange(URI, HttpMethod.POST, PROJECTS_VO,
				new ParameterizedTypeReference<List<ProjectVO>>() {
				});

		List<ProjectVO> projectsSaved = result.getBody();

		assertThat(HttpStatus.OK, is(result.getStatusCode()));
		assertNotNull(projectsSaved);
		assertThat(projects.size(), is(projectsSaved.size()));
		projectsSaved.forEach(project -> {
			assertNotNull(project.getId());
			assertThat(project.getId() > 0);
			assertNotNull(project.getRegistrationDate());
			assertNotNull(project.getDateOfLastEdition());
		});

	}

	private void getReady() {
		List<Email> emails1 = new ArrayList<>();
		emails1.add(new Email("alex@gmail.com"));
		emails1.add(new Email("alex2@gmail.com"));

		Person p1 = new Person("Alex", 21, emails1);

		List<Email> emails2 = new ArrayList<>();
		emails2.add(new Email("aline@gmail.com"));
		emails2.add(new Email("aline2@gmail.com"));
		emails2.add(new Email("aline3@gmail.com"));
		emails2.add(new Email("aline4@gmail.com"));

		Person p2 = new Person("Aline", 22, emails2);

		List<Email> emails3 = new ArrayList<>();
		emails3.add(new Email("alexandre@gmail.com"));
		emails3.add(new Email("alexandre2@gmail.com"));

		Person p3 = new Person("Alexandre", 23, emails3);

		List<Email> emails4 = new ArrayList<>();
		emails4.add(new Email("bruna@gmail.com"));
		emails4.add(new Email("bruna2@gmail.com"));

		Person p4 = new Person("Bruna", 24, emails4);

		List<Email> emails5 = new ArrayList<>();
		emails5.add(new Email("carlos@gmail.com"));

		Person p5 = new Person("Carlos", 25, emails5);

		List<Email> emails6 = new ArrayList<>();
		emails1.add(new Email("Daiane@gmail.com"));
		emails1.add(new Email("Daine2@gmail.com"));
		emails1.add(new Email("Daine3@gmail.com"));

		Person p6 = new Person("Daiane", 26, emails6);

		person1 = PersonVOUtil.buildPersonVO(personService.save(p1));
		person2 = PersonVOUtil.buildPersonVO(personService.save(p2));
		person3 = PersonVOUtil.buildPersonVO(personService.save(p3));
		person4 = PersonVOUtil.buildPersonVO(personService.save(p4));
		person5 = PersonVOUtil.buildPersonVO(personService.save(p5));
		person6 = PersonVOUtil.buildPersonVO(personService.save(p6));
	}

}