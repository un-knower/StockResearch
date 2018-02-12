package com.kers.stock.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.stream.FileCacheImageInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sanselan.common.BinaryInputStream;

public class ExcelHandUtils {
	 public static final String FILETYPE_XLS="xls";
	 public static final String FILETYPE_XLSX="xlsx";

	private InputStreamReader fr = null;
	private InputStream br = null;

	public ExcelHandUtils(String f) throws IOException {
		fr = new InputStreamReader(new FileInputStream(f), "GBK");
	}

	public ExcelHandUtils(InputStream inputStream) throws IOException {
//		fr = new InputStreamReader(inputStream, "GBK");
		br = inputStream;
//		BinaryInputStream binaryInputStream = new BinaryInputStream(inputStream);
//		FileCacheImageInputStream  inptuStream = new FileCacheImageInputStream(inputStream, new File( "/opt/tmp"));
	}

	
	public  List<List<List<String>>> readExcelWithoutTitle(String xlsType) throws Exception{  
//	    String fileType = filepath.substring(filepath.lastIndexOf(".") + 1, filepath.length());  
//	    InputStream is = null;  
//	    br = new BufferedReader(fr);
	    Workbook wb = null;  
	    try {  
//	        is = fr;//new FileInputStream(filepath);  
	          
	        if (xlsType.equals("xls")) {  
	    		BinaryInputStream binaryInputStream = new BinaryInputStream(br);
                   FileInputStream   fileInpuStream = new FileInputStream(new File("/Users/admin/Downloads/hs_month_2017_10.xls"));
	            wb = new HSSFWorkbook( fileInpuStream);
	        } else if (xlsType.equals("xlsx")) {  
	            wb = new XSSFWorkbook(br);  
	        } else {  
	            throw new Exception("读取的不是excel文件");  
	        }  
	          
	        List<List<List<String>>> result = new ArrayList<List<List<String>>>();//对应excel文件  
	          
	        int sheetSize = wb.getNumberOfSheets();  
	        for (int i = 0; i < sheetSize; i++) {//遍历sheet页  
	            Sheet sheet = wb.getSheetAt(i);  
	            List<List<String>> sheetList = new ArrayList<List<String>>();//对应sheet页  
	              
	            int rowSize = sheet.getLastRowNum() + 1;  
	            for (int j = 0; j < rowSize; j++) {//遍历行  
	                Row row = sheet.getRow(j);  
	                if (row == null) {//略过空行  
	                    continue;  
	                }  
	                int cellSize = row.getLastCellNum();//行中有多少个单元格，也就是有多少列  
	                List<String> rowList = new ArrayList<String>();//对应一个数据行  
	                for (int k = 0; k < cellSize; k++) {  
	                    Cell cell = row.getCell(k);  
	                    String value = null;  
	                    if (cell != null) {  
	                        value = cell.toString();  
	                    }  
	                    rowList.add(value);  
	                }  
	                sheetList.add(rowList);  
	            }  
	            result.add(sheetList);  
	        }  
	          
	        return result;  
	    } catch (FileNotFoundException e) {  
	        throw e;  
	    } finally {  
	        if (wb != null) {  
	            wb.close();  
	        }  
	        if (fr != null) {  
	            fr.close();  
	        }  
	    }  
	}  
}
