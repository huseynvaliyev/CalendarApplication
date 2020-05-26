package com.example.calendar;

public class Events {
    private String eventName;
    private String time;
    private String date;
    private String month;
    private String year;
    private String startTime;
    private String endTime;
    private String repeated;
    private String address;

    public Events(String eventName, String time, String date, String month, String year, String startTime, String endTime, String repeated, String address) {
        this.eventName = eventName;
        this.time = time;
        this.date = date;
        this.month = month;
        this.year = year;
        this.startTime = startTime;
        this.endTime = endTime;
        this.repeated = repeated;
        this.address = address;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

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

    public String getRepeated() {
        return repeated;
    }

    public void setRepeated(String repeated) {
        this.repeated = repeated;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
