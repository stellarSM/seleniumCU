package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceMark {
    WebDriver driver;
    public AttendanceMark(WebDriver driver) {
        this.driver = driver;
    }
    public void clickAllCheck() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            // Wait until the checkbox element is clickable
            WebElement checkAll = wait.until(ExpectedConditions.elementToBeClickable(By.id("chkCheckAll1")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkAll);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkAll);

            System.out.println("\u001B[37m Clicked on 'Check All' checkbox. \u001B[0m");
        } catch (Exception e) {
            System.out.println("\u001B[31m Unable to click on 'Check All' checkbox. \u001B[0m");
        }
    }
    public  void checkStudentCheckbox(WebDriver driver, List<String> studentInfoList) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement table = driver.findElement(By.id("gvStudents"));
        // Find all rows in the table
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        HashMap<String , WebElement> studentsRollNoAndCheckBoxMap = new HashMap<>();
        // Iterate through each row
            for (WebElement row : rows) {
                try {
                    // Find the cell containing the student name
                    WebElement nameCell = row.findElement(By.xpath(".//td[6]/span"));
                    String nameText = nameCell.getText().trim();
                    String[] parts = nameText.split(":::");
                    String rollNumber = parts[0].trim();
                    String name = parts[1].trim();
                    WebElement checkbox = row.findElement(By.id("chkAttendance1"));
                    studentsRollNoAndCheckBoxMap.put(rollNumber.toLowerCase(), checkbox);
                } catch (Exception e) {
                    System.out.println("\u001B[31m Unable to find the student name or checkbox within the row. "  + "\u001B[0m");
                }
            }
//            for (Map.Entry<String, WebElement> entry : studentsRollNoAndCheckBoxMap.entrySet()) {
//                String key = entry.getKey();
//                System.out.println("Key = " + key);
//                test.add(key);
//                entry.getValue();
//            }
//            studentInfoList = test;
        for (String studentsRollNumber : studentInfoList) {
            try {
                WebElement checkBox = studentsRollNoAndCheckBoxMap.get(studentsRollNumber);
                if (checkBox != null) {
                    wait.until(ExpectedConditions.elementToBeClickable(checkBox));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkBox);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkBox);
                    System.out.println("\u001B[36m Checkbox for student " + studentsRollNumber + " is selected. \u001B[0m");
                } else {
                    System.out.println("\u001B[31m Checkbox for student " + studentsRollNumber + " not found. \u001B[0m");
                }
            } catch (Exception ex) {
                System.out.println("Exception = " + ex.getMessage());
                System.out.println("\u001B[31m Unable to find the student name or checkbox within the row. " + studentsRollNumber + "\u001B[0m");
            }
        }
    }
}
