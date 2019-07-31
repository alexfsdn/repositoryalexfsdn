package com.apptestunitary.api.project.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.apptestunitary.AppTestUnitaryApplicationTests;
import com.apptestunitary.enums.url.ProjectURIEnum;
import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.model.Project;
import com.apptestunitary.repository.PersonProjectRepository;
import com.apptestunitary.repository.ProjectRepository;
import com.apptestunitary.service.PersonService;
import com.apptestunitary.service.ProjectService;
import com.apptestunitary.util.PersonVOUtil;
import com.apptestunitary.util.ProjectVOUtil;
import com.apptestunitary.vo.PersonVO;
import com.apptestunitary.vo.ProjectVO;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL, replace = Replace.NONE)
public class ProjectUpdateControllerTest extends AppTestUnitaryApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private PersonService personService;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private PersonProjectRepository personProjectRepository;

	private URI uri;

	private ProjectVO projectOne;
	private ProjectVO projectTwo;
	private ProjectVO projectThree;
	private ProjectVO projectFour;

	private PersonVO person1;
	private PersonVO person2;
	private PersonVO person3;
	private PersonVO person4;
	private PersonVO person5;
	private PersonVO person6;

	private List<PersonVO> peopleToProjectOne;
	private List<PersonVO> peopleToProjectTwo;
	private List<PersonVO> peopleToProjectThree;
	private List<PersonVO> peopleToProjectFour;

	private List<Project> projectsSaved;

	@Before
	public void setUp() throws ParseException, URISyntaxException {
		getReady();

		projectOne = ProjectVOUtil.buildProjectVO(projectsSaved.get(0));
		projectTwo = ProjectVOUtil.buildProjectVO(projectsSaved.get(1));
		projectThree = ProjectVOUtil.buildProjectVO(projectsSaved.get(2));
		projectFour = ProjectVOUtil.buildProjectVO(projectsSaved.get(3));

		peopleToProjectOne = Arrays.asList(person1, person2, person3);
		peopleToProjectTwo = Arrays.asList(person2, person3, person4);
		peopleToProjectThree = Arrays.asList(person3, person4, person5);
		peopleToProjectFour = Arrays.asList(person4, person5, person6);

		uri = new URI(ProjectURIEnum.URL_PROJECT.getUrl());
	}

	@After
	public void end() {
		personProjectRepository.deleteAll();
		projectRepository.deleteAll();
	}

	@Test
	public void mustAddPeopleInProjectsAlreayExisting() throws URISyntaxException {

		final URI URI = new URI(ProjectURIEnum.URL_PROJECT_UPDATE_LIST.getUrl());
		projectOne = new ProjectVO(projectOne.getId(), projectOne.getName(), projectOne.getRegistrationDate(),
				peopleToProjectOne);

		projectTwo = new ProjectVO(projectTwo.getId(), projectTwo.getName(), projectTwo.getRegistrationDate(),
				peopleToProjectTwo);

		projectThree = new ProjectVO(projectThree.getId(), projectThree.getName(), projectThree.getRegistrationDate(),
				peopleToProjectThree);

		projectFour = new ProjectVO(projectFour.getId(), projectFour.getName(), projectFour.getRegistrationDate(),
				peopleToProjectFour);

		List<ProjectVO> projects = new ArrayList<>();
		projects.add(projectOne);
		projects.add(projectTwo);
		projects.add(projectThree);
		projects.add(projectFour);

		final HttpEntity<List<ProjectVO>> PROJECTS_VOS = new HttpEntity<>(projects);

		ResponseEntity<List<ProjectVO>> result = restTemplate.exchange(URI, HttpMethod.PUT, PROJECTS_VOS,
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
			assertNotNull(project.getPeopleVOs());
		});

	}

	@Test
	public void mustUpdateProjectsAddNewPeople() {

		final Long ID_PROJECT_ONE = projectOne.getId();
		List<PersonVO> peopleToProjectOneWithNewPerson = new ArrayList<>();
		peopleToProjectOneWithNewPerson.addAll(peopleToProjectOne);
		peopleToProjectOneWithNewPerson.add(person4);

		ProjectVO projectOneToSavedVO = new ProjectVO(projectOne.getId(), projectOne.getName(),
				projectOne.getRegistrationDate(), peopleToProjectOneWithNewPerson);

		restTemplate.put(uri, projectOneToSavedVO);

		Optional<Project> projectsSaved = projectService.findProject(ID_PROJECT_ONE);

		assertNotNull(projectsSaved);
	}

	@Test
	public void updateProjectsAlterPeopleToOtherProject() {

		final Long ID_PROJECT_TWO = projectTwo.getId();
		List<PersonVO> peopleToProjectOneWithNewPerson = new ArrayList<>();
		peopleToProjectOneWithNewPerson = Arrays.asList(person1, person2, person3, person4, person5);

		ProjectVO projectTwoVO = new ProjectVO(projectTwo.getId(), projectTwo.getName(),
				projectTwo.getRegistrationDate(), peopleToProjectOneWithNewPerson);

		restTemplate.put(uri, projectTwoVO);

		Optional<Project> projectsSaved = projectService.findProject(ID_PROJECT_TWO);

		assertNotNull(projectsSaved);
	}

	private void getReady() {
		Project projectOne = new Project("projectOne");
		Project projectTwo = new Project("projectTwo");
		Project projectThree = new Project("projectThree");
		Project projectFour = new Project("projectFour");

		List<Project> projects = new ArrayList<>();
		projects.add(projectOne);
		projects.add(projectTwo);
		projects.add(projectThree);
		projects.add(projectFour);

		projectsSaved = projectService.save(projects);

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