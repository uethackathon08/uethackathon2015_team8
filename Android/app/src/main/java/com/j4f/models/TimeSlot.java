package com.j4f.models;

import java.util.Calendar;

/**
 * Created by TuanTQ on 11/21/15.
 */
public class TimeSlot {
    private String date;
    private String time;
    private Calendar datetime;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Calendar getDatetime() {
        return datetime;
    }

    public void setDatetime(Calendar datetime) {
        this.datetime = datetime;
    }

    public TimeSlot(String mDate, String mTime, Calendar mDatetime) {

        this.date = mDate;
        this.time = mTime;
        this.datetime = mDatetime;
    }
}
