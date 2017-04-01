package com.example.prachipc.reminderapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class editactivity extends AppCompatActivity {
    EditText title;
    TextView date;
    TextView time;
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editactivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title=(EditText) findViewById(R.id.entertitle);
        date=(TextView) findViewById(R.id.enterdate);
        time=(TextView) findViewById(R.id.entertime);
        description=(EditText) findViewById(R.id.enterdes);
        Intent i=getIntent();
        title.setText(i.getStringExtra("title"));
        date.setText(i.getStringExtra("date"));
        time.setText(i.getStringExtra("time"));
        description.setText(i.getStringExtra("description"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=getIntent();
                String text=title.getText().toString();
                String datetext=date.getText().toString();
                String timetext=time.getText().toString();
                String destext=description.getText().toString();
                i.putExtra("Title",text);
                i.putExtra("date",datetext);
                i.putExtra("Time",timetext);
                i.putExtra("Description",destext);
                setResult(Activity.RESULT_OK,i);
                finish();


            }
        });
    }

}
