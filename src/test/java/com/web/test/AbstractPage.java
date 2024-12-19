package com.web.test;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.PageFactory;
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
    public static JavascriptExecutor js;

    public AbstractPage(WebDriver driver) {
        AbstractPage.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        database = new Database();
        apiHandler = new ApiHandler();
        currentDate = LocalDate.now();
        currentMonth = currentDate.getMonthValue();
        currentDay = currentDate.getDayOfMonth();
        currentYear = currentDate.getYear();
        js = (JavascriptExecutor) driver;
    }

    public void implicitWait(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void scroll(WebElement element, int yOffset) {
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(element);
        new Actions(driver)
                .scrollFromOrigin(scrollOrigin, 0, yOffset)
                .perform();
    }

    public void touchFromElement(WebElement element, int xOffset, int yOffset) {
        new Actions(driver).moveToElement(element, xOffset, yOffset)
                .click()
                .build()
                .perform();
    }

    public void refreshApp() {
        driver.navigate().refresh();
    }
}
