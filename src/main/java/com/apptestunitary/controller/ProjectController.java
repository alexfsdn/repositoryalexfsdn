package com.apptestunitary.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apptestunitary.model.Project;
import com.apptestunitary.service.ProjectService;
import com.apptestunitary.util.ProjectUtil;
import com.apptestunitary.util.ProjectVOUtil;
import com.apptestunitary.util.ValidationProjectVOUtil;
import com.apptestunitary.util.ValidationUtil;
import com.apptestunitary.vo.ProjectVO;

@RestController
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@PostMapping(value = "/", produces = "application/json")
	public ResponseEntity<ProjectVO> save(@RequestBody ProjectVO projectVO) {
		if (!ValidationProjectVOUtil.projectIsValid((projectVO))) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		Project project = ProjectUtil.buildProject(projectVO);
		Project projectSaved = this.projectService.save(project);
		ProjectVO projectVOSaved = ProjectVOUtil.buildProjectVO(projectSaved);

		return new ResponseEntity<ProjectVO>(projectVOSaved, HttpStatus.OK);
	}

	@PutMapping(value = "/", produces = "application/json")
	public ResponseEntity<ProjectVO> update(@RequestBody ProjectVO projectVO) {
		if (!ValidationProjectVOUtil.projectIsValidToUpdate((projectVO))) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		Project project = ProjectUtil.buildProject(projectVO);
		Project projectSaved = this.projectService.save(project);
		ProjectVO projectVOSaved = ProjectVOUtil.buildProjectVO(projectSaved);

		return new ResponseEntity<ProjectVO>(projectVOSaved, HttpStatus.OK);
	}

	@PostMapping(value = "/saveList/", produces = "application/json")
	public ResponseEntity<List<ProjectVO>> saveList(@RequestBody List<ProjectVO> projectsVOs) {
		if (!ValidationProjectVOUtil.projectIsValid((projectsVOs))) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		List<Project> projects = ProjectUtil.buildProject(projectsVOs);
		List<Project> projectsSaved = this.projectService.save(projects);
		List<ProjectVO> projectVOSaved = ProjectVOUtil.buildProjectVO(projectsSaved);

		return new ResponseEntity<List<ProjectVO>>(projectVOSaved, HttpStatus.OK);
	}

	@PutMapping(value = "/updateList/", produces = "application/json")
	public ResponseEntity<List<ProjectVO>> updateList(@RequestBody List<ProjectVO> projectsVOs) {
		if (!ValidationProjectVOUtil.projectIsValidToUpdate((projectsVOs))) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		List<Project> projects = ProjectUtil.buildProject(projectsVOs);
		List<Project> projectsSaved = this.projectService.save(projects);
		List<ProjectVO> projectVOSaved = ProjectVOUtil.buildProjectVO(projectsSaved);

		return new ResponseEntity<List<ProjectVO>>(projectVOSaved, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<ProjectVO> findById(@PathVariable("id") Long id) {
		if (ValidationUtil.isNegative(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		Optional<Project> projectFinded = this.projectService.findProject(id);

		if (!projectFinded.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		ProjectVO projectVO = ProjectVOUtil.buildProjectVO(projectFinded.get());

		return new ResponseEntity<ProjectVO>(projectVO, HttpStatus.OK);
	}

	@GetMapping(value = "/nameProject/{name}", produces = "application/json")
	public ResponseEntity<List<ProjectVO>> findByName(@PathVariable("name") String name) {
		if (ValidationUtil.isEmpty(name)) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		Optional<List<Project>> projectFinded = this.projectService.findProjectByName(name);

		if (!projectFinded.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		List<ProjectVO> projectsVOs = ProjectVOUtil.buildProjectVO(projectFinded.get());

		return new ResponseEntity<List<ProjectVO>>(projectsVOs, HttpStatus.OK);
	}

	@GetMapping(value = "/nameAndDateOfLastEdition/", produces = "application/json")
	public ResponseEntity<List<ProjectVO>> nameAndDateOfLastEdition(@RequestParam("name") String name,
			@RequestParam("firstPeriod") long firstPeriod, @RequestParam("secondPeriod") long secondPeriod) {

		Timestamp first = null;
		Timestamp second = null;
		if (firstPeriod < 0 && secondPeriod < 0) {
			first = new Timestamp(firstPeriod);
			second = new Timestamp(secondPeriod);

			if (ValidationUtil.isValidTimestamp(first) || ValidationUtil.isValidTimestamp(second)) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
		}

		Optional<List<Project>> projectsFinded = this.projectService.findProjectByNameAndDateOfLastEdition(name, first,
				second);

		if (!projectsFinded.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		List<ProjectVO> projectsVOs = ProjectVOUtil.buildProjectVO(projectsFinded.get());

		return new ResponseEntity<List<ProjectVO>>(projectsVOs, HttpStatus.OK);
	}

	@GetMapping(value = "/nameAndRegistrationDate/", produces = "application/json")
	public ResponseEntity<List<ProjectVO>> nameAndRegistrationDate(@RequestParam("name") String name,
			@RequestParam("firstPeriod") long firstPeriod, @RequestParam("secondPeriod") long secondPeriod) {

		Timestamp first = null;
		Timestamp second = null;
		if (firstPeriod < 0 && secondPeriod < 0) {
			first = new Timestamp(firstPeriod);
			second = new Timestamp(secondPeriod);

			if (ValidationUtil.isValidTimestamp(first) || ValidationUtil.isValidTimestamp(second)) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
		}

		Optional<List<Project>> projectsFinded = this.projectService.findProjectByNameAndRegistrationDate(name, first,
				second);

		if (!projectsFinded.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		List<ProjectVO> projectsVOs = ProjectVOUtil.buildProjectVO(projectsFinded.get());

		return new ResponseEntity<List<ProjectVO>>(projectsVOs, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<String> deleteById(@PathVariable("id") Long id) throws Exception {
		if (ValidationUtil.isNegative(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		Optional<Project> projectFinded = projectService.findProject(id);

		if (!projectFinded.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		this.projectService.deleteById(id);

		return new ResponseEntity<>(HttpStatus.OK);
	}
}