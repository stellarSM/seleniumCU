package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public  HomePage login(String userName, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtUserId")));

        // Enter the login credentials
        usernameField.sendKeys(userName);
        WebElement nextButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnNext")));
        nextButton.click();
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtLoginPassword")));
        passwordField.sendKeys(password);
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnLogin")));

        // Click the login button
        loginButton.click();
        return new HomePage(driver);

    }
}
