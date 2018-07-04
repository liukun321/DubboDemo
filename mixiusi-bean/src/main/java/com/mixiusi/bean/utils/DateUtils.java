package com.mixiusi.bean.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/**
	 * 获取当前时刻的时间
	 * @param format
	 * @return
	 */
	public static String dateFormat(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date();
		return sdf.format(date);
	}
	
	public static Date getStartTimeOfDay() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	/**
	 * 获取时间差
	 * @param start
	 * @param end
	 * @param value
	 * @return
	 */
	public static Long subtractForDate(Date start, Date end, long value) {
		if(null == start || null == end || value <= 0)
			return null;
		return (end.getTime() - start.getTime())/value;
	}
	/**
	 * 获取当前年的第一天
	 * @return
	 */
	public static Date getDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 0);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 获取当前月的第一天时间
	 * @return
	 */
	public static Date getFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	/**
	 * 获取当前日先前退7天
	 * @return
	 */
	public static Date getPreWeek() {
		Calendar cal = Calendar.getInstance();  
	    cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 7);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 获取当前周一
	 * @return
	 */
	public static Date getNowWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(getDayOfYear()));
		System.out.println(sdf.format(getFirstDayOfMonth()));
		System.out.println(sdf.format(getPreWeek()));
		System.out.println(sdf.format(getNowWeek()));
	}
}
