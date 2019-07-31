package com.apptestunitary.project.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.apptestunitary.AppTests;
import com.apptestunitary.enums.DataFormatoEnum;
import com.apptestunitary.model.Project;
import com.apptestunitary.repository.PersonProjectRepository;
import com.apptestunitary.repository.ProjectRepository;
import com.apptestunitary.service.ProjectService;

public class ProjectServiceFindTest extends AppTests {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private PersonProjectRepository personProjectRepository;

	private static final String NAME_UPPERCASE = "ONE";
	private static final String NAME_LOWERCASE = "one";

	private static final String DATE_FOR_FIRST_PERIOD = "2018-01-01 00:00:11.763";
	private static final String DATE_FOR_SECOND_PERIOD = "2019-09-01 00:00:11.763";

	private static Timestamp FIRST_PERIOD = null;
	private static Timestamp SECOND_PERIOD = null;

	private Project projectSaved;

	@Before
	public void setUp() throws ParseException {
		Project projectOne = new Project("Project One");
		projectSaved = projectService.save(projectOne);

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
	public void mustFindProjectById() {
		final Long ID_PROJECT = projectSaved.getId();
		Project project = projectService.findProject(ID_PROJECT).get();

		assertNotNull(project);
		assertThat(projectSaved.getId(), is(project.getId()));
	}

	@Test
	public void mustFindProjectByNameEquallySmallAndTinyDigits() {
		List<Project> projectsUppercase = projectService.findProjectByName(NAME_UPPERCASE).get();
		List<Project> projectsLowerCase = projectService.findProjectByName(NAME_LOWERCASE).get();

		assertNotNull(projectsUppercase);
		assertNotNull(projectsLowerCase);
		assertThat(projectsUppercase.size(), is(projectsLowerCase.size()));
		projectsUppercase.forEach(p -> {
			assertTrue(p.getName().toLowerCase().contains(NAME_LOWERCASE.toLowerCase()));
		});

		projectsLowerCase.forEach(p -> {
			assertTrue(p.getName().toLowerCase().contains(NAME_LOWERCASE.toLowerCase()));
		});

	}

	@Test
	public void mustFindProjectByNameAndDateOfLastEdition() throws ParseException {
		List<Project> projects = projectService
				.findProjectByNameAndDateOfLastEdition(NAME_LOWERCASE, FIRST_PERIOD, SECOND_PERIOD).get();

		assertNotNull(projects);
		assertThat(projects.size() > 0);
		projects.forEach(p -> {
			assertTrue(p.getName().toLowerCase().contains(NAME_LOWERCASE.toLowerCase()));
		});
	}

	@Test
	public void mustFindProjectByNameAndDateOfRegistration() throws ParseException {
		List<Project> projects = projectService
				.findProjectByNameAndRegistrationDate(NAME_LOWERCASE, FIRST_PERIOD, SECOND_PERIOD).get();

		assertNotNull(projects);
		assertThat(projects.size() > 0);
		projects.forEach(p -> {
			assertTrue(p.getName().toLowerCase().contains(NAME_LOWERCASE.toLowerCase()));
		});
	}
}