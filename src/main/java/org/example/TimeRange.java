package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class TimeRange {
    static HashSet<String> setOfRanges = new HashSet<>() {{
        add("09:30 - 11:00 AM");
        add("11:05 - 12:35 PM");
        add("1:30 - 3:00 PM");
        add("3:05 - 4:35 PM");
    }};
    public static boolean isCurrentTimeWithinRange(String timeRange) {
        // Define the formatter for the input time format
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");

        // Trim the input string to remove any leading or trailing whitespace
        timeRange = timeRange.trim();

        // Split the input string into start time and end time
        String[] times = timeRange.split(" - ");
        if (times.length != 2) {
            throw new IllegalArgumentException("Invalid time range format");
        }

        // Get the AM/PM part from the end time and convert to lowercase
        String endTimePart = times[1].trim();
        if (endTimePart.length() < 3) {
            throw new IllegalArgumentException("Invalid end time format");
        }
        String amPm = endTimePart.substring(endTimePart.length() - 2).toLowerCase();
        if(timeRange.equals("11:05 - 12:35 PM")){
            amPm = "am";
        }
        String startTimeStr = times[0].trim() + " " + amPm;
        String endTimeStr = endTimePart.toLowerCase();

//        System.out.println("Parsed start time: " + startTimeStr);
//        System.out.println("Parsed end time: " + endTimeStr);

        LocalTime startTime;
        LocalTime endTime;
        try {
            startTime = LocalTime.parse(startTimeStr, timeFormatter);
            endTime = LocalTime.parse(endTimeStr, timeFormatter);
        } catch (DateTimeParseException e) {
            System.err.println("Failed to parse time: " + e.getParsedString());
            throw new IllegalArgumentException("Invalid time format", e);
        }

        LocalTime currentTime = LocalTime.now();

        return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
    }

    static void selectTimeRangeCheckbox(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement selectInput = wait.until(elementToBeClickable(By.id("txtTiming")));
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectInput);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectInput);

        WebElement timingDiv = driver.findElement(By.id("divTiming"));

        List<WebElement> rows = timingDiv.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            try {
                WebElement spanElement = row.findElement(By.tagName("span"));

                String timeRange = spanElement.getText().trim();
                Boolean isTimeInRange = isCurrentTimeWithinRange(timeRange);
                System.out.println("time Range = " + timeRange + " isRange " + isTimeInRange);
                if (isTimeInRange && setOfRanges.contains(timeRange)) {
                    WebElement checkbox = row.findElement(By.cssSelector("input[type='checkbox']"));
                    checkbox.click(); // Perform action (click the checkbox)
                    System.out.println("Checkbox for " + timeRange + " is selected.");
                    break;
                }

            } catch (NoSuchElementException e) {
                System.out.println("Unable to find span element within <tr>.");
                // Add additional wait/retry logic if needed
            }
        }
    }
}
