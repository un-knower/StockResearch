package com.cmall.stock.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	private static ArrayList<String> filelist = new ArrayList<String>();

	public static ArrayList<String>  getFiles(String filePath) {
		File root = new File(filePath);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				/*
				 * 递归调用
				 */
				getFiles(file.getAbsolutePath());
				
			} else {
				filelist.add(file.getAbsolutePath());
			}
		}
		return filelist;
	}
	
	public static void main(String[] args) {
		 String path="/opt/stock/data/StapleDay/";
		 System.out.println( getFiles(path));
	}
}
