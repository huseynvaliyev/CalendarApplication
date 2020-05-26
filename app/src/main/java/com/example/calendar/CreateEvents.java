package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class CreateEvents extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;

    SharedPreferences sharedPref;
    public static final String MYPREFERENCES = "MYPrefs" ;
    public static  final String DarkModeCheck = "darkModeCheckKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_events);

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

        if(darkMode!=true){
            ConstraintLayout layaut = findViewById(R.id.create);
            layaut.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }else{
            TextView dateText = findViewById(R.id.textDate);
            TextView nameText = findViewById(R.id.textName);
            TextView startText = findViewById(R.id.textStart);
            TextView endText = findViewById(R.id.textEnd);
            TextView repeatedText = findViewById(R.id.textRepeated);
            TextView addressText = findViewById(R.id.textAddress);

            dateText.setTextColor(Color.parseColor("#FFFFFF"));
            nameText.setTextColor(Color.parseColor("#FFFFFF"));
            startText.setTextColor(Color.parseColor("#FFFFFF"));
            endText.setTextColor(Color.parseColor("#FFFFFF"));
            repeatedText.setTextColor(Color.parseColor("#FFFFFF"));
            addressText.setTextColor(Color.parseColor("#FFFFFF"));
        }


        Bundle bundle = getIntent().getExtras();
        final String date = bundle.getString("date");

        final EditText dateText = findViewById(R.id.editTextDate);
        dateText.setText(date);

        final EditText eventName = findViewById(R.id.editTextName);
        final EditText startHour = findViewById(R.id.editTextStart);
        final EditText endHour = findViewById(R.id.editTextEnd);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.chooser, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        final EditText address = findViewById(R.id.editTextAddress);

        Button saveButton = findViewById(R.id.saveButton);

        final EventSource database = new EventSource(this);
        database.open();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date = String.valueOf(dateText.getText());
                String name = String.valueOf(eventName.getText());
                String start = String.valueOf(startHour.getText());
                String end = String.valueOf(endHour.getText());
                String repeat = spinner.getSelectedItem().toString();
                String addres = String.valueOf(address.getText());

                String dateString = String.valueOf(dateText.getText());

                int first = dateString.indexOf("/");
                String dayWithYear = dateString.substring(first + 1);
                int last = dayWithYear.indexOf("/");
                String monthString = dateString.substring(0, first);
                String dayString = dayWithYear.substring(0, last);
                String yearString = dayWithYear.substring(last + 1);
                Events newEvent = new Events(name,date,dayString,monthString,yearString,start,end,repeat,addres);
                database.createEvent(newEvent);
                Intent intent = new Intent(CreateEvents.this, EventList.class);
                Bundle bundle = new Bundle();
                bundle.putString("date",date);
                intent.putExtras(bundle);
                startActivity(intent);
                database.close();

            }
        });


    }

    public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
