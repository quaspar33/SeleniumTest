package com.web.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;

public abstract class AbstractPage {
    public static WebDriver driver;
    public static WebDriverWait wait;
    public static Database database;
    public static ApiHandler apiHandler;
    public static LocalDate currentDate;
    public static int currentMonth;
    public static int currentDay;
    public static int currentYear;

    public AbstractPage(WebDriver driver) {
        AbstractPage.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        database = new Database();
        apiHandler = new ApiHandler();
        currentDate = LocalDate.now();
        currentMonth = currentDate.getMonthValue();
        currentDay = currentDate.getDayOfMonth();
        currentYear = currentDate.getYear();
    }

    public void implicitWait(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
