package com.example.calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class EventSource {
    SQLiteDatabase db;
    SQLiteOpener eventDb;

    public EventSource(Context c){
        eventDb = new SQLiteOpener(c);
    }

    public void open(){
        db = eventDb.getWritableDatabase();
    }

    public void close(){
        eventDb.close();
    }

    public void createEvent(Events newEvent){
        ContentValues value = new ContentValues();
        value.put("eventName",newEvent.getEventName());
        value.put("time",newEvent.getTime());
        value.put("date",newEvent.getDate());
        value.put("month",newEvent.getMonth());
        value.put("year",newEvent.getYear());
        value.put("startTime",newEvent.getStartTime());
        value.put("endTime",newEvent.getEndTime());
        value.put("repeated",newEvent.getRepeated());
        value.put("address",newEvent.getAddress());
        db.insert("events",null,value);
    }

    public void deleteEvent(Events event){
        String eventName = event.getEventName();
        String time = event.getTime();
        String date = event.getDate();
        String month = event.getMonth();
        String year = event.getYear();
        String startTime = event.getStartTime();
        String endTime = event.getEndTime();
        String repeated = event.getRepeated();
        String address = event.getAddress();
        db.delete("events", "eventName='" + eventName + "'AND time='" + time + "'AND date='" + date + "'AND month='" + month + "'AND year='" + year + "' AND startTime='" + startTime + "'AND endTime='" + endTime + "'AND repeated='" + repeated + "'AND address='" + address + "'", null);
    }

    public ArrayList<Events> getEvents(String eventDate){
        String events[] = {"eventName","time","date","month","year","startTime","endTime","repeated","address"};
        ArrayList<Events> eventList = new ArrayList<Events>();
        Cursor c = db.rawQuery("select * from events where time='"+eventDate+"'order by startTime asc",null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            String eventName = c.getString(0);
            String time = c.getString(1);
            String date = c.getString(2);
            String month = c.getString(3);
            String year = c.getString(4);
            String startTime = c.getString(5);
            String endTime = c.getString(6);
            String repeated = c.getString(7);
            String address = c.getString(8);
            Events takenEvent = new Events(eventName,time,date,month,year,startTime,endTime,repeated,address);
            eventList.add(takenEvent);
            c.moveToNext();
        }
        c.close();
        return eventList;
    }
}
