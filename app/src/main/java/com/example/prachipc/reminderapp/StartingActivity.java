package com.example.prachipc.reminderapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Calendar;

public class StartingActivity extends AppCompatActivity {
    ArrayList<Reminder> reminder=new ArrayList<>();
     Reminderadapter adapter;
    TextView datetext;
    EditText titletext;
    TextView timetext;
    CheckBox checkbox;
    Calendar calendar;
    EditText descriptiontext;
    String[] titleArray;
    Button delete;
    String title,date,time,description;
    int vmonth,vday,vyear,vhour,vmin;
    SharedPreferences pk;
    SharedPreferences.Editor editor;
   // String[] titleArray;
    //SystemClock clock;
    DatePickerDialog datePickerDialog;
    TimePickerDialog TimePickerDilog;
  //  int hour,min;
    int Year, Month, Day;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        ListView listview = (ListView) findViewById(R.id.listview);
        pk = getSharedPreferences("ReminderApp", MODE_PRIVATE);
        title=pk.getString("Titles",null);
        editor=pk.edit();
        //editor.remove("Title");
        titleArray=title.split(",");
        if(titleArray!=null) {
            for (int i = 0; i < titleArray.length; i++) {
                Reminder m = new Reminder(titleArray[i], "date", "time");
                reminder.add(m);
            }
        }
      //  editor.clear().commit();
        adapter=new Reminderadapter(this,reminder);
        listview.setAdapter(adapter);
        calendar = Calendar.getInstance();



        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               // Toast.makeText(StartingActivity.this,"show task",Toast.LENGTH_SHORT).show();
//                View v = getLayoutInflater().inflate(R.layout.dialogue, null);
//                titletext=(EditText) v.findViewById(R.id.entertitle);
//                datetext=(TextView) v.findViewById(R.id.enterdate);
//                timetext=(TextView) v.findViewById(R.id.entertime);
//                descriptiontext=(EditText ) v.findViewById(R.id.enterdes);
              //  Toast.makeText(StartingActivity.this, "LKKKKKKKK", Toast.LENGTH_SHORT).show();


                        checkbox=(CheckBox) view.findViewById(R.id.listitemtitle);
                        if(checkbox.isChecked()){
                            reminder.remove(position);
                            adapter.notifyDataSetChanged();
                        }




                Intent i=getIntent();
                //i.putExtra("reminder",reminder);
                i.putExtra("position",position);
                i.putExtra("Title",reminder.get(position).title);
                i.putExtra("Date",reminder.get(position).date);
                i.putExtra("Time",reminder.get(position).time);
                i.putExtra("Description",reminder.get(position).description);
                i.setClass(StartingActivity.this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(i,1);

            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                delete=(Button)view.findViewById(R.id.deletebutton);

                if(delete.getVisibility()==View.VISIBLE)

                    delete.setVisibility(View.INVISIBLE);
                else
                   delete.setVisibility(View.VISIBLE);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reminder.remove(position);
                        adapter.notifyDataSetChanged();
                        for (int i=0;i<reminder.size();i++){
                            title=title+","+reminder.get(i).title;
                        }
                        editor.putString("Titles",title);
                        editor.commit();
                    }
                });

                return true;

            }
        });




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
//                Intent i=new Intent();
//                i.setClass(StartingActivity.this,MainActivity.class);
//                startActivity(i);
                AlertDialog.Builder dialog = new AlertDialog.Builder(StartingActivity.this);
                View v = getLayoutInflater().inflate(R.layout.dialogue, null);
                dialog.setView(v);
                datetext = (TextView) v.findViewById(R.id.enterdate);
                datetext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePicker(StartingActivity.this,Year,Month,Day);
                    }
                });
                timetext=(TextView) v.findViewById(R.id.entertime);
                timetext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showtimepicker(StartingActivity.this,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));
                    }
                });
                descriptiontext=(EditText) v.findViewById(R.id.enterdes);


                titletext=(EditText) v.findViewById(R.id.entertitle);
                dialog.setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Reminder m=new Reminder(titletext.getText().toString(),datetext.getText().toString(),timetext.getText().toString());
                        m.description=descriptiontext.getText().toString();
                        reminder.add(m);
                        adapter.notifyDataSetChanged();
                        if(title!=null)
                        title=title+","+titletext.getText().toString();
                        else
                         title=titletext.getText().toString();
                        editor.putString("Titles",title);


                       // editor.clear();
                        editor.commit();
                       // titleArray=title.split(",");

                      //  getNotification(StartingActivity.this,"bleh");

//                        calendar.set(vyear,vmonth,vday,vhour,vmin);
//                        Intent notificationmassage = new Intent(getApplicationContext(),NotificationClass.class);
//
//                        //This is alarm manager
//                        PendingIntent pi = PendingIntent.getBroadcast(StartingActivity.this, 0 , notificationmassage, PendingIntent.FLAG_UPDATE_CURRENT);
//                        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//                        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                                AlarmManager.INTERVAL_DAY, pi);

                    }
                });
                dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialog.create().show();
            }



            });
        }


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
      //  client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    public Action getIndexApiAction() {
//        Thing object = new Thing.Builder()
//                .setName("Starting Page") // TODO: Define a title for the content shown.
//                // TODO: Make sure this auto-generated URL is correct.
//                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
//                .build();
//        return new Action.Builder(Action.TYPE_VIEW)
//                .setObject(object)
//                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
//                .build();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        AppIndex.AppIndexApi.start(client, getIndexApiAction());
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        AppIndex.AppIndexApi.end(client, getIndexApiAction());
//        client.disconnect();
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode== Activity.RESULT_OK){
                int position=data.getIntExtra("position",0);
                reminder.get(position).title=data.getStringExtra("Title");
                reminder.get(position).date=data.getStringExtra("Date");
                adapter.notifyDataSetChanged();



            }
        }

    }
    public void showDatePicker(Context context, int Year, int Month, int Day) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(StartingActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int year , int month, int day) {
                        //this condition is necessary to work properly on all android versions
                       // datetext.setText(day +"/"+month+"/"+year);
                        if(datepicker.isShown()){
                            //You now have the selected year, month and day
                            vyear=year;
                            vmonth=month+1;
                            vday=day;

                            datetext.setText(day +"/"+vmonth+"/"+year);
                        }

                    }
                }, Year, Month , Day);

        //Call show() to simply show the dialog
        datePickerDialog.show();

    }
    public void showtimepicker(Context context, int hour, final int min) {
        final TimePickerDialog time = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                vhour = hourOfDay;
                vmin = minute;
                timetext.setText(hourOfDay + " : " + minute);

            }
        }, hour, min, false);


        time.setTitle("Select Time");
        time.show();
    }

    public void getNotification(Context context,String Message){
        int icon = R.drawable.bell;
        int when =(int) System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, Message, when);

        String title = context.getString(R.string.app_name);

        Intent notificationIntent = new Intent(context, MainActivity.class);

        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent intent =PendingIntent.getActivity(context, 0, notificationIntent, 0);
       // notification.setLatestEventInfo(context, title,Message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);

    }

    }

