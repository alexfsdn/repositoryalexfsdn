package com.apptestunitary.project.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.apptestunitary.AppTestUnitaryApplicationTests;
import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.model.Project;
import com.apptestunitary.service.PersonService;
import com.apptestunitary.service.ProjectService;

public class ProjectServiceUpdateTest extends AppTestUnitaryApplicationTests {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private PersonService personService;

	private Project projectOne;
	private Project projectTwo;
	private Project projectThree;
	private Project projectFour;

	private Person person1;
	private Person person2;
	private Person person3;
	private Person person4;
	private Person person5;
	private Person person6;

	private List<Person> peopleToProjectOne;
	private List<Person> peopleToProjectTwo;
	private List<Person> peopleToProjectThree;
	private List<Person> peopleToProjectFour;

	private List<Project> projectsSaved;

	@Before
	public void setUp() {
		getReady();
		projectOne = projectsSaved.get(0);
		projectTwo = projectsSaved.get(1);
		projectThree = projectsSaved.get(2);
		projectFour = projectsSaved.get(3);

		peopleToProjectOne = Arrays.asList(person1, person2, person3);
		peopleToProjectTwo = Arrays.asList(person2, person3, person4);
		peopleToProjectThree = Arrays.asList(person3, person4, person5);
		peopleToProjectFour = Arrays.asList(person4, person5, person6);	
	}

	@Test
	public void mustAddPeopleInProjectsAlreayExisting() {
		projectOne = new Project(projectOne.getId(), projectOne.getName(), projectOne.getRegistrationDate(),
				peopleToProjectOne);

		projectTwo = new Project(projectTwo.getId(), projectTwo.getName(), projectTwo.getRegistrationDate(),
				peopleToProjectTwo);

		projectThree = new Project(projectThree.getId(), projectThree.getName(), projectThree.getRegistrationDate(),
				peopleToProjectThree);

		projectFour = new Project(projectFour.getId(), projectFour.getName(), projectFour.getRegistrationDate(),
				peopleToProjectFour);

		List<Project> projects = new ArrayList<>();
		projects.add(projectOne);
		projects.add(projectTwo);
		projects.add(projectThree);
		projects.add(projectFour);

		List<Project> projectsSaved = projectService.save(projects);

		assertNotNull(projectsSaved);
		assertThat(projectsSaved.size(), is(projects.size()));
		projectsSaved.forEach(project -> {
			assertNotNull(project.getId());
			assertThat(project.getId() > 0);
			assertNotNull(project.getRegistrationDate());
			assertNotNull(project.getDateOfLastEdition());
			assertNotNull(project.getPeople());
		});
	}

	@Test
	public void mustUpdateProjectsAddingNewPeople() {
		List<Person> peopleToProjectOneWithNewPerson = new ArrayList<>();
		peopleToProjectOneWithNewPerson.addAll(peopleToProjectOne);
		peopleToProjectOneWithNewPerson.add(person4);

		projectOne = new Project(projectOne.getId(), projectOne.getName(), projectOne.getRegistrationDate(),
				peopleToProjectOneWithNewPerson);

		Project projectsSaved = projectService.save(projectOne);

		assertNotNull(projectsSaved);
		assertThat(projectsSaved.getPeople().size(), is(peopleToProjectOneWithNewPerson.size()));
	}

	@Test
	public void mustUpdateProjectsAlterPeopleToOtherProject() {
		List<Person> peopleToProjectOneWithNewPerson = new ArrayList<>();
		peopleToProjectOneWithNewPerson = Arrays.asList(person1, person2, person3, person4, person5);

		projectTwo = new Project(projectTwo.getId(), projectTwo.getName(), projectTwo.getRegistrationDate(),
				peopleToProjectOneWithNewPerson);

		Project projectsSaved = projectService.save(projectTwo);

		assertNotNull(projectsSaved);
		assertThat(projectsSaved.getPeople().size(), is(peopleToProjectOneWithNewPerson.size()));
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