package com.gudeng.commerce.gd.report.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 缓存所用时间类型
 * @author Ailen
 *
 */
public class DateType {
	
	/**
	 * 今天之前小时
	 */
	public static Integer TODAY_BEFORE = 101;
	
	/**
	 * 今天当前小时
	 */
	public static Integer TODAY_NOW = 102;
	
	/**
	 * 今天
	 */
	public static Integer TODAY = 1;
	
	/**
	 * 昨天
	 */
	public static Integer YESTODAY = 2;
	
	/**
	 * 最近6天
	 */
	public static Integer SIX_DAY_BEFORE = 3;
	
	/**
	 * 最近14天
	 */
	public static Integer FOURTEENTH_DAY_BEFORE = 4;
	
	/**
	 * 最近29天
	 */
	public static Integer TWENTYNIGHT_DAY_BEFORE = 5;
	
	/**
	 * 本月 最近一月 不含今天
	 */
	public static Integer MONTH_DAY_BEFORE = 6;

	/**
	 * 某一天 之前
	 */
	public static Integer DAY_OF_BEFORE = 7;
	
	/**
	 * 某一月 之前
	 */
	public static Integer MONTH_OF_BEFORE = 8;
	
	private String startTime;
	
	private String endTime;
	
	private Calendar calendar;
	
	private SimpleDateFormat df;
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * 获取对应DateType对象
	 * 可以得到相应的startTime and endTime
	 * @param type 1:今天 2：昨天 3：六天前 4：14天前 5：29天前 6：最近一个月不包含今天 102:今天当时  101:今天之前
	 * @return
	 */
	public static DateType getDateType(Integer type) {
		DateType dt = new DateType();
		
		switch(type) {
		case 1:
			dt.getToday();
			break;
		case 2:
			dt.getYestoday();
			break;
		case 3:
			dt.getSix();
			break;
		case 4:
			dt.getFourtheen();
			break;
		case 5:
			dt.getTwentyNight();
			break;
		case 6:
			dt.getMonth();
			break;
		case 102:
			dt.getTodayNow();
			break;
		case 101:
			dt.getTodayBefore();
			break;
		}
		
		return dt;
	}
	
	/**
	 * 获取对应DateType对象
	 * 可以得到相应的startTime and endTime
	 * @param type 7：之前某一天 8：之前某一月 
	 * @param timeString 指定哪一天或之前哪月
	 * 
	 * @return
	 */
	public static DateType getDateType(Integer type, String timeString) throws ParseException {
		DateType dt = new DateType();
		
		switch(type) {
		case 7:
			dt.getDayOfBefore(timeString);
			break;
		case 8:
			dt.getMonthBefore(timeString);
			break;
		}
		
		return dt;
	}
	
	private void getDayOfBefore(String timeString) throws ParseException {
		String temp =  timeString + " 00:00:00";
		this.setStartTime(temp);
		
		temp =  timeString + " 23:59:59";
		this.setEndTime(temp);
	}
	
	private void getMonthBefore(String timeString) throws ParseException {
		df = new SimpleDateFormat("yyyy-MM");
		calendar=Calendar.getInstance();
		calendar.setTime(df.parse(timeString));
		
		String today = df.format(calendar.getTime());
		String temp =  today + "-01 00:00:00";
		
		today = df.format(calendar.getTime());
		this.setStartTime(temp);
		
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
		df = new SimpleDateFormat("yyyy-MM-dd");
		today = df.format(calendar.getTime());
		temp =  today + " 23:59:59";
		this.setEndTime(temp);
	}

	private void getToday() {
		df = new SimpleDateFormat("yyyy-MM-dd");
		String today = df.format(new Date());
		String temp =  today + " 00:00:00";
		this.setStartTime(temp);
		temp =  today + " 23:59:59";
		this.setEndTime(temp);
	}
	
	private void getTodayNow() {
		df = new SimpleDateFormat("yyyy-MM-dd HH");
		String today = df.format(new Date());
		String temp =  today + ":00:00";
		this.setStartTime(temp);
		temp =  today + ":59:59";
		this.setEndTime(temp);
	}
	
	private void getTodayBefore() {
		df = new SimpleDateFormat("yyyy-MM-dd");
		calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		String today = df.format(calendar.getTime());
		String temp =  today + " 00:00:00";
		this.setStartTime(temp);
		df = new SimpleDateFormat("yyyy-MM-dd HH");
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR)-1);
		today = df.format(calendar.getTime());
		temp =  today + ":59:59";
		this.setEndTime(temp);
	}
	
	private void getYestoday() {
		df = new SimpleDateFormat("yyyy-MM-dd");
		calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-1);
		String today = df.format(calendar.getTime());
		String temp =  today + " 00:00:00";
		this.setStartTime(temp);
		temp =  today + " 23:59:59";
		this.setEndTime(temp);
	}
	
	private void getSix() {
		df = new SimpleDateFormat("yyyy-MM-dd");
		calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-6);
		
		String today = df.format(calendar.getTime());
		String temp =  today + " 00:00:00";
		this.setStartTime(temp);
		
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+5);
		today = df.format(calendar.getTime());
		temp =  today + " 23:59:59";
		this.setEndTime(temp);
	}
	
	private void getFourtheen() {
		df = new SimpleDateFormat("yyyy-MM-dd");
		calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-14);
		String today = df.format(calendar.getTime());
		String temp =  today + " 00:00:00";
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+13);
		today = df.format(calendar.getTime());
		this.setStartTime(temp);
		temp =  today + " 23:59:59";
		this.setEndTime(temp);
	}
	
	private void getTwentyNight() {
		df = new SimpleDateFormat("yyyy-MM-dd");
		calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-29);
		String today = df.format(calendar.getTime());
		String temp =  today + " 00:00:00";
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+28);
		today = df.format(calendar.getTime());
		this.setStartTime(temp);
		temp =  today + " 23:59:59";
		this.setEndTime(temp);
	}
	
	private void getMonth() {
		df = new SimpleDateFormat("yyyy-MM");
		calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		String today = df.format(calendar.getTime());
		String temp =  today + "-01 00:00:00";
		
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-1);
		today = df.format(calendar.getTime());
		this.setStartTime(temp);
		
		df = new SimpleDateFormat("yyyy-MM-dd");
		today = df.format(calendar.getTime());
		temp =  today + " 23:59:59";
		this.setEndTime(temp);
	}
	
	public static void main(String[] args) throws ParseException {
		DateType dt = DateType.getDateType(DateType.SIX_DAY_BEFORE);
		
		System.out.println(dt.getStartTime());
		System.out.println(dt.getEndTime());
		
	}
}
