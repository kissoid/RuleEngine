/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raven.rem.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mehmet Adem Sengul
 */
public class DateUtil {

	public static final String DATE_FORMAT = "dd.MM.yyyy";

    public static int getYearOfDate() {
        Calendar cal = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String yearString = sdf.format(cal.getTime()).substring(0, 4);
        return Integer.parseInt(yearString);
    }

    public static Date stringToDate(String date) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        return simpleDateFormat.parse(date);
    }

    public static String dateToString(Date date) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        return simpleDateFormat.format(date);
    }

    public static Date sqlDateToUtilDate(java.sql.Date date) {
        return new Date(date.getTime());
    }

    public static Date utilDateToSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

	public static int dayDifference(Date startDate, Date endDate) {
		return Double.valueOf((startDate.getTime() - endDate.getTime()) / (1000 * 60 * 60 * 24)).intValue();
    }

    public static int dayDifferenceAbsolute(Date startDate, Date endDate) {
        return (int) Math.abs(dayDifference(startDate, endDate) / (1000 * 60 * 60 * 24));
    }

    public static boolean isDateEqual(Date startDate, Date endDate) {
        return (dayDifference(startDate, endDate) == 0);
    }

    public static boolean isDateAfter(Date startDate, Date endDate) {
        return (dayDifference(startDate, endDate) > 0);
    }

    public static boolean isDateBefore(Date startDate, Date endDate) {
        return (dayDifference(startDate, endDate) < 0);
    }

    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static int dayDifferenceDays(Date startDate, Date endDate) {
        int days = 0;
        try {
            String pattern = "yyyyMMdd";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date sDate = sdf.parse(dateToString(startDate, pattern));
            Date eDate = sdf.parse(dateToString(endDate, pattern));
            days = new Long((eDate.getTime() - sDate.getTime()) / (1000 * 60 * 60 * 24)).intValue();
        } catch (ParseException ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return days;
    }

    public static Date addDaysToDate(Date date, int count) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, count);
        return cal.getTime();
    }

    public static Date addMonthsToDate(Date date, int count) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, count);
        return cal.getTime();
    }

    public static Date addYearsToDate(Date date, int count) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, count);
        return cal.getTime();
    }

    public static boolean isLeapYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String str = sdf.format(date);
        int year = Integer.parseInt(str);
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    public static Date formatDateToPattern(Date date, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateStr = sdf.format(date);
        return sdf.parse(dateStr);
    }
}
