package com.apptestunitary.mockito.project.service;

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

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.TransactionSystemException;

import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.model.Project;
import com.apptestunitary.repository.ProjectRepository;
import com.apptestunitary.service.PersonProjectService;
import com.apptestunitary.service.ProjectService;

public class ProjectServiceTest {

	private ProjectRepository projectRepository;

	private PersonProjectService personProjectService;

	private static final Long ID_PROJECT_1 = 1L;
	private static final Long ID_PROJECT_2 = 2L;
	private static final Long ID_PROJECT_3 = 3L;
	private static final Long ID_PROJECT_4 = 3L;

	private static final String NAME_PROJECT_1 = "Project One";
	private static final String NAME_PROJECT_2 = "Project Two";
	private static final String NAME_PROJECT_3 = "Project Three";
	private static final String NAME_PROJECT_4 = "Project Four";

	private static final Timestamp REGISTRATION_DATE = new Timestamp(Calendar.getInstance().getTimeInMillis());

	private Project project;
	private Project projectSaved;

	private List<Project> projects;
	private List<Project> projectsSaved;

	private Project projectWithouPeople;
	private Project projectSavedWithouPeople;

	private List<Project> projectsWithouPeople;
	private List<Project> projectsSavedWithouPeople;

	private List<Person> people;

	public static final Project PROJECT_WITH_VALUES_NULL = new Project();

	@Before
	public void setUp() {
		mockPeople();
		mockProjectToSaveWithouPeople();
		mockProjectSavedWithouPeople();
		mockListProjectsToSaveWithouPeople();
		mockListProjectsSavedWithouPeople();

		mockProjectToSave();
		mockProjectSaved();
		mockListProjectsToSave();
		mockListProjectsSaved();

		Project projectOne = projects.get(0);
		Project projectTwo = projects.get(1);
		Project projectThree = projects.get(2);
		Project projectFour = projects.get(3);

		Project projectOneSaved = projectsSaved.get(0);
		Project projectTwoSaved = projectsSaved.get(1);
		Project projectThreeSaved = projectsSaved.get(2);
		Project projectFourSaved = projectsSaved.get(3);

		projectRepository = mock(ProjectRepository.class);
		when(projectRepository.save(project)).thenReturn(projectSaved);
		when(projectRepository.save(projectOne)).thenReturn(projectOneSaved);
		when(projectRepository.save(projectTwo)).thenReturn(projectTwoSaved);
		when(projectRepository.save(projectThree)).thenReturn(projectThreeSaved);
		when(projectRepository.save(projectFour)).thenReturn(projectFourSaved);

		when(projectRepository.save(projectWithouPeople)).thenReturn(projectSavedWithouPeople);
		when(projectRepository.save(projectOne)).thenReturn(projectOneSaved);
		when(projectRepository.save(projectTwo)).thenReturn(projectTwoSaved);
		when(projectRepository.save(projectThree)).thenReturn(projectThreeSaved);
		when(projectRepository.save(projectFour)).thenReturn(projectFourSaved);

		when(projectRepository.save(PROJECT_WITH_VALUES_NULL)).thenThrow(TransactionSystemException.class);

		personProjectService = mock(PersonProjectService.class);
	}

	@Test(expected = TransactionSystemException.class)
	public void shouldNotSaveProjectWithValuesNull() {
		new ProjectService(projectRepository, personProjectService).save(PROJECT_WITH_VALUES_NULL);

		verify(projectRepository, times(1)).save(any());
		verify(personProjectService, times(0)).savePersonProject(any(), any());
	}

	@Test
	public void mustSaveProjectWithoutPeople() {
		new ProjectService(projectRepository, personProjectService).save(projectWithouPeople);

		verify(projectRepository, times(1)).save(any());
		verify(personProjectService, times(0)).savePersonProject(any(), any());
	}

	@Test
	public void mustSaveProjectWithPeople() {
		new ProjectService(projectRepository, personProjectService).save(project);

		verify(projectRepository, times(1)).save(any());
		verify(personProjectService, times(1)).savePersonProject(any(), any());
	}

	@Test
	public void mustSaveListProjecstWithoutPeople() {
		new ProjectService(projectRepository, personProjectService).save(projectsWithouPeople);

		verify(projectRepository, times(4)).save(any());
		verify(personProjectService, times(0)).savePersonProject(any(), any());
	}

	@Test
	public void mustSaveListProjecstWithPeople() {
		new ProjectService(projectRepository, personProjectService).save(projects);

		verify(projectRepository, times(4)).save(any());
		verify(personProjectService, times(4)).savePersonProject(any(), any());
	}

	@Test
	public void shouldOnlyPassInFindProjectByNameWithValuesFirstPeriodAndSecondPeriodNullInFindPersonByNameAndDateOfLastEdition() {
		final Timestamp FIRST_PERIOD_NULL = null;
		final Timestamp SECOND_PERIOD_NULL = null;
		final String NAME_PERSON_TO_SEARCH = "Ale";

		new ProjectService(projectRepository, personProjectService)
				.findProjectByNameAndDateOfLastEdition(NAME_PERSON_TO_SEARCH, FIRST_PERIOD_NULL, SECOND_PERIOD_NULL);

		verify(projectRepository, times(0)).findProjectByNameAndDateOfLastEdition(
				NAME_PERSON_TO_SEARCH.toLowerCase().trim(), FIRST_PERIOD_NULL, SECOND_PERIOD_NULL);
		verify(projectRepository, times(1)).findProjectByName(NAME_PERSON_TO_SEARCH.toLowerCase().trim());
	}

	@Test
	public void shouldOnlyPassInFindProjectByNameAndDateOfLastEditionWithValuesFirstPeriodAndSecondPeriod() {

		final Timestamp FIRST_PERIOD = new Timestamp(Calendar.getInstance().getTimeInMillis());
		final Timestamp SECOND_PERIOD = new Timestamp(Calendar.getInstance().getTimeInMillis());
		final String NAME_PROJECT_TO_SEARCH = "Ale";

		new ProjectService(projectRepository, personProjectService)
				.findProjectByNameAndDateOfLastEdition(NAME_PROJECT_TO_SEARCH, FIRST_PERIOD, SECOND_PERIOD);

		verify(projectRepository, times(1)).findProjectByNameAndDateOfLastEdition(
				NAME_PROJECT_TO_SEARCH.toLowerCase().trim(), FIRST_PERIOD, SECOND_PERIOD);
		verify(projectRepository, times(0)).findProjectByName(NAME_PROJECT_TO_SEARCH.toLowerCase().trim());
	}

	@Test
	public void shouldOnlyPassInFindProjectByNameWithValuesFirstPeriodAndSecondPeriodNullInFindPersonByNameAndRegistrationDate() {
		final Timestamp FIRST_PERIOD_NULL = null;
		final Timestamp SECOND_PERIOD_NULL = null;
		final String NAME_PERSON_TO_SEARCH = "Ale";

		new ProjectService(projectRepository, personProjectService)
				.findProjectByNameAndRegistrationDate(NAME_PERSON_TO_SEARCH, FIRST_PERIOD_NULL, SECOND_PERIOD_NULL);

		verify(projectRepository, times(0)).findProjectByNameAndRegistrationDate(
				NAME_PERSON_TO_SEARCH.toLowerCase().trim(), FIRST_PERIOD_NULL, SECOND_PERIOD_NULL);
		verify(projectRepository, times(1)).findProjectByName(NAME_PERSON_TO_SEARCH.toLowerCase().trim());
	}

	@Test
	public void shouldOnlyPassInFindProjectByNameAndRegistrationDateWithValuesFirstPeriodAndSecondPeriodFull() {

		final Timestamp FIRST_PERIOD = new Timestamp(Calendar.getInstance().getTimeInMillis());
		final Timestamp SECOND_PERIOD = new Timestamp(Calendar.getInstance().getTimeInMillis());
		final String NAME_PERSON_TO_SEARCH = "Ale";

		new ProjectService(projectRepository, personProjectService)
				.findProjectByNameAndRegistrationDate(NAME_PERSON_TO_SEARCH, FIRST_PERIOD, SECOND_PERIOD);

		verify(projectRepository, times(1)).findProjectByNameAndRegistrationDate(
				NAME_PERSON_TO_SEARCH.toLowerCase().trim(), FIRST_PERIOD, SECOND_PERIOD);
		verify(projectRepository, times(0)).findProjectByName(NAME_PERSON_TO_SEARCH.toLowerCase().trim());
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

	private void mockListProjectsToSaveWithouPeople() {
		final List<Person> PEOPLE_NULL = null;

		Project p1 = new Project(NAME_PROJECT_1, PEOPLE_NULL);
		Project p2 = new Project(NAME_PROJECT_2, PEOPLE_NULL);
		Project p3 = new Project(NAME_PROJECT_3, PEOPLE_NULL);
		Project p4 = new Project(NAME_PROJECT_4, PEOPLE_NULL);

		projectsWithouPeople = new ArrayList<>();
		projectsWithouPeople.add(p1);
		projectsWithouPeople.add(p2);
		projectsWithouPeople.add(p3);
		projectsWithouPeople.add(p4);
	}

	public void mockListProjectsSavedWithouPeople() {
		final List<Person> PEOPLE_NULL = null;

		Project p1 = new Project(ID_PROJECT_1, NAME_PROJECT_1, PEOPLE_NULL);
		Project p2 = new Project(ID_PROJECT_2, NAME_PROJECT_2, PEOPLE_NULL);
		Project p3 = new Project(ID_PROJECT_3, NAME_PROJECT_3, PEOPLE_NULL);
		Project p4 = new Project(ID_PROJECT_4, NAME_PROJECT_4, PEOPLE_NULL);

		projectsSavedWithouPeople = new ArrayList<>();
		projectsSavedWithouPeople.add(p1);
		projectsSavedWithouPeople.add(p2);
		projectsSavedWithouPeople.add(p3);
		projectsSavedWithouPeople.add(p4);
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

	private void mockProjectSavedWithouPeople() {
		final List<Person> PEOPLE_NULL = null;
		projectSavedWithouPeople = new Project(ID_PROJECT_1, NAME_PROJECT_1, REGISTRATION_DATE, PEOPLE_NULL);
	}

	private void mockProjectToSaveWithouPeople() {
		final List<Person> PEOPLE_NULL = null;
		projectWithouPeople = new Project(NAME_PROJECT_1, PEOPLE_NULL);
	}

}
