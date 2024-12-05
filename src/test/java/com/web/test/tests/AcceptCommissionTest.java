package com.web.test.tests;

import com.web.test.AbstractTest;
import com.web.test.page.AcceptCommissionPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AcceptCommissionTest extends AbstractTest {
    private AcceptCommissionPage acceptCommissionPage;

    @BeforeClass
    @Override
    public void beforeClass() {
        super.beforeClass();
        acceptCommissionPage = new AcceptCommissionPage(driver);
    }

    @Test
    public void acceptCommissionTest() {
        acceptCommissionPage.clickCommission();
        acceptCommissionPage.clickAcceptCommission();
        acceptCommissionPage.setAgreements();
    }
}
