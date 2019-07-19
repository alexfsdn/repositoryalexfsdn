package com.apptestunitary.util;

import java.util.ArrayList;
import java.util.List;

import com.apptestunitary.model.Person;
import com.apptestunitary.model.Project;
import com.apptestunitary.vo.PersonVO;
import com.apptestunitary.vo.ProjectVO;

public class ProjectUtil {

	public static Project buildProject(ProjectVO projectVO) {
		List<Person> people = buildPeople(projectVO.getPeopleVOs());
		if (projectVO.getId() != null && projectVO.getId() > 0) {
			return new Project(projectVO.getId(), projectVO.getName(), projectVO.getRegistrationDate(), people);
		} else {
			return new Project(projectVO.getName(), people);
		}
	}

	private static Person buildPerson(PersonVO personVO) {
		return new Person(personVO.getId());
	}

	private static List<Person> buildPeople(List<PersonVO> peopleVO) {
		List<Person> people = new ArrayList<Person>();

		for (PersonVO personVO : peopleVO) {
			people.add(buildPerson(personVO));
		}

		return people;
	}

	public static List<Project> buildProject(List<ProjectVO> projectsVOs) {
		List<Project> projects = new ArrayList<>();

		for (ProjectVO projectVO : projectsVOs) {
			projects.add(buildProject(projectVO));
		}

		return projects;
	}
}