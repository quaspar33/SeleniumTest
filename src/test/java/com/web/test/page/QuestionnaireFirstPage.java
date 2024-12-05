package com.web.test.page;

import com.web.test.AbstractPage;
import com.web.test.JsonHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.LinkedHashMap;
import java.util.Map;

public class QuestionnaireFirstPage extends AbstractPage {
    JsonHandler jsonHandler;
    private int birthYear;

    private Map<By, String> addressMap = new LinkedHashMap<>() {{
        put(By.xpath("//input[@maxlength='6']"), "postalCode");
        put(By.xpath("//input[@type='text']"), "cityName");
        put(By.xpath("/html/body/div[1]/div/div/div/div/div/div/div/div/div/div/div[1]/div/div/div/div/div/div/div[1]/div/div/div[3]/div/div/div/div/div/div/div[5]/div[7]/div/div[3]/div/input"), "streetName");
        put(By.xpath("//input[@placeholder='Nr budynku']"), "buildingNumber");
    }};

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
        WebElement dateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Data urodzenia']")));
        js.executeScript("arguments[0].value = '';", dateInput);
        dateInput.sendKeys(String.format("%d-%02d-%02d", currentDay, currentMonth, birthYear));
        js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" + "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", dateInput);
    }

    public void enterNationality() {
        new Select(driver.findElement(By.xpath("//select[@aria-label='Obywatelstwo']"))).selectByValue("145");
    }

    public void enterPesel() {
        String peselStr = apiHandler.GET(String.format("https://generator.avris.it/api/PL/pesel?birthdate=%d-%02d-%02d&gender=m", birthYear, currentMonth, currentDay));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='PESEL']"))).sendKeys(peselStr.substring(1, peselStr.length() - 1));
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
        WebElement phoneNumber = driver.findElement(By.xpath("//input[@placeholder='123456789']"));
        scroll(phoneNumber, 1500);
        phoneNumber.sendKeys(jsonHandler.getStrFromJson("phoneNumber"));
    }

    public void enterTaxOffice() {
        implicitWait(1000);
        driver.findElement(By.cssSelector("div.r-1xuzw63:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > svg:nth-child(1)")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Urząd Skarbowy Poznań-Wilda 61-558, Dolna Wilda 80')]"))).click();
    }

    public void enterIban() {
        String iban = apiHandler.GET("https://generator.avris.it/api/_/iban?country=PL");
        driver.findElement(By.xpath("//input[@placeholder='Nr rachunku bankowego']")).sendKeys(iban.substring(3, iban.length() - 1).replace(" ", ""));
    }

    public void enterAddress() {
        addressMap.forEach((key, value) -> {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(key));
            scroll(element, 300);
            element.sendKeys(jsonHandler.getStrFromJson(value));
            if (!value.equals("buildingNumber")) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.format("//*[contains(text(), '%s')]", jsonHandler.getStrFromJson(value))))).click();
            }
        });
    }

    public void acceptAddress() {
        driver.findElement(By.xpath("//*[contains(text(), 'Tak')]")).click();
    }

    public void nextPage() {
        implicitWait(2000);
        driver.findElement(By.xpath("//*[contains(text(), 'Dalej')]")).click();
    }
}
