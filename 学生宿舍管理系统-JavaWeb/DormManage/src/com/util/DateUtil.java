package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	/*
	* 获取指定格式的时间日期字符串
	* */
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	/*
	* 将时间字符串转换为某种格式
	* */
	public static Date formatString(String str,String format) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}

}
