package com.sutong.bjstjh.hcp.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTools {

	public DateTools() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {

	}

	public static String getYMDHMS() {
		Calendar c = Calendar.getInstance();
		String STR = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH) + "_" + c.get(Calendar.HOUR_OF_DAY) + "-" + c.get(Calendar.MINUTE) + "-" + c.get(Calendar.SECOND);
		return STR;
	}

	/**
	 * 格式化日期，时间
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormate(Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date.getTime());
	}

	/**
	 * 毫秒转成日期，时间格式
	 * 
	 * @param seconds
	 * @return
	 */
	public static String ms2DateTime(long seconds) {
		Date datetime = new Date(seconds);
		return dateFormate(datetime);
	}

	public static String date2String(Date date) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date.getTime());
	}

	/**
	 *
	 * @param datetimeStr
	 * @return
	 * @throws ParseException
	 */
	public static Date string2Date(String datetimeStr) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		// formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

		date = formatter.parse(datetimeStr);

		return date;
	}

	/*
	 * public static java.util.Date string2UTCDate(String datetimeStr) throws ParseException {
	 * DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date date=null;
	 * formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
	 *
	 * date = formatter.parse(datetimeStr);
	 *
	 *
	 * return date; }
	 */
	public static String getNowStr() {
		Date datetime = new Date();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(datetime.getTime());
	}

	/**
	 * 得到当天0时，0分，0秒
	 *
	 * @return
	 */
	public static String getNowStr000000() {
		Date datetime = new Date();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(datetime.getTime()) + " 00:00:00";
	}
}
