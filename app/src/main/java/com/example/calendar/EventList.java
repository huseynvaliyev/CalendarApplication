package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import java.util.ArrayList;

public class EventList extends AppCompatActivity {

    SharedPreferences sharedPref;

    public static final String MYPREFERENCES = "MYPrefs" ;
    public static  final String DarkModeCheck = "darkModeCheckKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

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

        ImageButton addButton = findViewById(R.id.addButton);

        Bundle bundle = getIntent().getExtras();
        final String date = bundle.getString("date");

        final EventSource database = new EventSource(this);
        database.open();

        ArrayList<Events> eventlist = database.getEvents(date);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recylerview);


        if(darkMode==true){
            EventAdapter eventAdapter = new EventAdapter(this,eventlist);
            recyclerView.setAdapter(eventAdapter);
        }else{
            LightEventAdapter lightEventAdapter = new LightEventAdapter(this,eventlist);
            recyclerView.setAdapter(lightEventAdapter);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventList.this, CreateEvents.class);
                Bundle bundle = new Bundle();
                bundle.putString("date",date);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
