package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickAcademicsMenuItem() {
        WebElement academicsMenuItem = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@data-toggle='collapse' and @data-target='#2218']/a[contains(@class, 'a-uims-nav')]")));
        academicsMenuItem.click();
    }

    public void clickAdvancedCreditProgram() {
        WebElement acpMenuItem = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@data-toggle='collapse' and @data-target='#3174']/a[contains(@class, 'a-uims-nav')]")));
        acpMenuItem.click();
    }
    public void clickAcpMarkAttendance() {
        WebElement acpMarkAttendanceMenuItem = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li/a[@class='a-uims-nav' and contains(@href, 'frmWinningCampMarkAttendance.aspx')]")));
        acpMarkAttendanceMenuItem.click();
    }

    // Define other methods to interact with elements on the home page as needed
    // Example: public void doSomethingElse() { ... }
}

