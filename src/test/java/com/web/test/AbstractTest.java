package com.web.test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;

public abstract class AbstractTest {
    public static WebDriver driver;

    @BeforeClass
    public void beforeClass() {
        driver = BaseTest.driver;
    }
}
