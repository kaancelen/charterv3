package com.kaancelen.charterv3.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kaancelen.charterv3.models.JobRecord;

public class CustomHelper {
	
	public static List<JobRecord> getJobRecordsFromExcelFile(String filepath){
		List<JobRecord> records = new ArrayList<JobRecord>();
		
		try{
			
			FileInputStream inputStream = new FileInputStream(new File(filepath));
			String mimeType = Util.getFileExtension(filepath);
			Workbook workbook = null;
			Sheet sheet = null;
			if(mimeType.compareTo("xls") == 0){
				workbook = new HSSFWorkbook(inputStream);
			}else{
				workbook = new XSSFWorkbook(inputStream);
			}
			sheet = workbook.getSheetAt(0);
			//For each row
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();//skip headers
			while(rowIterator.hasNext()){
				Row row = rowIterator.next();
				
				//For each cell
				JobRecord record = new JobRecord();
				int cellIndex = 0;
				Iterator<Cell> cellIterator = row.cellIterator();
				while(cellIterator.hasNext()){
					Cell cell = cellIterator.next();
					try{
						switch (cellIndex) {
							case 0: record.setPersonel(getPersonelName(cell.getStringCellValue())); break;
							case 1: break;
							case 2: break;
							case 3: break;
							case 4: break;
							case 5: break;
							case 6: break;
							case 7: break;
							case 8: break;
							case 9: record.setMonth(Util.replaceTurkishChars(cell.getStringCellValue().toUpperCase())); break;
							case 10: break;
							case 12: break;
							case 13: break;
							case 14: record.setType(cell.getStringCellValue().toLowerCase());break;
							case 15: record.setYear(String.format("%4.0f", cell.getNumericCellValue())); break;
						}
					}catch(IllegalStateException e){//if catch exception just keep going
						System.err.println(e.getLocalizedMessage());
					}
					cellIndex++;
				}
				//add record to list
				records.add(record);
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("CustomHelper : " + e.getLocalizedMessage());
			return null;
		} catch (IOException e) {
			System.err.println("CustomHelper : " + e.getLocalizedMessage());
			return null;
		}
		
		return records;
	}
	
	private static String getPersonelName(String cellValue){
		String[] splittedName = cellValue.split(" ");
		return Util.replaceTurkishChars(splittedName[0]).toUpperCase();
	}
}
