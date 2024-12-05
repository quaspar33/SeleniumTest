package com.web.test.page;

import com.web.test.AbstractPage;
import com.web.test.JsonHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AcceptCommissionPage extends AbstractPage {
    private JsonHandler jsonHandler;
    private int commissionDay;
    private int commissionsCount;

    public AcceptCommissionPage(WebDriver driver) {
        super(driver);
        jsonHandler = new JsonHandler("accept_commission.json");
        commissionDay = currentDate.plusDays(2).getDayOfMonth();
        database.connect();
        commissionsCount = database.queryForCommission(String.format("select count(*) as 'commissions' from tikrow_dev.commissions where definitionId = 1463 and startDate like '2024-%02d-%02d %%'", currentMonth, commissionDay));
        System.out.printf("Liczba zleceń o definicji \"Zlecenie 2024\" = %s, w dniu 2024-%02d-%02d%n", commissionsCount, currentMonth, commissionDay);
        database.disconnect();
        System.out.println("Rozpoczynam test przyjęcia zlecenia!");
    }

    public void clickCommission() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Start')]"))).click();
        if (commissionsCount == 0) {
            apiHandler.POST(
                    jsonHandler.getStrFromJson("uri"),
                    String.format(
                            "{\"employees_per_day\":1,\"hours\":1,\"start_time\":\"08:00\",\"dates\":[\"%d-%02d-%02d\"],\"region\":\"4809\",\"location\":\"460\",\"commission\":\"1463\"}",
                            currentYear,
                            currentMonth,
                            commissionDay
                    ),
                    jsonHandler.getStrFromJson("auth")
            );
            System.out.println("Wystawiono zlecenie!");
            refreshApp();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format("//*[contains(text(), '%d')]", commissionDay)))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Zlecenie 2024')]"))).click();
        } else {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(String.format("//*[contains(text(), '%d')]", commissionDay)))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Zlecenie 2024')]"))).click();
        }
    }

    public void clickAcceptCommission() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Lokalizacja')]")));
        scroll(element, 400);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Przyjmuję zlecenie')]"))).click();
    }

    public void setAgreements() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Potwierdzam, że moje dane w Kwestionariuszu osobowym są aktualne.')]"))).click();
        driver.findElement(By.xpath("//*[contains(text(), 'Potwierdzam zapoznanie się ze szczegółami zlecenia.')]")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'Potwierdzam zapoznanie się z Ogólnymi Warunkami Realizacji zleceń.')]")).click();
        driver.findElement(By.xpath("//*[contains(text(), 'Potwierdzam, że nie jestem pracownikiem tego Klienta.')]")).click();
        driver.findElement(By.xpath("//*[text()='Potwierdzam']")).click();
        implicitWait(3000);
    }
}
