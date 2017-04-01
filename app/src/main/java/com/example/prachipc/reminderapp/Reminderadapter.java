package com.example.prachipc.reminderapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by PRACHI PC on 2/16/2017.
 */

public class Reminderadapter extends ArrayAdapter<Reminder>{
    Context mcontext;
    ArrayList<Reminder> mreminder;
//    SharedPreferences sp=getSharedPreferences

    String titles;


    public Reminderadapter(Context context, ArrayList<Reminder> objects) {
        super(context,0, objects);
        mcontext=context;
        mreminder=objects;

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(convertView==null){
          convertView=View.inflate(mcontext, R.layout.listitem,null)  ;
        }
        final Reminder reminder=mreminder.get(position);
        final Button delete=(Button) convertView.findViewById(R.id.deletebutton);
       final CheckBox title=(CheckBox) convertView.findViewById(R.id.listitemtitle);
        title.setText(reminder.title);
        TextView date=(TextView) convertView.findViewById(R.id.listitemdate);
        date.setText(reminder.date);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( title.isChecked()) {
                    mreminder.remove(position);
                  //  Toast.makeText(Reminderadapter.this,"task finished",Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }


            }
        });
//        convertView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if(delete.getVisibility()==View.VISIBLE)
//                    delete.setVisibility(View.INVISIBLE);
//                else
//                   delete.setVisibility(View.VISIBLE);
//                return true;
//            }
//        });
//        delete.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                mreminder.remove(position);
//                notifyDataSetChanged();
//                for (int i=0;i<mreminder.size();i++){
//                    titles=titles+","+mreminder.get(i).title;
//                    editor.putString("Titles",titles);
//                    editor.commit();
//
//                }
            //}
       // });

        return convertView;
    }
}
