package com.ruipengkj.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	
	final static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	/**
	 * 返回日期字符串
	 * @param date 日期类型
	 * @param pattern 日期格式
	 * @return
	 */
	public static String getDate(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 返回日期对象
	 * @param time 日期字符串
	 * @param pattern 日期格式
	 * @return
	 */
	public static Date getDate(String date, String pattern) {
		if (date == null || "".equals(date.trim())) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date datetime = null;
		try {
			datetime = sdf.parse(date);
		} catch (Exception e) {
			logger.error("转换日期出错，转换日期:{}，日期格式:{}", date, pattern);
		}
		return datetime;
	}

}
