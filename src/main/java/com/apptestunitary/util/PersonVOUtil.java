package com.apptestunitary.util;

import java.util.ArrayList;
import java.util.List;

import com.apptestunitary.model.Person;
import com.apptestunitary.vo.PersonVO;

public class PersonVOUtil {

	public static List<PersonVO> buildPeopleVO(List<Person> people) {
		List<PersonVO> peopleVO = new ArrayList<PersonVO>();

		for (Person person : people) {
			peopleVO.add(buildPersonVO(person));
		}

		return peopleVO;
	}

	public static PersonVO buildPersonVO(Person person) {
		return new PersonVO(person.getId(), person.getNamePerson(), person.getAge(), person.getRegistrationDate(),
				person.getDateOfLastEdition(), EmailVOUtil.buildEmailVO(person.getEmails()));
	}

}