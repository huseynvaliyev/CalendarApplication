package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class EventList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        ImageButton addButton = findViewById(R.id.addButton);

        Bundle bundle = getIntent().getExtras();
        final String date = bundle.getString("date");

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
