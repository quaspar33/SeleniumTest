package com.web.test.page;

import com.web.test.AbstractPage;
import com.web.test.JsonHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class LoginPage extends AbstractPage {
    JsonHandler jsonHandler;

    public LoginPage(WebDriver driver) {
        super(driver);
        jsonHandler = new JsonHandler("login.json");
        System.out.println("Rozpoczynam test logowania!");
    }

    public void enterLogin() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Numer telefonu']"))).sendKeys(jsonHandler.getStrFromJson("login"));
    }

    public void enterPassword(LocalDateTime registerTime) {
        database.connect();
        AtomicReference<String> atomicPassword = new AtomicReference<>("");
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver -> {
            List<String> recentSms = database.queryForTempPassword("SELECT * FROM tikrow_dev.notificationsSmsHistory ORDER BY sendDate DESC LIMIT 5");

            for (String sms : recentSms) {
                String[] parts = sms.split(";");
                if (parts.length < 3) continue;

                LocalDateTime smsDate = LocalDateTime.parse(parts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String phoneNumber = "48" + jsonHandler.getStrFromJson("phoneNumber");

                if (smsDate.isAfter(registerTime) && parts[2].equals(phoneNumber)) {
                    String password = parts[0].replace("[DEV] Czesc! Twoje haslo do Tikrow to: ", "");
                    atomicPassword.set(password);
                    return true;
                }
            }
            System.out.println("Próbuję pobrać hasło...");
            return false;
        });
        System.out.println("Udało się pobrać hasło!");
        database.disconnect();
        driver.findElement(By.xpath("//input[@placeholder='Hasło']")).sendKeys(atomicPassword.get());
    }

    public void clickLogin() {
        driver.findElement(By.cssSelector("button.css-175oi2r:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1)")).click();
        implicitWait(10000);
    }
}
