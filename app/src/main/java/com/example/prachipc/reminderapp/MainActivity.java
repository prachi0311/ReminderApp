package com.example.prachipc.reminderapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView title;
    TextView date;
    TextView time;
    TextView description;
    ImageView edit;
    Reminder reminder;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title=(TextView) findViewById(R.id.displaytitle);
        date=(TextView) findViewById(R.id.displaydate);
        time=(TextView) findViewById(R.id.displaytime);
        description=(TextView)findViewById(R.id.displaydes);
        edit=(ImageView)findViewById(R.id.edit);
        Intent i=getIntent();
        title.setText(i.getStringExtra("Title"));
        date.setText(i.getStringExtra("Date"));
        time.setText(i.getStringExtra("Time"));
        description.setText(i.getStringExtra("Description"));
        position=i.getIntExtra("position",0);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(MainActivity.this, editactivity.class);
                i.putExtra("Title",title.getText().toString());
                i.putExtra("Date",date.getText().toString());
                i.putExtra("Time",time.getText().toString());
                i.putExtra("Description",description.getText().toString());

//                startActivity(i);
                startActivityForResult(i, 1);


            }
        });

            }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode== Activity.RESULT_OK){
                title.setText(data.getStringExtra("Title"));
                date.setText(data.getStringExtra("Date"));
                time.setText(data.getStringExtra("Time"));
                description.setText(data.getStringExtra("Description"));
                setResult(Activity.RESULT_OK,data);



            }
        }

    }
}

