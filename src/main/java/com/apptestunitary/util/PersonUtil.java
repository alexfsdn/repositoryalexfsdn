package com.apptestunitary.util;

import java.util.ArrayList;
import java.util.List;

import com.apptestunitary.model.Email;
import com.apptestunitary.model.Person;
import com.apptestunitary.vo.PersonVO;

public class PersonUtil {

	public static Person buildPerson(PersonVO personVO) {
		List<Email> emails = EmailVOUtil.buildEmails(personVO.getEmailsVO());
		if (personVO.getId() != null && personVO.getId() > 0) {
			return new Person(personVO.getId(), personVO.getNamePerson(), personVO.getAge(),
					personVO.getRegistrationDate(), emails);
		} else {
			return new Person(personVO.getNamePerson(), personVO.getAge(), emails);
		}
	}

	public static List<Person> buildPeople(List<PersonVO> peopleVO) {
		List<Person> people = new ArrayList<Person>();

		for (PersonVO personVO : peopleVO) {
			people.add(buildPerson(personVO));
		}

		return people;
	}
}