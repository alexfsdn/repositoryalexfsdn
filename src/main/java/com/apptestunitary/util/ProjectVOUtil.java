package com.apptestunitary.util;

import java.util.ArrayList;
import java.util.List;

import com.apptestunitary.model.Project;
import com.apptestunitary.vo.ProjectVO;

public class ProjectVOUtil {

	public static List<ProjectVO> buildListPersonVO(List<Project> projects) {
		List<ProjectVO> projectsVOs = new ArrayList<ProjectVO>();

		for (Project project : projects) {
			projectsVOs.add(buildProjectVO(project));
		}

		return projectsVOs;
	}

	public static ProjectVO buildProjectVO(Project project) {
		return new ProjectVO(project.getId(), project.getName(), project.getRegistrationDate(),
				project.getDateOfLastEdition(), PersonVOUtil.buildPeopleVO(project.getPeople()));
	}

	public static List<ProjectVO> buildProjectVO(List<Project> projects) {
		List<ProjectVO> projectsVOs = new ArrayList<ProjectVO>();

		for (Project project : projects) {
			projectsVOs.add(buildProjectVO(project));
		}
		return projectsVOs;
	}
}