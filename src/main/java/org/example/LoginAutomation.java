package org.example;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import javax.swing.plaf.IconUIResource;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class LoginAutomation {

    public static void selectAndCheckGroupDropdown(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the dropdown to be visible and clickable
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rcbStudentGroup_Input")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);

        // Wait for the checkbox to be visible and clickable
        WebElement dcpdK4batch = driver.findElement(By.id("chkStudentGroup"));
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dcpdK4batch);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dcpdK4batch);

    }
    public static void clickShowButton(WebDriver driver) {
        WebElement showBtn = driver.findElement(By.id("btnShow"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", showBtn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", showBtn);
    }
    static LocalTime parseTime(String timeString) {
        return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("hh:mm a"));
    }

    public static void main(String[] args) throws InterruptedException {
        List<String> studentPresent = ExcelReader.studentAttendanceList();
        WebDriverManager.chromedriver().setup();
        WebDriver driver=new ChromeDriver();
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        try {
            driver.get("https://staff.cuchd.in/");
            LoginPage loginPage = new LoginPage(driver);

            HomePage homePage = loginPage.login("e17277", "Cu@.2210");
            homePage.clickAcademicsMenuItem();
            homePage.clickAdvancedCreditProgram();
            homePage.clickAcpMarkAttendance();

            WebElement checkbox = driver.findElement(By.id("chkCheck"));
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
            Thread.sleep(4000);
            TimeRange.selectTimeRangeCheckbox(driver);
            selectAndCheckGroupDropdown(driver);
            Thread.sleep(2000);
            clickShowButton(driver);
            Thread.sleep(3000);
            AttendanceMark markAttendance = new AttendanceMark(driver);
            markAttendance.clickAllCheck();


            markAttendance.checkStudentCheckbox(driver, studentPresent);
            Thread.sleep(5000000);

        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
