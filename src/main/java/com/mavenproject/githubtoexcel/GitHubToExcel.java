package com.mavenproject.githubtoexcel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GitHubToExcel {

    private static List<Format> dataRecord = new ArrayList();
    private static String URL = "https://github.com/STIW3054-A191/Assignments/wiki/List_of_Student";
    private static String URL2 = "https://github.com/STIW3054-A191/Main-Issues/issues/1";
    

    public static void GitHub() {
        try {
            System.out.println("Accessing " + URL + "...");

            Document source = Jsoup.connect(URL).get();
            Element table = source.select("table").get(0);
            Elements rows = table.select("tr");

            for (Element row : rows) {
                
                Elements th1 = row.select("th:nth-child(1)");
                Elements th2 = row.select("th:nth-child(2)");
                Elements th3 = row.select("th:nth-child(3)");
                
                Elements data1 = row.select("td:nth-child(1)");
                Elements data2 = row.select("td:nth-child(2)");
                Elements data3 = row.select("td:nth-child(3)");
                
                String header1 = th1.text();
                String header2 = th2.text();
                String header3 = th3.text();
                
                String column1 = data1.text();
                String column2 = data2.text();
                String column3 = data3.text();

                dataRecord.add(new Format(header1 + column1, header2 + column2, header3 + column3));
            }

            System.out.println("Table data has been collected successfully.");
            System.out.println();

        } catch (IOException e) {
            System.out.println("ERROR : Failed to access " + URL);
        }
    }

    public static void Excel() {

        if (dataRecord.isEmpty()) {
            System.out.println("ERROR : No data to write, build terminated.");
            System.exit(0); //without this, the program will write empty excel file
        }

        String excelFile = "Excel_List.xlsx";

        System.out.println("Writing the " + excelFile + "...");

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Malaysia");
        try {
            for (int i = 0; i < dataRecord.size(); i++) {
                XSSFRow row = sheet.createRow(i);

                XSSFCell cell1 = row.createCell(0);
                cell1.setCellValue(dataRecord.get(i).getHeader());
                XSSFCell cell2 = row.createCell(1);
                cell2.setCellValue(dataRecord.get(i).getData());
                XSSFCell cell3 = row.createCell(2);
                cell3.setCellValue(dataRecord.get(i).getData2());
                
            if (i > 0){
                Document doc2 = Jsoup.connect(URL2).get();
                Elements link1 = doc2.select("td");
                Elements comments = link1.select("p:Contains("+ dataRecord.get(i).getData() + ")");
                Elements links = comments.select("a");
                String gitlinks = links.text();
                XSSFCell cell4 = row.createCell(3);
                cell4.setCellValue(gitlinks);       
                
            }
            else {
                 XSSFCell cell4 = row.createCell(3);
                cell4.setCellValue("GitHub Links");
            }
            }

            FileOutputStream outputFile = new FileOutputStream(excelFile);
            workbook.write(outputFile);
            outputFile.flush();
            outputFile.close();
            System.out.println(excelFile + " Is written successfully.");
        } catch (IOException e) {
            System.out.println("ERROR : Failed to write the file!");
        }
    }

    public static void main(String[] args) {
        GitHub();
        Excel();
    }
}








