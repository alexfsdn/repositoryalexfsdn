package com.apptestunitary.api.project.controller;

import static org.junit.Assert.assertFalse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.apptestunitary.AppTestUnitaryApplicationTests;
import com.apptestunitary.enums.url.BaseUrlEnum;
import com.apptestunitary.enums.url.ProjectURIEnum;
import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.model.PersonProject;
import com.apptestunitary.model.Project;
import com.apptestunitary.service.PersonProjectService;
import com.apptestunitary.service.PersonService;
import com.apptestunitary.service.ProjectService;

public class ProjectDeleteControllerTest extends AppTestUnitaryApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private PersonService personService;

	@Autowired
	private PersonProjectService personProjectService;

	private Person person1;
	private Person person2;
	private Person person3;
	private Person person4;
	private Person person5;
	private Person person6;

	private Project projectSaved;

	@Before
	public void setUp() {
		final String NAME_PROJECT = "New Project to be deleted";
		getReady();

		final List<Person> PEOPLE = Arrays.asList(person1, person2, person3, person4, person5, person6);
		final Project PROJECT = new Project(NAME_PROJECT, PEOPLE);
		projectSaved = projectService.save(PROJECT);
	}

	@Test
	public void mustDeletePersonByIdProject() throws URISyntaxException {

		final Long ID_PROJECT = projectSaved.getId();
		final URI uri = new URI(BaseUrlEnum.URL_BASE.getUrl() + ProjectURIEnum.URL_PROJECT.getUrl());

		restTemplate.delete(uri + "" + ID_PROJECT);

		Optional<Project> projectRemoved = projectService.findProject(ID_PROJECT);
		Optional<List<PersonProject>> personProjects = personProjectService.findByIdProject(ID_PROJECT);

		assertFalse(projectRemoved.isPresent());
		assertFalse(personProjects.isPresent());
	}

	private void getReady() {
		List<Email> emails1 = new ArrayList<>();
		emails1.add(new Email("alex@gmail.com"));
		emails1.add(new Email("alex2@gmail.com"));

		person1 = new Person("Alex", 21, emails1);

		List<Email> emails2 = new ArrayList<>();
		emails2.add(new Email("aline@gmail.com"));
		emails2.add(new Email("aline2@gmail.com"));
		emails2.add(new Email("aline3@gmail.com"));
		emails2.add(new Email("aline4@gmail.com"));

		person2 = new Person("Aline", 22, emails2);

		List<Email> emails3 = new ArrayList<>();
		emails3.add(new Email("alexandre@gmail.com"));
		emails3.add(new Email("alexandre2@gmail.com"));

		person3 = new Person("Alexandre", 23, emails3);

		List<Email> emails4 = new ArrayList<>();
		emails4.add(new Email("bruna@gmail.com"));
		emails4.add(new Email("bruna2@gmail.com"));

		person4 = new Person("Bruna", 24, emails4);

		List<Email> emails5 = new ArrayList<>();
		emails5.add(new Email("carlos@gmail.com"));

		person5 = new Person("Carlos", 25, emails5);

		List<Email> emails6 = new ArrayList<>();
		emails1.add(new Email("Daiane@gmail.com"));
		emails1.add(new Email("Daine2@gmail.com"));
		emails1.add(new Email("Daine3@gmail.com"));

		person6 = new Person("Daiane", 26, emails6);

		person1 = personService.save(person1);
		person2 = personService.save(person2);
		person3 = personService.save(person3);
		person4 = personService.save(person4);
		person5 = personService.save(person5);
		person6 = personService.save(person6);

	}

}