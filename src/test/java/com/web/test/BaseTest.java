package com.web.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.time.LocalDateTime;
import java.util.Random;

public class BaseTest {
    public static WebDriver driver;
    private JsonHandler jsonHandler = new JsonHandler("set_up.json");
    private Database database = new Database();
    public static LocalDateTime registerTime;

    @BeforeSuite
    public void setUp() {
        databaseSetup();
        System.setProperty("webdriver.gecko.driver", "src/test/java/com/web/test/resources/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(jsonHandler.getStrFromJson("url"));
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void databaseSetup() {
        String login = jsonHandler.getStrFromJson("login");
        database.connect();

        String checkDatabaseLogin = database.queryForLogin("select * from tikrow_dev.user where login like '" + login + "'");

        if (!checkDatabaseLogin.isEmpty()) {
            System.out.println("Znaleziono dopasowanie: " + checkDatabaseLogin);
            String newLogin = login + randomLoginEnd();
            int rowsAffected = database.executeUpdate("update tikrow_dev.user set login = '" + newLogin + "' where login like '" + login + "'");
            System.out.println("Zmodyfikowano wiersze w liczbie: " + rowsAffected);
        } else {
            System.out.println("Brak loginu w bazie");
        }

        database.disconnect();
    }

    private String randomLoginEnd() {
        StringBuilder sb = new StringBuilder(3);
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int index = random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".length());
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".charAt(index));
        }
        return sb.toString();
    }

    public static void setRegisterTime(LocalDateTime registerTime) {
        BaseTest.registerTime = registerTime;
    }

    public static LocalDateTime getRegisterTime() {
        return registerTime;
    }
}
