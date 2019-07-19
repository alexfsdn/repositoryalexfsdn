package com.apptestunitary;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppTestUnitaryApplicationTests.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = AppTestUnitaryApplication.class)
public class AppTestUnitaryApplicationTests {

}
