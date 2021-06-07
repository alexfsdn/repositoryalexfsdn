package com.apptestunitary.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.apptestunitary.api.person.controller.PersonControllerTest;
import com.apptestunitary.api.person.controller.PersonDeleteControllerTest;
import com.apptestunitary.api.person.controller.PersonGetControllerTest;
import com.apptestunitary.api.person.controller.PersonPutControllerTest;
import com.apptestunitary.api.person.controller.PersonSaveControllerTest;
import com.apptestunitary.mockito.person.service.PersonServiceTest;
import com.apptestunitary.person.repository.PersonRepositoryTest;
import com.apptestunitary.person.service.PersonServiceDeleteTest;
import com.apptestunitary.person.service.PersonServiceFindTest;
import com.apptestunitary.person.service.PersonServiceSaveTest;
import com.apptestunitary.person.service.PersonServiceUpdateTest;

@RunWith(Suite.class)
@SuiteClasses({ PersonServiceTest.class, PersonServiceSaveTest.class, PersonServiceDeleteTest.class,
		PersonServiceFindTest.class, PersonServiceUpdateTest.class, PersonControllerTest.class,
		PersonDeleteControllerTest.class, PersonGetControllerTest.class, PersonSaveControllerTest.class,
		PersonPutControllerTest.class, PersonRepositoryTest.class })
public class PersonSuiteTest {

}
