package chart.inputchart;

import input.Information;
import input.Session;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;

public class InformationChart extends Information{

    private static File file = new File("du_lieu_btl.xlsx");

    private static int totalRow = 0;

    public static int getTotalRow(String nameIndex){
        setTotalRow(nameIndex);
        return totalRow;
    }

    public static void setTotalRow(String nameIndex){
        try{
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet(nameIndex);
            totalRow = sheet.getPhysicalNumberOfRows();
            workbook.close();
        }catch (Exception e){
            e.getMessage();
        }
    }

    public static SessionCandleChart getCandleRow(String nameIndex, int rowIndex) {
        SessionCandleChart session = new SessionCandleChart();
        try {
            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet sheet = wb.getSheet(nameIndex);
            XSSFRow row = sheet.getRow(rowIndex);
            XSSFCell day = row.getCell(0);
            XSSFCell price = row.getCell(1); // endPrice
            XSSFCell state = row.getCell(2);
            String str = state.getStringCellValue();
            int i = str.indexOf("(");
            int j = str.indexOf("%");
            session.setState(Float.parseFloat(str.substring(i + 1, j)));
            session.setChange(Float.parseFloat(str.substring(0, i)));
            XSSFCell matchingTradeWeight = row.getCell(4);
            XSSFCell matchingTradeValue = row.getCell(5);
            XSSFCell transactionWeight = row.getCell(6);
            XSSFCell transactionValue = row.getCell(7);

            XSSFCell startPrice = row.getCell(8);
            XSSFCell highPrice = row.getCell(9);
            XSSFCell lowPrice = row.getCell(10);

            session.setDay(day.getStringCellValue());
            session.setPrice(Float.parseFloat(price.getStringCellValue()));
            session.setNameIndex(nameIndex);
            session.setMatchingTradeWeight(matchingTradeWeight.getStringCellValue());
            session.setMatchingTradeValue(matchingTradeValue.getStringCellValue());
            session.setTransactionWeight(transactionWeight.getStringCellValue());
            session.setTransactionValue(transactionValue.getStringCellValue());

            session.setStartPrice(Float.parseFloat(startPrice.getStringCellValue()));
            session.setHighPrice(Float.parseFloat(highPrice.getStringCellValue()));
            session.setLowPrice(Float.parseFloat(lowPrice.getStringCellValue()));

            wb.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return session;
    }

    
    public static long convertStringToLong(String st){
        if(st.indexOf(',')>=0){
            st = st.replace(",","");
        }
        return Long.parseLong(st);
    }
}
