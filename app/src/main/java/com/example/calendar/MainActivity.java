package com.example.calendar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.Switch;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.myToolBar);
        CalendarView mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        setSupportActionBar(toolbar);

        Switch switchButton = findViewById(R.id.switchAB);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println(isChecked);
            }
        });

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int day) {
                String date = day + "/" + month + "/"+ year;
                Intent intent = new Intent(MainActivity.this, EventList.class);
                Bundle bundle = new Bundle();
                bundle.putString("date",date);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}


