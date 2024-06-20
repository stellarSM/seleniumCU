package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExcelReader {

    public static List<String> studentAttendanceList() {
        try {
            String fileId = "1PlwO9-_Of6cA3eLyJqnYV8BLwgVVlLgZDKSTj4B7TiM";
            String downloadUrl = "https://docs.google.com/spreadsheets/d/" + fileId + "/export?format=xlsx";
            String localFilePath = "downloaded_file.xlsx";

            downloadFile(downloadUrl, localFilePath);

            Set<String> firstColumnData = readFirstColumnData(localFilePath);
            List<String> studentPresent = new ArrayList<>();
            for (String cellData : firstColumnData) {
                if(!cellData.isEmpty())
                studentPresent.add(cellData.toLowerCase());
                System.out.println(cellData);
            }
            deleteFile(localFilePath);
            System.out.println("\u001B[33m Student Present = " + studentPresent.size() + "\u001B[0m");
            return studentPresent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static void downloadFile(String fileURL, String savePath) throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");

        int responseCode = httpConn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = httpConn.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(savePath);

            int bytesRead;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            System.out.println("File downloaded successfully!");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }
    public static Set<String> readFirstColumnData(String excelFilePath) {
        Set<String> firstColumnData = new HashSet<>();

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            int count = 0;
            while (rowIterator.hasNext() && count < 50) {
                count++;
                Row row = rowIterator.next();
                Cell cell = row.getCell(0); // Get the first column cell
                if (cell != null) {
                    switch (cell.getCellType()) {
                        case STRING:
                            firstColumnData.add(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            firstColumnData.add(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case BOOLEAN:
                            firstColumnData.add(String.valueOf(cell.getBooleanCellValue()));
                            break;
                        case FORMULA:
                            firstColumnData.add(cell.getCellFormula());
                            break;
                        default:
                            firstColumnData.add("");
                    }
                } else {
                    firstColumnData.add("");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return firstColumnData;
    }
    private static void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.delete(path);
            System.out.println("File deleted successfully!");
        } catch (IOException e) {
            System.out.println("Failed to delete the file.");
            e.printStackTrace();
        }
    }
}
