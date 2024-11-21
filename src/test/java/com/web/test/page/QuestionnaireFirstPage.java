package com.web.test.page;

import com.web.test.AbstractPage;
import com.web.test.JsonHandler;
import org.openqa.selenium.WebDriver;

public class QuestionnaireFirstPage extends AbstractPage {
    JsonHandler jsonHandler;
    private int birthYear;

    public QuestionnaireFirstPage(WebDriver driver) {
        super(driver);
        jsonHandler = new JsonHandler("questionnaire_first.json");
        birthYear = currentYear;
        System.out.println("Rozpoczynam wypełnianie pierwszej strony kwesionariusza!");
    }


}
