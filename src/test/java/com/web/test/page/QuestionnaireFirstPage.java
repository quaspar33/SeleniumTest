package com.web.test.page;

import com.web.test.AbstractPage;
import com.web.test.JsonHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class QuestionnaireFirstPage extends AbstractPage {
    JsonHandler jsonHandler;
    private int birthYear;

    public QuestionnaireFirstPage(WebDriver driver) {
        super(driver);
        jsonHandler = new JsonHandler("questionnaire_first.json");
        birthYear = currentYear - 18;
        System.out.println("Rozpoczynam wypełnianie pierwszej strony kwesionariusza!");
    }

    public void enterQuestionnaire() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.css-175oi2r:nth-child(7) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1)"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.css-175oi2r:nth-child(5) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1)"))).click();
    }

    public void enterBirthDate() {
        WebElement dateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Data urodzenia']")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1];", dateInput, String.format("%d-%02d-%02d", birthYear, currentMonth, currentDay));
        js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", dateInput);
    }

    public void enterNationality() {
        new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@aria-label='Obywatelstwo']")))).selectByValue("145");
    }

    public void enterPesel() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='PESEL']"))).sendKeys(apiHandler.GET(String.format("https://generator.avris.it/api/PL/pesel?birthdate=%d-%02d-%02d&gender=m", birthYear, currentMonth, currentDay)));
    }

    public void enterName() {
        driver.findElement(By.xpath("//input[@placeholder='Imię']")).sendKeys("Test");
    }

    public void enterSurname() {
        driver.findElement(By.xpath("//input[@placeholder='Nazwisko']")).sendKeys("Selenium");
    }

    public void enterEmail() {
        driver.findElement(By.xpath("//input[@placeholder='E-mail']")).sendKeys(jsonHandler.getStrFromJson("email"));
    }

    public void enterPhoneNumber() {
        driver.findElement(By.xpath("//input[@placeholder='123456789']")).sendKeys(jsonHandler.getStrFromJson("phoneNumber"));
    }
}
