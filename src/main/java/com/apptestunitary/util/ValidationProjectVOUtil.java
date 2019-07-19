package com.apptestunitary.util;

import java.util.List;

import com.apptestunitary.vo.ProjectVO;

public class ValidationProjectVOUtil {

	public static boolean projectIsValid(ProjectVO projectVO) {
		if (projectVO == null) {
			return false;
		}

		if (ValidationUtil.isEmpty(projectVO.getName())) {
			return false;
		}

		return true;
	}

	public static boolean projectIsValid(List<ProjectVO> projectsVOs) {
		for (ProjectVO projectVO : projectsVOs) {
			if (!projectIsValid(projectVO)) {
				return false;
			}
		}

		return true;
	}

	public static boolean projectIsValidToUpdate(ProjectVO projectVO) {
		if (projectVO.getId() == null || projectVO.getId() <= 0) {
			return false;
		}

		if (!projectIsValid(projectVO)) {
			return false;
		}

		if (!ValidationUtil.isValidTimestamp(projectVO.getRegistrationDate())) {
			return false;
		}

		return true;
	}

	public static boolean projectIsValidToUpdate(List<ProjectVO> projectsVOs) {
		for (ProjectVO projectVO : projectsVOs) {
			if (!projectIsValidToUpdate(projectVO)) {
				return false;
			}
		}

		return true;
	}

}
