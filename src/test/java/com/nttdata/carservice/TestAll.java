package com.nttdata.carservice;

import com.nttdata.carservice.controller.AutomobileControllerTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({
        AutomobileDataStorageTest.class,
        AutomobileTest.class,
        AutomobileControllerTest.class
})
public class TestAll {
}
