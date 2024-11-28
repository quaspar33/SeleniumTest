package com.web.test.tests;

import com.web.test.AbstractTest;
import com.web.test.page.QuestionnaireSecondPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class QuestionnaireSecondTest extends AbstractTest {
    QuestionnaireSecondPage questionnaireSecondPage;

    @BeforeClass
    @Override
    public void beforeClass() {
        super.beforeClass();
        questionnaireSecondPage = new QuestionnaireSecondPage(driver);
    }

    @Test
    public void questionnaireSecondTest() {
        questionnaireSecondPage.enterUnemployed();
        questionnaireSecondPage.nextPage();
    }
}
