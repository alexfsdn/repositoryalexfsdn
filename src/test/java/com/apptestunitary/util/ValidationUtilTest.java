package com.apptestunitary.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;

import org.junit.Test;

import com.apptestunitary.util.ValidationUtil;

public class ValidationUtilTest {

	@Test
	public void shouldNotEmptyValue() {
		boolean result = ValidationUtil.isEmpty(null);
		boolean result2 = ValidationUtil.isEmpty("");

		assertTrue(result);
		assertTrue(result2);
	}

	@Test
	public void allowFullValue() {
		boolean result = ValidationUtil.isEmpty("Test Unitary");

		assertFalse(result);
	}

	@Test
	public void shouldNotNegativeValue() {
		boolean result = ValidationUtil.isNegative(0L);
		boolean result2 = ValidationUtil.isNegative(-1L);
		boolean result3 = ValidationUtil.isNegative(Arrays.asList(1L, 2L, 0L, -3L));

		assertTrue(result);
		assertTrue(result2);
		assertTrue(result3);
	}

	@Test
	public void allowPostiveValue() {
		boolean result = ValidationUtil.isNegative(1L);
		boolean result2 = ValidationUtil.isNegative(Arrays.asList(1L, 2L, 3L));

		assertFalse(result);
		assertFalse(result2);
	}

	@Test
	public void shouldNotTimestempWithNullValue() {
		Timestamp time = new Timestamp(0);

		boolean result = ValidationUtil.isValidTimestamp(null);
		boolean result2 = ValidationUtil.isValidTimestamp(time);

		assertFalse(result);
		assertFalse(result2);
	}

	@Test
	public void allowPostiveValueInisValidTimestamp() {
		Timestamp time = new Timestamp(Calendar.getInstance().getTimeInMillis());
		boolean result = ValidationUtil.isValidTimestamp(time);

		assertTrue(result);
	}

}
