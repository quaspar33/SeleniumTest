package com.web.test.page;

import com.web.test.AbstractPage;
import com.web.test.JsonHandler;
import com.web.test.PasswordFromSmsParser;
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

                String password = new PasswordFromSmsParser().parse(parts[0]);
                LocalDateTime smsDate = LocalDateTime.parse(parts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String phoneNumber = parts[2];

                if ((registerTime.withNano(0).isBefore(smsDate) || registerTime.withNano(0).isEqual(smsDate)) && phoneNumber.equals("48".concat(jsonHandler.getStrFromJson("phoneNumber")))) {
                    atomicPassword.set(password);
                    return true;
                }
            }
            return false;
        });
        String password = atomicPassword.get();
        System.out.println("Udało się pobrać hasło: " + password);
        database.disconnect();
        driver.findElement(By.xpath("//input[@placeholder='Hasło']")).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(By.cssSelector("button.css-175oi2r:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1)")).click();
    }
}
