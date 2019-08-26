package com.apptestunitary.util;

import org.springframework.util.StringUtils;

import com.apptestunitary.vo.PersonVO;

public class ValidationPersonVOUtil {

	public static boolean personIsValid(PersonVO personVO) {
		if (personVO == null) {
			return false;
		}
		if (personVO.getEmailsVO() == null || personVO.getEmailsVO().size() <= 0) {
			return false;
		}
		if (StringUtils.isEmpty(personVO.getNamePerson())) {
			return false;
		}
		if (personVO.getAge() <= 0) {
			return false;
		}

		if (!ValidationEmailVOUtil.validEmail(personVO.getEmailsVO())) {
			return false;
		}

		return true;
	}

	public static boolean personIsValidToUpdate(PersonVO personVO) {
		if (personVO.getId() == null || personVO.getId() <= 0) {
			return false;
		}

		if (!personIsValid(personVO)) {
			return false;
		}

		if (!ValidationUtil.isValidTimestamp(personVO.getRegistrationDate())) {
			return false;
		}

		return true;
	}

}