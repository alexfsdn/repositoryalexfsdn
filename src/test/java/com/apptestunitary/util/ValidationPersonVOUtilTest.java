package com.apptestunitary.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.apptestunitary.vo.EmailVO;
import com.apptestunitary.vo.PersonVO;

public class ValidationPersonVOUtilTest {

	private static final Long ID_PERSON = 1L;
	private static final Timestamp REGISTRATION_DATE = new Timestamp(Calendar.getInstance().getTimeInMillis());

	private static final String EMAIL_NAME_1 = "alex@gmail.com";
	private static final String EMAIL_NAME_2 = "alex2@gmail.com";

	private static final String NAME = "Alex Francisco";
	private static final int AGE = 27;

	private List<EmailVO> emailsVOs;

	@Before
	public void setUp() {
		emailsVOs = new ArrayList<>();
	}

	@Test
	public void personIsValidShouldNotEmptyPerson() {
		final PersonVO PERSON_VO = null;
		boolean result = ValidationPersonVOUtil.personIsValid(PERSON_VO);

		assertFalse(result);
	}

	@Test
	public void personIsValidMustAllowWithFullValue() {
		emailsVOs.add(new EmailVO(EMAIL_NAME_1));
		emailsVOs.add(new EmailVO(EMAIL_NAME_2));
		final PersonVO PERSON_VO = new PersonVO(NAME, AGE, emailsVOs);

		boolean result = ValidationPersonVOUtil.personIsValid(PERSON_VO);

		assertTrue(result);
	}

	@Test
	public void personIsValidShouldNotNameNull() {
		emailsVOs.add(new EmailVO(EMAIL_NAME_1));
		emailsVOs.add(new EmailVO(EMAIL_NAME_2));

		final String NAME_NULL = null;
		final String NAME_CLEAN = "";

		PersonVO personVO = null;
		personVO = new PersonVO(NAME_NULL, AGE, emailsVOs);
		boolean result = ValidationPersonVOUtil.personIsValid(personVO);

		personVO = new PersonVO(NAME_CLEAN, AGE, emailsVOs);
		boolean result2 = ValidationPersonVOUtil.personIsValid(personVO);

		assertFalse(result);
		assertFalse(result2);
	}

	@Test
	public void personIsValidShouldNotAgeLessThanOne() {
		final int AGE_WITH_0 = 0;
		final int AGE_WITH_NEGATIVE_VALUE = -1;

		emailsVOs.add(new EmailVO(EMAIL_NAME_1));
		emailsVOs.add(new EmailVO(EMAIL_NAME_2));

		PersonVO personVO = null;
		personVO = new PersonVO(NAME, AGE_WITH_0, emailsVOs);
		boolean result = ValidationPersonVOUtil.personIsValid(personVO);

		personVO = new PersonVO(NAME, AGE_WITH_NEGATIVE_VALUE, emailsVOs);
		boolean result2 = ValidationPersonVOUtil.personIsValid(personVO);

		assertFalse(result);
		assertFalse(result2);
	}

	@Test
	public void personIsValidShouldNotEmailsNull() {
		emailsVOs.add(new EmailVO());
		emailsVOs.add(new EmailVO(EMAIL_NAME_2));

		PersonVO personVO = null;
		personVO = new PersonVO(NAME, AGE, emailsVOs);
		boolean result = ValidationPersonVOUtil.personIsValid(personVO);

		personVO = new PersonVO(NAME, AGE, emailsVOs);
		boolean result2 = ValidationPersonVOUtil.personIsValid(personVO);

		assertFalse(result);
		assertFalse(result2);
	}

	@Test
	public void personIsValidToUpdateShouldNotNullValues() {
		final List<EmailVO> EMAILS_VOS_NULL = null;
		final Timestamp REGISTRATION_DATE_NULL = null;
		final String NAME_NULL = null;
		final Long ID_PERSON_WITH_0 = 0L;
		final int AGE_WITH_0 = 0;
		final int AGE_WITH_NEGATIVE_VALUE = -1;

		PersonVO personVO = null;

		personVO = new PersonVO(NAME, AGE, EMAILS_VOS_NULL);
		final boolean RESULT = ValidationPersonVOUtil.personIsValidToUpdate(personVO);

		personVO = new PersonVO(ID_PERSON, NAME, AGE, REGISTRATION_DATE_NULL, EMAILS_VOS_NULL);
		final boolean RESULT2 = ValidationPersonVOUtil.personIsValidToUpdate(personVO);

		personVO = new PersonVO(ID_PERSON, NAME_NULL, AGE, REGISTRATION_DATE, EMAILS_VOS_NULL);
		final boolean RESULT3 = ValidationPersonVOUtil.personIsValidToUpdate(personVO);

		personVO = new PersonVO(ID_PERSON_WITH_0, NAME, AGE, REGISTRATION_DATE, EMAILS_VOS_NULL);
		final boolean RESULT4 = ValidationPersonVOUtil.personIsValidToUpdate(personVO);

		personVO = new PersonVO(ID_PERSON, NAME, AGE_WITH_0, REGISTRATION_DATE, EMAILS_VOS_NULL);
		final boolean RESULT5 = ValidationPersonVOUtil.personIsValidToUpdate(personVO);

		personVO = new PersonVO(ID_PERSON, NAME, AGE_WITH_NEGATIVE_VALUE, REGISTRATION_DATE, EMAILS_VOS_NULL);
		final boolean RESULT6 = ValidationPersonVOUtil.personIsValidToUpdate(personVO);

		assertFalse(RESULT);
		assertFalse(RESULT2);
		assertFalse(RESULT3);
		assertFalse(RESULT4);
		assertFalse(RESULT5);
		assertFalse(RESULT6);
	}

	@Test
	public void personIsValidToUpdateMustAllowPersonWithIdPerson() {
		emailsVOs.add(new EmailVO(EMAIL_NAME_1));
		emailsVOs.add(new EmailVO(EMAIL_NAME_2));

		PersonVO personVO = null;
		personVO = new PersonVO(ID_PERSON, NAME, AGE, REGISTRATION_DATE, emailsVOs);

		boolean result = ValidationPersonVOUtil.personIsValidToUpdate(personVO);

		assertTrue(result);
	}
}