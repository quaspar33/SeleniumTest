package com.web.test.page;

import com.web.test.AbstractPage;
import com.web.test.JsonHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDate;

public class AcceptCommissionPage extends AbstractPage {
    private JsonHandler jsonHandler;
    private LocalDate commissionDate;
    private int commissionDay;
    private int commissionMonth;
    private int commissionYear;
    private int commissionsCount;

    public AcceptCommissionPage(WebDriver driver) {
        super(driver);
        jsonHandler = new JsonHandler("accept_commission.json");
        commissionDate = currentDate.plusDays(13);
        commissionDay = commissionDate.getDayOfMonth();
        commissionMonth = commissionDate.getMonthValue();
        commissionYear = commissionDate.getYear();
        database.connect();
        commissionsCount = database.queryForCommission("select count(*) as 'commissions' from tikrow_dev.commissions where startDate > CURRENT_DATE and startDate < DATE_ADD(CURRENT_DATE, INTERVAL 29 DAY) and taken = 0");
        System.out.printf("Liczba zleceń w zakresie dni w aplikacji: %s\n", commissionsCount);
        database.disconnect();
        System.out.println("Rozpoczynam test przyjęcia zlecenia!");
    }

    public void clickCommission() {
        implicitWait(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text() = 'Start']"))).click();
        if (commissionsCount > 0) {
            touchFromElement(wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format("//*[text() = '%s']", commissionDay)))), 0, 330);
        } else {
            apiHandler.POST(
                    jsonHandler.getStrFromJson("uri"),
                    String.format(
                            "{\"employees_per_day\":1,\"hours\":1,\"start_time\":\"08:00\",\"dates\":[\"%d-%02d-%02d\"],\"region\":\"4809\",\"location\":\"460\",\"commission\":\"1463\"}",
                            commissionYear,
                            commissionMonth,
                            commissionDay
                    ),
                    jsonHandler.getStrFromJson("auth")
            );
            System.out.println("Wystawiono zlecenie!");
            refreshApp();
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(text(), 'Zlecenie 2025')]")))).click();
        }
    }

    public void clickAcceptCommission() {
        implicitWait(1000);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Lokalizacja')]")));
        scroll(element, 400);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Przyjmuję zlecenie')]"))).click();
    }

    public void setAgreements() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Potwierdzam, że moje dane w Kwestionariuszu osobowym są aktualne.')]"))).click();
        driver.findElement(By.xpath("//*[contains(text(), 'Potwierdzam zapoznanie się ze szczegółami zlecenia.')]")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'Potwierdzam zapoznanie się z Ogólnymi Warunkami Realizacji zleceń.')]")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'Potwierdzam, że nie jestem pracownikiem tego Klienta.')]")).click();
        driver.findElement(By.xpath("//*[text() = 'Potwierdzam']")).click();
        implicitWait(3000);
    }
}
