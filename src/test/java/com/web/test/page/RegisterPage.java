package com.web.test.page;

import com.web.test.AbstractPage;
import com.web.test.JsonHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends AbstractPage {
    JsonHandler jsonHandler;

    public RegisterPage(WebDriver driver) {
        super(driver);
        jsonHandler = new JsonHandler("register.json");
        System.out.println("Rozpoczynam test rejetsracji!");
    }

    public void enterCreateAccount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".css-175oi2r:nth-child(5) .css-1jxf684"))).click();
    }

    public void enterPhoneNumber() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Numer telefonu komórkowego']"))).sendKeys(jsonHandler.getStrFromJson("phoneNumber"));
    }

    public void enterPostalCode() {
        driver.findElement(By.xpath("//input[@placeholder='Kod pocztowy']")).sendKeys(jsonHandler.getStrFromJson("postalCode"));
    }

    public void clickAcceptConditionButton() {
        driver.findElement(By.cssSelector(".r-19wmn03")).click();
    }

    public void createAccount() {
        driver.findElement(By.cssSelector(".r-jwli3a")).click();
    }
}
