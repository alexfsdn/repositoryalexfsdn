package com.apptestunitary.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.apptestunitary.util.PersonUtilTest;
import com.apptestunitary.util.PersonVOUtilTest;
import com.apptestunitary.util.ValidationPersonVOUtilTest;
import com.apptestunitary.util.ValidationUtilTest;

@RunWith(Suite.class)
@SuiteClasses({ PersonUtilTest.class, PersonVOUtilTest.class, ValidationPersonVOUtilTest.class,
		ValidationUtilTest.class })
public class UtilSuiteTest {

}
