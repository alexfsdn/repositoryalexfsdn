package com.apptestunitary.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apptestunitary.model.Person;
import com.apptestunitary.model.PersonProject;
import com.apptestunitary.model.Project;
import com.apptestunitary.repository.PersonProjectRepository;

@Service
public class PersonProjectService {

	@Autowired
	private PersonProjectRepository personProjectRepository;

	public PersonProjectService(PersonProjectRepository personProjectRepository) {
		super();
		this.personProjectRepository = personProjectRepository;
	}

	public void savePersonProject(Project project, List<Person> people) {
		removeByIdProject(project.getId());
		List<PersonProject> personProjects = new ArrayList<>();
		for (Person person : people) {
			PersonProject personProject = new PersonProject(person.getId(), project.getId());
			personProjects.add(personProject);
		}

		personProjectRepository.saveAll(personProjects);
	}

	public void savePersonProject(List<Project> projects) {
		for (Project project : projects) {
			savePersonProject(project, project.getPeople());
		}
	}

	public void removeByIdProject(Long idProject) {
		Optional<List<PersonProject>> personProjects = personProjectRepository.findByIdProject(idProject);

		if (personProjects.isPresent()) {
			personProjectRepository.deleteByIdProject(idProject);
		}
	}

	public Optional<List<PersonProject>> findByIdProject(Long idProject) {
		return personProjectRepository.findByIdProject(idProject);
	}
}