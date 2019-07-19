package com.apptestunitary.util;

import java.util.ArrayList;
import java.util.List;

import com.apptestunitary.model.Email;
import com.apptestunitary.vo.EmailVO;

public class EmailVOUtil {

	public static List<EmailVO> buildEmailVO(List<Email> emails) {
		List<EmailVO> emailsVO = new ArrayList<>();
		for (Email email : emails) {
			emailsVO.add(buildEmailVO(email));
		}

		return emailsVO;
	}

	private static EmailVO buildEmailVO(Email email) {
		EmailVO emailVO = new EmailVO();
		emailVO.setId(email.getId());
		emailVO.setEmailName(email.getEmailName());
		return emailVO;
	}

	public static List<Email> buildEmails(List<EmailVO> emailsVOs) {
		List<Email> emails = new ArrayList<>();
		for (EmailVO emailVO : emailsVOs) {
			emails.add(buildEmail(emailVO));
		}

		return emails;
	}

	private static Email buildEmail(EmailVO emailVO) {
		if (emailVO.getId() != null && emailVO.getId() > 0) {
			return new Email(emailVO.getId(), emailVO.getEmailName());
		} else {
			return new Email(emailVO.getEmailName());
		}

	}
}