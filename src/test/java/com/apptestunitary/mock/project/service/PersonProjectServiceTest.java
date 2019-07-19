package com.apptestunitary.mock.project.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.model.PersonProject;
import com.apptestunitary.model.Project;
import com.apptestunitary.repository.PersonProjectRepository;
import com.apptestunitary.service.PersonProjectService;

public class PersonProjectServiceTest {

	private PersonProjectRepository personProjectRepository;

	private static final Long ID_PROJECT_1 = 1L;
	private static final Long ID_PROJECT_2 = 2L;
	private static final Long ID_PROJECT_3 = 3L;
	private static final Long ID_PROJECT_4 = 3L;

	private static final String NAME_PROJECT_1 = "Project One";
	private static final String NAME_PROJECT_2 = "Project Two";
	private static final String NAME_PROJECT_3 = "Project Three";
	private static final String NAME_PROJECT_4 = "Project Four";

	private static final Timestamp REGISTRATION_DATE = new Timestamp(Calendar.getInstance().getTimeInMillis());

	private Project projectSaved;
	private Project project;

	private List<Project> projects;
	private List<Project> projectsSaved;

	private List<Person> people;

	public static final Project PROJECT_WITH_VALUES_NULL = new Project();

	@Before
	public void setUp() {
		mockPeople();

		mockProjectToSave();
		mockProjectSaved();
		mockListProjectsToSave();
		mockListProjectsSaved();

		PersonProject personProject = new PersonProject(1L, 1L);
		PersonProject personProject1 = new PersonProject(2L, 1L);
		PersonProject personProject2 = new PersonProject(3L, 1L);
		List<PersonProject> personProjects = Arrays.asList(personProject, personProject1, personProject2);

		Optional<List<PersonProject>> personProjectsOptional = Optional.of(personProjects);
		personProjectRepository = mock(PersonProjectRepository.class);
		when(personProjectRepository.findByIdProject(ID_PROJECT_1)).thenReturn(personProjectsOptional);
		when(personProjectRepository.findByIdProject(ID_PROJECT_2)).thenReturn(personProjectsOptional);
		when(personProjectRepository.findByIdProject(ID_PROJECT_3)).thenReturn(personProjectsOptional);
		when(personProjectRepository.findByIdProject(ID_PROJECT_4)).thenReturn(personProjectsOptional);
	}

	@Test
	public void mustSaveListPersonProject() {
		new PersonProjectService(personProjectRepository).savePersonProject(projectsSaved);

		verify(personProjectRepository, times(4)).deleteByIdProject(any());
		verify(personProjectRepository, times(4)).saveAll(any());
		verify(personProjectRepository, times(4)).findByIdProject(any());
	}

	@Test
	public void mustSavePersonProject() {
		new PersonProjectService(personProjectRepository).savePersonProject(project, people);

		verify(personProjectRepository, times(0)).deleteByIdProject(any());
		verify(personProjectRepository, times(1)).saveAll(any());
		verify(personProjectRepository, times(1)).findByIdProject(any());
	}

	@Test
	public void mustSavePersonProjectWithProjectExisting() {
		new PersonProjectService(personProjectRepository).savePersonProject(projectSaved, people);

		verify(personProjectRepository, times(1)).deleteByIdProject(any());
		verify(personProjectRepository, times(1)).saveAll(any());
		verify(personProjectRepository, times(1)).findByIdProject(any());
	}

	private void mockListProjectsToSave() {
		Project p1 = new Project(NAME_PROJECT_1, people);
		Project p2 = new Project(NAME_PROJECT_2, people);
		Project p3 = new Project(NAME_PROJECT_3, people);
		Project p4 = new Project(NAME_PROJECT_4, people);

		projects = new ArrayList<>();
		projects.add(p1);
		projects.add(p2);
		projects.add(p3);
		projects.add(p4);
	}

	private void mockListProjectsSaved() {
		Project p1 = new Project(ID_PROJECT_1, NAME_PROJECT_1, people);
		Project p2 = new Project(ID_PROJECT_2, NAME_PROJECT_2, people);
		Project p3 = new Project(ID_PROJECT_3, NAME_PROJECT_3, people);
		Project p4 = new Project(ID_PROJECT_4, NAME_PROJECT_4, people);

		projectsSaved = new ArrayList<>();
		projectsSaved.add(p1);
		projectsSaved.add(p2);
		projectsSaved.add(p3);
		projectsSaved.add(p4);
	}

	private void mockPeople() {
		people = Arrays.asList(mockPerson1(), mockPerson2(), mockPerson3());
	}

	private Person mockPerson1() {
		final Long ID_PERSON = 1L;
		final Timestamp REGISTRATION_DATE = new Timestamp(Calendar.getInstance().getTimeInMillis());
		final String NAME = "Alex Francisco";
		final int AGE = 27;
		final List<Email> emails = null;

		return new Person(ID_PERSON, NAME, AGE, REGISTRATION_DATE, emails);
	}

	private Person mockPerson2() {
		final Long ID_PERSON = 2L;
		final Timestamp REGISTRATION_DATE = new Timestamp(Calendar.getInstance().getTimeInMillis());
		final String NAME = "Bruna Letícia";
		final int AGE = 28;
		final List<Email> emails = null;

		return new Person(ID_PERSON, NAME, AGE, REGISTRATION_DATE, emails);
	}

	private Person mockPerson3() {
		final Long ID_PERSON = 3L;
		final Timestamp REGISTRATION_DATE = new Timestamp(Calendar.getInstance().getTimeInMillis());
		final String NAME = "Carol Lima";
		final int AGE = 29;
		final List<Email> emails = null;

		return new Person(ID_PERSON, NAME, AGE, REGISTRATION_DATE, emails);
	}

	private void mockProjectSaved() {
		projectSaved = new Project(ID_PROJECT_1, NAME_PROJECT_1, REGISTRATION_DATE, people);
	}

	private void mockProjectToSave() {
		project = new Project(NAME_PROJECT_1, people);
	}

}
