package com.cisco.menstruation.configs;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具
 * Created by Administrator on 2016/4/1.
 */
public class DateUtils {
    public static String[] sWeekName = { "周日", "周一", "周二", "周三", "周四", "周五","周六" };

    /**
     * 获取当年当月的天数
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year,int month){
        if(month > 12){
            year += 1;
            month = 1;
        }else if(month < 1){
            year -= 1;
            month = 12;
        }
        int[] arrDay = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = 0;

        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            arrDay[1] = 29; // 闰年2月29天
        }
        days = arrDay[month - 1];

        return days;
    }

    /**
     * 获取年份
     * @return
     */
    public static int getYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取月份
     * @return
     */
    public static int getMonth(){
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前是当月的第几天
     * @return
     */
    public static int getCurrentMonthDay(){
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当天是星期几
     * @return
     */
    public static int getWeekDay(){
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取下一个星期天是那一天
     * @return
     */
    public static CustomDate getNextSunday(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,7-getWeekDay()+1);
        CustomDate date = new CustomDate(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH));
        return date;
    }

    public static int[] getWeekSunday(int year, int month, int day, int pervious) {
        int[] time = new int[3];
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.add(Calendar.DAY_OF_MONTH, pervious);
        time[0] = c.get(Calendar.YEAR);
        time[1] = c.get(Calendar.MONTH )+1;
        time[2] = c.get(Calendar.DAY_OF_MONTH);
        return time;
    }

    public static int getWeekDayFromDate(int year,int month){
        Calendar calendar = Calendar.getInstance();
        if(getDateFromString(year,month) == null){
            Log.i("sys","+++++++++++++");
        }
        calendar.setTime(getDateFromString(year, month));
        int weekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if(weekday < 0){
            weekday = 0;
        }
        return weekday;
    }
    public static int getNumberWeek(int year,int month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateFromString(year, month));
        return calendar.getMaximum(Calendar.WEEK_OF_MONTH);
    }
    public static Date getDateFromString(int year,int month){
        String dateString = year+"-"+(month > 9 ? month : ("0"+month)) + "-01";
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static boolean isToday(CustomDate date){
        return(date.getYear() == DateUtils.getYear() &&
                date.getMonth() == DateUtils.getMonth()
                && date.getDay() == DateUtils.getCurrentMonthDay());
    }

    public static boolean isCurrentMonth(CustomDate date){
        return(date.getYear() == DateUtils.getYear() &&
                date.getMonth() == DateUtils.getMonth());
    }
}
