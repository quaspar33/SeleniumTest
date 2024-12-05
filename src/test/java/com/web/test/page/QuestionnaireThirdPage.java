package com.web.test.page;

import com.web.test.AbstractPage;
import com.web.test.MavenRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class QuestionnaireThirdPage extends AbstractPage {
    MavenRunner mavenRunner;

    public QuestionnaireThirdPage(WebDriver driver) {
        super(driver);
        mavenRunner = new MavenRunner();
    }

    public void acceptVerify() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Potwierdzam osobiście w biurze Tikrow ul. Kraszewskiego 32/4, 05-803 Pruszków (po uprzednim umówieniu telefonicznym)')]"))).click();
    }

    public void endQuestionnaire() {
        implicitWait(2000);
        driver.findElement(By.xpath("//*[contains(text(), 'Zakończ')]")).click();
        mavenRunner.run();
    }
}
