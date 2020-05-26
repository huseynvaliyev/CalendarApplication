package com.example.calendar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    SharedPreferences sharedPref;

    public static final String MYPREFERENCES = "MYPrefs" ;
    public static  final String DarkModeCheck = "darkModeCheckKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.myToolBar);
        CalendarView mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        setSupportActionBar(toolbar);

        Switch switchButton = findViewById(R.id.switchAB);
        sharedPref = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE);
        boolean darkMode = sharedPref.getBoolean("darkModeCheckKey", true);
        switchButton.setChecked(darkMode);

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(DarkModeCheck,isChecked).commit();
            }
        });

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int day) {
                month = month +1;
                String date = day + "/" + month + "/"+ year;
                Intent intent = new Intent(MainActivity.this, EventList.class);
                Bundle bundle = new Bundle();
                bundle.putString("date",date);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        ArrayList <Events> currentEvents = new ArrayList<Events>();
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        int first = currentDate.indexOf("/");
        String dayWithYear = currentDate.substring(first + 1);
        int last = dayWithYear.indexOf("/");
        String monthString = currentDate.substring(0, first);
        String dayString = dayWithYear.substring(0, last);
        String yearString = dayWithYear.substring(last + 1);

        if(dayString.startsWith("0")){
            dayString = dayString.substring(1);
        }

        String newCurrentDate = monthString+"/"+dayString+"/"+yearString;
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        final EventSource database = new EventSource(this);
        database.open();
        currentEvents = database.getEvents(newCurrentDate);

        for (Events event: currentEvents){
            if(event.getStartTime().equals(currentTime)){
                createNotificationChannel(event.getEventName(),event.getAddress());
                Toast.makeText(getApplicationContext(),"Reminder Set",Toast.LENGTH_SHORT).show();
                Intent notifyIntent = new Intent(MainActivity.this,NotificationBroadcast.class);
                Bundle eventBundle = new Bundle();
                eventBundle.putString("name", event.getEventName());
                eventBundle.putString("description",event.getAddress() );
                notifyIntent.putExtras(eventBundle);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0, notifyIntent,0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

                long timeAtCreate = System.currentTimeMillis();
                long tenSecInMillis = 1000 * 10;

                alarmManager.set(AlarmManager.RTC_WAKEUP,timeAtCreate+tenSecInMillis,pendingIntent);
            }
        }




    }
    private void  createNotificationChannel(String eventName, String eventDescription){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = eventName;
            String description = eventDescription;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("eventNotify",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}


