package com.apptestunitary.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apptestunitary.model.Project;
import com.apptestunitary.repository.ProjectRepository;

@Service
public class ProjectService {

	private ProjectRepository projectRepository;

	private PersonProjectService personProjectService;

	@Autowired
	public ProjectService(ProjectRepository projectRepository, PersonProjectService personProjectService) {
		super();
		this.projectRepository = projectRepository;
		this.personProjectService = personProjectService;
	}

	@Transactional
	public Project save(Project project) {
		Project projectSaved = projectRepository.save(project);
		if (project.getPeople() != null && project.getPeople().size() > 0) {
			personProjectService.savePersonProject(projectSaved, project.getPeople());
			
		}
		return projectSaved;
	}

	@Transactional
	public List<Project> save(List<Project> projects) {
		List<Project> projectsSaved = new ArrayList<>();

		for (Project project : projects) {
			projectsSaved.add(save(project));
		}

		return projectsSaved;
	}

	public Optional<Project> findProject(Long id) {
		return projectRepository.findById(id);
	}

	public Optional<List<Project>> findProjectByName(String name) {
		return projectRepository.findProjectByName(name.toLowerCase().trim());
	}

	public Optional<List<Project>> findProjectByNameAndDateOfLastEdition(String name, Timestamp firstPeriod,
			Timestamp secondPeriod) {

		if (firstPeriod == null || secondPeriod == null) {
			return findProjectByName(name);
		}

		return projectRepository.findProjectByNameAndDateOfLastEdition(name.toLowerCase().trim(), firstPeriod,
				secondPeriod);
	}

	public Optional<List<Project>> findProjectByNameAndRegistrationDate(String name, Timestamp firstPeriod,
			Timestamp secondPeriod) {
		if (firstPeriod == null || secondPeriod == null) {
			return findProjectByName(name);
		}

		return projectRepository.findProjectByNameAndRegistrationDate(name.toLowerCase().trim(), firstPeriod,
				secondPeriod);
	}

	@Transactional
	public void deleteById(Long id) {
		personProjectService.removeByIdProject(id);
		projectRepository.deleteById(id);
	}

	public void deleteById(List<Project> projectsSaved) {
		for (Project project : projectsSaved) {
			deleteById(project.getId());
		}

	}

}