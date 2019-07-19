package com.apptestunitary.project.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;

import com.apptestunitary.AppTestUnitaryApplicationTests;
import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.model.Project;
import com.apptestunitary.service.PersonService;
import com.apptestunitary.service.ProjectService;

public class ProjectServiceSaveTest extends AppTestUnitaryApplicationTests {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private PersonService personService;

	private Person person1;
	private Person person2;
	private Person person3;
	private Person person4;
	private Person person5;
	private Person person6;

	@Test
	public void mustSaveProjects() {
		Project p1 = new Project("Project 1");
		Project p2 = new Project("Project 2");
		Project p3 = new Project("Project 3");
		Project p4 = new Project("Project 4");

		List<Project> projects = new ArrayList<>();
		projects.add(p1);
		projects.add(p2);
		projects.add(p3);
		projects.add(p4);

		List<Project> projectsSaved = projectService.save(projects);

		assertNotNull(projectsSaved);
		assertThat(projectsSaved.size(), is(projects.size()));
		projectsSaved.forEach(project -> {
			assertNotNull(project.getId());
			assertThat(project.getId() > 0);
			assertNotNull(project.getRegistrationDate());
			assertNotNull(project.getDateOfLastEdition());
		});

	}

	@Test
	public void mustSaveProjectsWithPeople() {
		getReady();
		String nameProject = "New Project";
		List<Person> people = Arrays.asList(person1, person2, person3, person4, person5, person6);
		Project project = new Project(nameProject, people);

		Project projectSaved = projectService.save(project);

		assertNotNull(projectSaved);
	}

	@Test(expected = TransactionSystemException.class)
	public void shouldNotSaveProjectWithValuesNull() {
		final Project PROJECT_WITH_VALUES_NULL = new Project();
		projectService.save(PROJECT_WITH_VALUES_NULL);
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

		person1 = personService.save(p1);
		person2 = personService.save(p2);
		person3 = personService.save(p3);
		person4 = personService.save(p4);
		person5 = personService.save(p5);
		person6 = personService.save(p6);
	}

}