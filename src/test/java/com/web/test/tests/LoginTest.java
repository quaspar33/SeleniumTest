package com.web.test.tests;

import com.web.test.AbstractTest;
import com.web.test.BaseTest;
import com.web.test.page.LoginPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest extends AbstractTest {
    private LoginPage loginPage;

    @BeforeClass
    @Override
    public void beforeClass() {
        super.beforeClass();
        loginPage = new LoginPage(driver);
    }

    @Test
    public void loginTest() {
        loginPage.enterLogin();
        loginPage.enterPassword(BaseTest.getRegisterTime());
        loginPage.clickLogin();
    }
}
