package com.unist.am.lineup;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by JeonghyunLee on 2015-09-25.
 */
public class Number_Customer extends Dialog {

    ImageView btn1;
    ImageView btn2;
    ImageView btn3;
    ImageView btn4;
    ImageView btn5;
    ImageView btn6;
    int Table_id;
    int No=0;
    ArrayList<Integer> table_list;
    ArrayList<String> table_time_list;
    public Number_Customer(Context context) {
        super(context);
    }
    public Number_Customer(Context context, ArrayList<Integer> list, ArrayList<String> times) {
        super(context);
        table_list = list;
        table_time_list= times;
    }


    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        setContentView(R.layout.table_sit);

        btn1 = (ImageView) findViewById(R.id.selection_1_table);
        btn2 = (ImageView) findViewById(R.id.selection_2_table);
        btn3 = (ImageView) findViewById(R.id.selection_3_table);
        btn4 = (ImageView) findViewById(R.id.selection_4_table);
        btn5 = (ImageView) findViewById(R.id.selection_5_table);
        btn6 = (ImageView) findViewById(R.id.selection_6_table);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                No = 1;
                cancel();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                No = 2;
                cancel();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                No = 3;
                cancel();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                No = 4;
                cancel();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                No = 5;
                cancel();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                No = 6;
                cancel();
            }
        });



    }

}
