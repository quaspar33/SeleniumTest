package com.web.test.tests;

import com.web.test.AbstractTest;
import com.web.test.page.RegisterPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RegisterTest extends AbstractTest {
    private RegisterPage registerPage;

    @BeforeClass
    @Override
    public void beforeClass() {
        super.beforeClass();
        registerPage = new RegisterPage(driver);
    }

    @Test
    public void registerTest() {
        registerPage.enterCreateAccount();
        registerPage.enterPhoneNumber();
        registerPage.enterPostalCode();
        registerPage.clickAcceptConditionButton();
        registerPage.createAccount();
    }
}
