package com.apptestunitary.util;

import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.apptestunitary.vo.EmailVO;

public class ValidationEmailVOUtil {

	public static boolean validEmail(EmailVO emailVO) {
		if (emailIsEmpty(emailVO)) {
			return false;
		}

		if (!isValidEmailAddress(emailVO.getEmailName())) {
			return false;
		}

		return true;
	}

	public static boolean validEmail(List<EmailVO> emailsVOs) {
		for (EmailVO emailVO : emailsVOs) {
			if (!validEmail(emailVO)) {
				return false;
			}
		}

		return true;
	}

	private static boolean emailIsEmpty(EmailVO emailVO) {
		if (emailVO == null) {
			return true;
		}
		if (ValidationUtil.isEmpty(emailVO.getEmailName())) {
			return true;
		}
		return false;
	}

	private static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

}