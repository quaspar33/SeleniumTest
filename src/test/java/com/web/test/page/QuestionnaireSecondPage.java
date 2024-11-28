package com.web.test.page;

import com.web.test.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class QuestionnaireSecondPage extends AbstractPage {
    public QuestionnaireSecondPage(WebDriver driver) {
        super(driver);
    }

    public void enterUnemployed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Bezrobotny/a')]"))).click();
    }

    public void nextPage() {
        implicitWait(1000);
        driver.findElement(By.xpath("//*[contains(text(), 'Dalej')]")).click();
    }
}
