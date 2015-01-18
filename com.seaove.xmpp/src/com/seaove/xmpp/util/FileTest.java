package com.seaove.xmpp.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class FileTest {
	public static void main(String[] args) {
		
		String s = "get_login:{12312123132123132123}";
		String key=s.substring(0,s.indexOf(":")).trim();//截取0到:的值
		//F:\DevelopFile\rcp\com.seaove.xmpp\src\key_value.properties
		String  path = "F:/DevelopFile/rcp/com.seaove.xmpp/";
		System.out.println(key);
		String value=FileTool.getReturnStr(path+"src/key_value.properties", key);
		System.out.println(value);
		SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
		Date a = new Date();
		String test = data.format(a);
		System.out.println(test);
		
	}
}
