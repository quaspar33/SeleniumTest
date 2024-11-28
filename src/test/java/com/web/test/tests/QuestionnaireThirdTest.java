package com.web.test.tests;

import com.web.test.AbstractTest;
import com.web.test.page.QuestionnaireThirdPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class QuestionnaireThirdTest extends AbstractTest {
    QuestionnaireThirdPage questionnaireThirdPage;

    @BeforeClass
    @Override
    public void beforeClass() {
        super.beforeClass();
        questionnaireThirdPage = new QuestionnaireThirdPage(driver);
    }

    @Test
    public void questionnaireThirdTest() {
        questionnaireThirdPage.acceptVerify();
        questionnaireThirdPage.endQuestionnaire();
    }
}
