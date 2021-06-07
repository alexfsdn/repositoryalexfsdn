package com.apptestunitary.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.apptestunitary.api.project.controller.ProjectDeleteControllerTest;
import com.apptestunitary.api.project.controller.ProjectGetControllerTest;
import com.apptestunitary.api.project.controller.ProjectSaveControllerTest;
import com.apptestunitary.api.project.controller.ProjectUpdateControllerTest;
import com.apptestunitary.mockito.project.service.PersonProjectServiceTest;
import com.apptestunitary.mockito.project.service.ProjectServiceTest;
import com.apptestunitary.project.service.ProjectServiceDeleteTest;
import com.apptestunitary.project.service.ProjectServiceFindTest;
import com.apptestunitary.project.service.ProjectServiceSaveTest;
import com.apptestunitary.project.service.ProjectServiceUpdateTest;

@RunWith(Suite.class)
@SuiteClasses({ ProjectServiceTest.class, ProjectServiceSaveTest.class, ProjectServiceDeleteTest.class,
		ProjectServiceFindTest.class, ProjectServiceUpdateTest.class, ProjectDeleteControllerTest.class,
		ProjectGetControllerTest.class, ProjectSaveControllerTest.class, ProjectUpdateControllerTest.class,
		PersonProjectServiceTest.class })
public class ProjectSuiteTest {

}
