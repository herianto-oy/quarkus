package org.acme.employee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.multipart.FileUpload;

public class EmployeeParser {
    private static final Logger LOG = Logger.getLogger(EmployeeParser.class);

    public static List<EmployeeEntity> parserExcel(FileUpload fileUpload){
        List<EmployeeEntity> list = new ArrayList<>();
        try {
            File file = new File(fileUpload.filePath().toString());
            //Create Workbook instance for xlsx/xls file input stream
			Workbook workbook = null;
			if(fileUpload.fileName().toLowerCase().endsWith("xlsx")){
				workbook = new XSSFWorkbook(file);
                LOG.info("XLSX TYPE");
			}else if(fileUpload.fileName().toLowerCase().endsWith("xls")){
                workbook = new HSSFWorkbook(new FileInputStream(file));
                LOG.info("XLS TYPE");
			}
            
            Sheet sheet = workbook.getSheetAt(0); 

            Iterator<Row> rowIterator = sheet.iterator(); 
            while (rowIterator.hasNext()) { 
                Row row = rowIterator.next();
                
                // Skip header
                if(row.getRowNum() == 0){
                    continue;
                } 
            
                EmployeeEntity emp = new EmployeeEntity();
                emp.setName(row.getCell(0).getStringCellValue()); 
                emp.setEmail(row.getCell(1).getStringCellValue()); 
                list.add(emp);
            }
        } catch (InvalidFormatException e) {
            LOG.error(e);
            // e.printStackTrace();
        } catch (IOException e) {
            LOG.error(e);
            // e.printStackTrace();
        } 

        return list;
    }

    public static List<EmployeeEntity> parserCsv(FileUpload fileUpload){
        List<EmployeeEntity> list = new ArrayList<>();
        
        String line = "";
        String csvDelimiter = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(fileUpload.filePath().toString()))) {
            // Skip header
            line = br.readLine();
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvDelimiter);
                if(data.length == 2){
                    EmployeeEntity emp = new EmployeeEntity();
                    emp.setName(data[0]);
                    emp.setEmail(data[1]);
                    list.add(emp);
                }
            }
        } catch (IOException e) {
            LOG.error(e);
        }
        
        return list;
    }
}
