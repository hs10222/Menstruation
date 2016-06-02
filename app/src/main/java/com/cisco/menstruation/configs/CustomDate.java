package com.cisco.menstruation.configs;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * custom Date class
 * Created by Cisco.hu on 2016/4/1.
 */
public class CustomDate implements Parcelable{
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mWeek;

    public CustomDate(){
        mYear = DateUtils.getYear();
        mMonth = DateUtils.getMonth();
        mDay = DateUtils.getCurrentMonthDay();
        mWeek = DateUtils.getWeekDay();
    }
    public CustomDate(int year,int month,int day){
        if(month > 12){
            month = 1;
            year++;
        }else if(month < 1){
            month = 12;
            year--;
        }
        mYear = year;
        mMonth = month;
        mDay = day;
    }
    protected CustomDate(Parcel in) {
        mYear = in.readInt();
        mMonth = in.readInt();
        mDay = in.readInt();
        mWeek = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mYear);
        dest.writeInt(mMonth);
        dest.writeInt(mDay);
        dest.writeInt(mWeek);
    }

    public static final Creator<CustomDate> CREATOR = new Creator<CustomDate>() {
        @Override
        public CustomDate createFromParcel(Parcel in) {
            return new CustomDate(in);
        }

        @Override
        public CustomDate[] newArray(int size) {
            return new CustomDate[size];
        }
    };
    public static CustomDate modifiDayForObject(CustomDate date,int day){
        CustomDate modifiDate = new CustomDate(date.getYear(),date.getMonth(),day);
        return modifiDate;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int Year) {
        this.mYear = mYear;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setMonth(int Month) {
        this.mMonth = Month;
    }

    public int getDay() {
        return mDay;
    }

    public void setDay(int Day) {
        this.mDay = Day;
    }

    public int getmWeek() {
        return mWeek;
    }

    public void setmWeek(int Week) {
        this.mWeek = Week;
    }
}
