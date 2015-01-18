package com.seaove.xmpp.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;

public class FileTool {
	public static Properties prop = null;
	public static String getRoot(){
		 String path=null;
		  try {
		    path = FileLocator.toFileURL(Platform.getBundle("com.seaove.xmpp").getEntry("")).getPath();
		    path = path.substring(path.indexOf("/") + 1, path.length());
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
		  return "/"+path;
	}
	/**
	 * 获取配置文件对应的key值
	 * @param filePath 配置文件的地址
	 * @param parm 想获取的参数key
	 * @return
	 */
	public static String getReturnStr(String filePath, String parm) {
		try {
			FileInputStream fis = new FileInputStream(new File(filePath));
			prop = new Properties();
			prop.load(fis);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(parm);
	}
	/**
	 * 写入日志文件
	 * @param filename 文件名
	 * @param s	写入的内容
	 */
	public static void setlogfile(String filename,String s){
		String filepath=FileTool.getRoot();
		File file=new File(filepath+filename);
		if(!file.exists()||!file.isFile()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileWriter writer=null;
		 BufferedWriter bwriter=null;
		try {
			writer=new FileWriter(file,true);
			bwriter = new BufferedWriter(writer);
			 bwriter.write(s);
			 bwriter.newLine();
			 bwriter.flush();
			 bwriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 格式化日期
	 * @return
	 */
	public static String ToDateString(){
		Date a = new Date();
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
		return data.format(a);
	}
}
