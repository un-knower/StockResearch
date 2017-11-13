package com.cmall.stock.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class TextUtil {
	/**
	 * txt写入
	 * @param path
	 * @param value
	 */
	public void writerTxt( String path , String value) {
		BufferedWriter fw = null;
		try {
			
			File file = new File(path);
			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "utf-8")); // 指定编码格式，以免读取时中文字符异常
			fw.append(value);
			fw.newLine();
			fw.flush(); // 全部写入缓存中的内容
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
	
	/**
	 * txt 读取文件
	 * @param filePath
	 * @return
	 */
	public static List<String> readTxtFile(String filePath){
    	List<String> list = new ArrayList<String>();
        try {
                String encoding="utf-8";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                    	//String[] a = lineTxt.split("	");
                    	list.add(lineTxt);
                       // System.out.println(lineTxt);
                    }
                    read.close();
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
     return list;
    }
	
	public static void main(String[] args) {
		List<String> sss = TextUtil.readTxtFile("C://Users//temp1//Desktop//新建文本文档 (6).txt");
		String re = "";
		for (String string : sss) {
			re = re + string;
		}
		System.out.println(re);
		//System.out.println(MD5.MD5Encode("男Tee").toUpperCase());
	}
}
