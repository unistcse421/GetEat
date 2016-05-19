/**
 * Copyright 2014 Daum Kakao Corp.
 *
 * Redistribution and modification in source or binary forms are not permitted without specific prior written permission. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.unist.am.lineup;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 유효한 세션이 있다는 검증 후
 * me를 호출하여 가입 여부에 따라 가입 페이지를 그리던지 Main 페이지로 이동 시킨다.
 */

public class TableActivity extends Activity {

    private Context mcontext;
    private TextView table1_1;
    private TextView table1_2;
    private TextView table2_1;
    private TextView table2_2;
    private TextView table3_1;
    private TextView table3_2;
    private TextView table4_1;
    private TextView table4_2;
    private TextView table5_1;
    private TextView table5_2;
    private TextView table6_1;
    private TextView table6_2;
    private TextView table7_1;
    private TextView table7_2;
    private TextView table8_1;
    private TextView table8_2;

    private FrameLayout tb1_1;
    private FrameLayout tb1_2;
    private FrameLayout tb2_1;
    private FrameLayout tb2_2;
    private FrameLayout tb3_1;
    private FrameLayout tb3_2;
    private FrameLayout tb4_1;
    private FrameLayout tb4_2;
    private FrameLayout tb5_1;
    private FrameLayout tb5_2;
    private FrameLayout tb6_1;
    private FrameLayout tb6_2;
    private FrameLayout tb7_1;
    private FrameLayout tb7_2;
    private FrameLayout tb8_1;
    private FrameLayout tb8_2;

    private RelativeLayout tb1;
    private RelativeLayout tb2;
    private RelativeLayout tb3;
    private RelativeLayout tb4;
    private RelativeLayout tb5;
    private RelativeLayout tb6;
    private RelativeLayout tb7;
    private RelativeLayout tb8;

    TextView Sit;
    TextView Out;
    TextView Go_to_List;
    ImageView cle1;
    ImageView cle2;
    ImageView cle3;

// 그냥 state는 가게단에서 하는 state변경값들 저장

    //비어있을 떄 off
    static Boolean state1 = false;
    static Boolean state2 = false;
    static Boolean state3 = false;
    static Boolean state4 = false;
    static Boolean state5 = false;
    static Boolean state6 = false;
    static Boolean state7 = false;
    static Boolean state8 = false;


    // _2 state는 서버에서 가져와 맨처음 앱 실행시 사용
    static Boolean state1_2 = false;
    static Boolean state2_2 = false;
    static Boolean state3_2 = false;
    static Boolean state4_2 = false;
    static Boolean state5_2 = false;
    static Boolean state6_2 = false;
    static Boolean state7_2 = false;
    static Boolean state8_2 = false;
    static Boolean state_check = null;

    static TextView check;

    int checkBtn_color;
    Number_Customer mdialog;

    ArrayList<String>num_people = null;
    ArrayList<String> start_time = null;
    ArrayList<Integer> selected_tables = null;
    ArrayList<String>selected_table= null;
    String current_times;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_table);
        //     setContentView(R.layout.table_management);

        table1_1 = (TextView) findViewById(R.id.tb1_txt1);
        table1_2 = (TextView) findViewById(R.id.tb1_txt2);
        table2_1 = (TextView) findViewById(R.id.tb2_txt1);
        table2_2 = (TextView) findViewById(R.id.tb2_txt2);
        table3_1 = (TextView) findViewById(R.id.tb3_txt1);
        table3_2 = (TextView) findViewById(R.id.tb3_txt2);
        table4_1 = (TextView) findViewById(R.id.tb4_txt1);
        table4_2 = (TextView) findViewById(R.id.tb4_txt2);
        table5_1 = (TextView) findViewById(R.id.tb5_txt1);
        table5_2 = (TextView) findViewById(R.id.tb5_txt2);
        table6_1 = (TextView) findViewById(R.id.tb6_txt1);
        table6_2 = (TextView) findViewById(R.id.tb6_txt2);
        table7_1 = (TextView) findViewById(R.id.tb7_txt1);
        table7_2 = (TextView) findViewById(R.id.tb7_txt2);
        table8_1 = (TextView) findViewById(R.id.tb8_txt1);
        table8_2 = (TextView) findViewById(R.id.tb8_txt2);

        tb1_1 = (FrameLayout) findViewById(R.id.tb1_box1);
        tb1_2 = (FrameLayout) findViewById(R.id.tb1_box2);
        tb2_1 = (FrameLayout) findViewById(R.id.tb2_box1);
        tb2_2 = (FrameLayout) findViewById(R.id.tb2_box2);
        tb3_1 = (FrameLayout) findViewById(R.id.tb5_box1);
        tb3_2 = (FrameLayout) findViewById(R.id.tb5_box2);
        tb4_1 = (FrameLayout) findViewById(R.id.tb6_box1);
        tb4_2 = (FrameLayout) findViewById(R.id.tb6_box2);
        tb5_1 = (FrameLayout) findViewById(R.id.tb9_box1);
        tb5_2 = (FrameLayout) findViewById(R.id.tb9_box2);
        tb6_1 = (FrameLayout) findViewById(R.id.tb10_box1);
        tb6_2 = (FrameLayout) findViewById(R.id.tb10_box2);
        tb7_1 = (FrameLayout) findViewById(R.id.tb13_box1);
        tb7_2 = (FrameLayout) findViewById(R.id.tb13_box2);
        tb8_1 = (FrameLayout) findViewById(R.id.tb14_box1);
        tb8_2 = (FrameLayout) findViewById(R.id.tb14_box2);

        cle1 = (ImageView) findViewById(R.id.circle);
        cle2 = (ImageView) findViewById(R.id.circle2);
        cle3 = (ImageView) findViewById(R.id.circle3);

        Sit = (TextView) findViewById(R.id.sit_table);
        Out = (TextView) findViewById(R.id.out_table);
        check = (TextView) findViewById(R.id.ok_table);
        check.setEnabled(false);
        cle3.setBackgroundResource(R.drawable.round_logo_blocked);
        Go_to_List = (TextView) findViewById(R.id.list_table);
        Go_to_List.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mintent = new Intent(mcontext,OwnerActivity.class);
                startActivity(mintent);

            }
        });
        tb1 = (RelativeLayout) findViewById(R.id.tb1);
        tb2 = (RelativeLayout) findViewById(R.id.tb2);
        tb3 = (RelativeLayout) findViewById(R.id.tb5);
        tb4 = (RelativeLayout) findViewById(R.id.tb6);
        tb5 = (RelativeLayout) findViewById(R.id.tb9);
        tb6 = (RelativeLayout) findViewById(R.id.tb10);
        tb7 = (RelativeLayout) findViewById(R.id.tb13);
        tb8 = (RelativeLayout) findViewById(R.id.tb14);
        mcontext = this;
        setEnabledOfTb(false);
        state1 = false;state2 = false;state3 = false;state4 = false;state5 = false;state6 = false;state7 = false;state8 = false;
        HttpPostRequest Reception = new HttpPostRequest();
        try {
            Reception.execute();

            Log.e("server", "완료");
        }catch (Exception e) {

            Log.e("DB", e.toString());
        }
        /*
manager = new DBManager_reserv(context, "reserv_info.db", null, 1);
        서버에서 시작시간과 stete 로드하여 각각 start_from_server 와 state_2에 저장

         */
        mdialog = new Number_Customer(mcontext);
        Sit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Out.setEnabled(false);
                Sit.setEnabled(false);
                cle1.setBackgroundResource(R.drawable.round_logo_blocked);
                cle2.setBackgroundResource(R.drawable.round_logo_blocked);
                cle3.setBackgroundResource(R.drawable.round_logo);
                check.setEnabled(true);
                setEnabledOfTb(true);
                current_times = String.valueOf(System.currentTimeMillis());
                tb1.setOnClickListener(new Click(1, true));
                tb2.setOnClickListener(new Click(2, true));
                tb3.setOnClickListener(new Click(3, true));
                tb4.setOnClickListener(new Click(4, true));
                tb5.setOnClickListener(new Click(5, true));
                tb6.setOnClickListener(new Click(6, true));
                tb7.setOnClickListener(new Click(7, true));
                tb8.setOnClickListener(new Click(8, true));
                check.setOnClickListener(new Click(9, true));

            }
        });
        Out.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Sit.setEnabled(false);
                Out.setEnabled(false);
                current_times = String.valueOf(System.currentTimeMillis());
                cle1.setBackgroundResource(R.drawable.round_logo_blocked);
                cle2.setBackgroundResource(R.drawable.round_logo_blocked);
                cle3.setBackgroundResource(R.drawable.round_logo);
                check.setEnabled(true);
                setEnabledOfTb(true);
                tb1.setOnClickListener(new Click(1, false));
                tb2.setOnClickListener(new Click(2, false));
                tb3.setOnClickListener(new Click(3, false));
                tb4.setOnClickListener(new Click(4, false));
                tb5.setOnClickListener(new Click(5, false));
                tb6.setOnClickListener(new Click(6, false));
                tb7.setOnClickListener(new Click(7, false));
                tb8.setOnClickListener(new Click(8, false));
                check.setOnClickListener(new Click(10, false));

            }
        });

        //     mdialog.setCancelable(false);
        mdialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }
        });
        mdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                new HttpPostRequest3().execute("");
                if (mdialog.No != 0) {
                    for(int i = 0 ; i < selected_tables.size(); i++){
                        TextPutter(selected_tables.get(i));
                    }
                    // 서버에 시간저장
                    Out.setEnabled(true);
                    Sit.setEnabled(true);
                    check.setEnabled(false);
                    cle1.setBackgroundResource(R.drawable.round_logo);
                    cle2.setBackgroundResource(R.drawable.round_logo);
                    cle3.setBackgroundResource(R.drawable.round_logo_blocked);
                    setEnabledOfTb(false);
                }
                state1_2 = state1;
                state2_2 = state2;
                state3_2 = state3;
                state4_2 = state4;
                state5_2 = state5;
                state6_2 = state6;
                state7_2 = state7;
                state8_2 = state8;
            }
        });

    }

    void TextPutter(int id) {
        switch (id) {
            case 1:
                tb1_2.setVisibility(View.VISIBLE);
                tb1_1.setVisibility(View.VISIBLE);
                table1_1.setText(String.valueOf(mdialog.No));
                table1_2.setText(getDate(String.valueOf(current_times)));
                tb1.setBackgroundResource(R.drawable.round_box_ing);
                break;

            case 2:
                tb2_2.setVisibility(View.VISIBLE);
                tb2_1.setVisibility(View.VISIBLE);
                table2_1.setText(String.valueOf(mdialog.No));
                table2_2.setText(getDate(String.valueOf(current_times)));
                tb2.setBackgroundResource(R.drawable.round_box_ing);
                break;

            case 3:
                tb3_2.setVisibility(View.VISIBLE);
                tb3_1.setVisibility(View.VISIBLE);
                table3_1.setText(String.valueOf(mdialog.No));
                table3_2.setText(getDate(String.valueOf(current_times)));
                tb3.setBackgroundResource(R.drawable.round_box_ing);
                break;

            case 4:
                tb4_2.setVisibility(View.VISIBLE);
                tb4_1.setVisibility(View.VISIBLE);
                table4_1.setText(String.valueOf(mdialog.No));
                table4_2.setText(getDate(String.valueOf(current_times)));
                tb4.setBackgroundResource(R.drawable.round_box_ing);
                break;

            case 5:
                tb5_2.setVisibility(View.VISIBLE);
                tb5_1.setVisibility(View.VISIBLE);
                table5_1.setText(String.valueOf(mdialog.No));
                table5_2.setText(getDate(String.valueOf(current_times)));
                tb5.setBackgroundResource(R.drawable.round_box_ing);
                break;
            case 6:
                tb6_2.setVisibility(View.VISIBLE);
                tb6_1.setVisibility(View.VISIBLE);
                table6_1.setText(String.valueOf(mdialog.No));
                table6_2.setText(getDate(String.valueOf(current_times)));
                tb6.setBackgroundResource(R.drawable.round_box_ing);
                break;
            case 7:
                tb7_2.setVisibility(View.VISIBLE);
                tb7_1.setVisibility(View.VISIBLE);
                table7_1.setText(String.valueOf(mdialog.No));
                table7_2.setText(getDate(String.valueOf(current_times)));
                tb7.setBackgroundResource(R.drawable.round_box_ing);
                break;
            case 8:
                tb8_2.setVisibility(View.VISIBLE);
                tb8_1.setVisibility(View.VISIBLE);
                table8_1.setText(String.valueOf(mdialog.No));
                table8_2.setText(getDate(String.valueOf(current_times)));
                tb8.setBackgroundResource(R.drawable.round_box_ing);
                break;
        }
    }
    void TextDeletor(int id) {
        switch (id) {
            case 1:
                tb1_2.setVisibility(View.INVISIBLE);
                tb1_1.setVisibility(View.INVISIBLE);
                tb1.setBackgroundResource(R.drawable.round_box_table);
                break;

            case 2:
                tb2_2.setVisibility(View.INVISIBLE);
                tb2_1.setVisibility(View.INVISIBLE);
                tb2.setBackgroundResource(R.drawable.round_box_table);
                break;

            case 3:
                tb3_2.setVisibility(View.INVISIBLE);
                tb3_1.setVisibility(View.INVISIBLE);
                tb3.setBackgroundResource(R.drawable.round_box_table);
                break;

            case 4:
                tb4_2.setVisibility(View.INVISIBLE);
                tb4_1.setVisibility(View.INVISIBLE);
                tb4.setBackgroundResource(R.drawable.round_box_table);
                break;

            case 5:
                tb5_2.setVisibility(View.INVISIBLE);
                tb5_1.setVisibility(View.INVISIBLE);
                tb5.setBackgroundResource(R.drawable.round_box_table);
                break;
            case 6:
                tb6_2.setVisibility(View.INVISIBLE);
                tb6_1.setVisibility(View.INVISIBLE);
                tb6.setBackgroundResource(R.drawable.round_box_table);
                break;
            case 7:
                tb7_2.setVisibility(View.INVISIBLE);
                tb7_1.setVisibility(View.INVISIBLE);
                tb7.setBackgroundResource(R.drawable.round_box_table);
                break;
            case 8:
                tb8_2.setVisibility(View.INVISIBLE);
                tb8_1.setVisibility(View.INVISIBLE);
                tb8.setBackgroundResource(R.drawable.round_box_table);
                break;
        }
    }


    public class Click implements OnClickListener {

        int mtype;
        Boolean TF;
        Click(int type, Boolean sit_out){
            TF = sit_out;
            mtype = type;
        }

        @Override
        public void onClick(View v) {
            switch(mtype){
                case 1:
                    if(state1 == false && TF) {
                        v.setBackgroundResource(R.drawable.round_box_table_selected);
                        state1 = true;
                    }
                    else if(state1 && !state1_2 && TF){
                        v.setBackgroundResource(R.drawable.round_box_table);
                        state1 = !state1;
                    }
                    else{
                        if(state1 && !TF) {
                            v.setBackgroundResource(R.drawable.round_box_ing_selected);
                            state1 = false;
                        }
                        else if(!state1 && state1_2 && !TF){
                            v.setBackgroundResource(R.drawable.round_box_ing);
                            state1 = true;
                        }
                    }
                    break;
                case 2:
                    if(state2 == false && TF) {
                        v.setBackgroundResource(R.drawable.round_box_table_selected);
                        state2 = true;
                    }
                    else if(state2 && !state2_2 && TF){
                        v.setBackgroundResource(R.drawable.round_box_table);
                        state2 = !state2;
                    }
                    else{
                        if(state2 && !TF) {
                            v.setBackgroundResource(R.drawable.round_box_ing_selected);
                            state2 = false;
                        }
                        else if(!state2 && state2_2 && !TF){
                            v.setBackgroundResource(R.drawable.round_box_ing);
                            state2 = true;

                        }
                    }

                    break;
                case 3:
                    if(state3 == false && TF) {
                        v.setBackgroundResource(R.drawable.round_box_table_selected);
                        state3 = true;
                    }
                    else if(state3 && !state3_2 && TF){
                        v.setBackgroundResource(R.drawable.round_box_table);
                        state3 = !state3;
                    }
                    else{
                        if(state3 && !TF) {
                            v.setBackgroundResource(R.drawable.round_box_ing_selected);
                            state3 = false;
                        }
                        else if(!state3 && state3_2 && !TF){
                            v.setBackgroundResource(R.drawable.round_box_ing);
                            state3 = true;

                        }
                    }

                    break;
                case 4:
                    if(state4 == false &&TF) {
                        v.setBackgroundResource(R.drawable.round_box_table_selected);
                        state4 = true;
                    }
                    else if(state4 && !state4_2 && TF){
                        v.setBackgroundResource(R.drawable.round_box_table);
                        state4 = !state4;
                    }
                    else{
                        if(state4 && !TF) {
                            v.setBackgroundResource(R.drawable.round_box_ing_selected);
                            state4 = false;
                        }
                        else if(!state4 && state4_2 && !TF){
                            v.setBackgroundResource(R.drawable.round_box_ing);
                            state4 = true;

                        }
                    }

                    break;
                case 5:
                    if(state5 == false && TF) {
                        v.setBackgroundResource(R.drawable.round_box_table_selected);
                        state5 = true;
                    }
                    else if(state5 && !state5_2 && TF){
                        v.setBackgroundResource(R.drawable.round_box_table);
                        state5 = !state5;
                    }
                    else{
                        if(state5 && !TF) {
                            v.setBackgroundResource(R.drawable.round_box_ing_selected);
                            state5 = false;
                        }
                        else if(!state5 && state5_2 && !TF){
                            v.setBackgroundResource(R.drawable.round_box_ing);
                            state5 = true;

                        }
                    }
                    break;
                case 6:
                    if(state6 == false &&TF) {
                        v.setBackgroundResource(R.drawable.round_box_table_selected);
                        state6 = true;
                    }
                    else if(state6 && !state6_2 && TF){
                        v.setBackgroundResource(R.drawable.round_box_table);
                        state6 = !state6;
                    }
                    else{
                        if(state6 && !TF) {
                            v.setBackgroundResource(R.drawable.round_box_ing_selected);
                            state6 = false;
                        }
                        else if(!state6 && state6_2 && !TF){
                            v.setBackgroundResource(R.drawable.round_box_ing);
                            state6 = true;

                        }

                    }

                    break;
                case 7:
                    if(state7 == false &&TF) {
                        v.setBackgroundResource(R.drawable.round_box_table_selected);
                        state7 = true;
                    }
                    else if(state7 && !state7_2 && TF){
                        v.setBackgroundResource(R.drawable.round_box_table);
                        state7 = !state7;
                    }
                    else{
                        if(state7 && !TF) {
                            v.setBackgroundResource(R.drawable.round_box_ing_selected);
                            state7 = false;
                        }
                        else if(!state7 && state7_2 && !TF){
                            v.setBackgroundResource(R.drawable.round_box_ing);
                            state7 = true;

                        }

                    }

                    break;
                case 8:
                    if(state8 == false &&TF) {
                        v.setBackgroundResource(R.drawable.round_box_table_selected);
                        state8 = true;
                    }
                    else if(state8 && !state8_2 && TF){
                        v.setBackgroundResource(R.drawable.round_box_table);
                        state8 = !state8;
                    }
                    else{
                        if(state8 && !TF) {
                            v.setBackgroundResource(R.drawable.round_box_ing_selected);
                            state8 = false;
                        }
                        else if(!state8 && state8_2 && !TF){
                            v.setBackgroundResource(R.drawable.round_box_ing);
                            state8 = true;

                        }
                    }

                    break;
                case 9:
                        if(TF){
                        selected_tables = new ArrayList<Integer>();
                        selected_table = new ArrayList<String>();
                        if(state1 != state1_2 && !state1_2){
                            selected_tables.add(1);
                            selected_table.add(current_times);
                        }
                        if(state2!= state2_2 && !state2_2){
                            selected_tables.add(2);
                            selected_table.add(current_times);
                        }
                        if(state3!= state3_2 && !state3_2){
                            selected_tables.add(3);
                            selected_table.add(current_times);
                        }
                        if(state4!= state4_2 && !state4_2){
                            selected_tables.add(4);
                            selected_table.add(current_times);
                        }
                        if(state5!= state5_2 && !state5_2){
                            selected_tables.add(5);
                            selected_table.add(current_times);
                        }
                        if(state6!= state6_2 && !state6_2){
                            selected_tables.add(6);
                            selected_table.add(current_times);
                        }
                        if(state7!= state7_2 && !state7_2){
                            selected_tables.add(7);
                            selected_table.add(current_times);
                        }
                        if(state8!= state8_2 && !state8_2){
                            selected_tables.add(8);
                            selected_table.add(current_times);
                        }

                            Log.e("after insert",selected_table.toString()+"\n"+selected_tables.toString());
                        mdialog.table_list = selected_tables;
                        mdialog.table_time_list = selected_table;

                    }

                    if(selected_table.size()>0) {
                        mdialog.show();
                    }
                    else{
                        Out.setEnabled(true);
                        Sit.setEnabled(true);
                        check.setEnabled(false);
                        cle1.setBackgroundResource(R.drawable.round_logo);
                        cle2.setBackgroundResource(R.drawable.round_logo);
                        cle3.setBackgroundResource(R.drawable.round_logo_blocked);
                    }
                    break;

                case 10:
                    selected_table= new ArrayList<String>();
                    selected_tables=new ArrayList<Integer>();

                    if(!state1 && state1_2){
                        selected_table.add(current_times);
                        selected_tables.add(1);
                        TextDeletor(1);
                        state1 = state1_2 = false;
                    }
                    if(!state2 && state2_2){
                        selected_table.add(current_times);
                        selected_tables.add(2);
                        state2 = state2_2 = false;
                        TextDeletor(2);
                    }

                    if(!state3 && state3_2){
                        selected_table.add(current_times);
                        selected_tables.add(3);
                        state3 = state3_2 = false;
                        TextDeletor(3);
                    }

                    if(!state4 && state4_2){
                        selected_table.add(current_times);
                        selected_tables.add(4);
                        state4 = state4_2 = false;
                        TextDeletor(4);
                    }

                    if(!state5 && state5_2){
                        selected_table.add(current_times);
                        selected_tables.add(5);
                        state5 = state5_2 = false;
                        TextDeletor(5);
                    }

                    if(!state6 && state6_2){
                        selected_table.add(current_times);
                        selected_tables.add(6);
                        state6 = state6_2 = false;
                        TextDeletor(6);
                    }

                    if(!state7 && state7_2){
                        selected_table.add(current_times);
                        selected_tables.add(7);
                        state7 = state7_2 = false;
                        TextDeletor(7);
                    }

                    if(!state8 && state8_2){
                        selected_table.add(current_times);
                        selected_tables.add(8);
                        state8 = state8_2 = false;
                        TextDeletor(8);
                    }
                    Log.e("after delete",selected_table.toString()+"\n"+selected_tables.toString());
                    if(selected_table.size() >0){
                        new HttpPostRequest2().execute("");
                    }
                    Sit.setEnabled(true);
                    Out.setEnabled(true);
                    check.setEnabled(false);
                    cle1.setBackgroundResource(R.drawable.round_logo);
                    cle2.setBackgroundResource(R.drawable.round_logo);
                    cle3.setBackgroundResource(R.drawable.round_logo_blocked);
                    setEnabledOfTb(false);



                    break;
            }


        }


    }
    String getDate(String times){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);

        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
        calendar.setTimeInMillis(Long.valueOf(times));

        return sdf.format(calendar.getTime());
    }
    public class HttpPostRequest extends AsyncTask<String,Void,String> {
        String sResult="error";
        @Override
        protected String doInBackground(String... info) {
            URL url = null;
            try {
                url = new URL("http://52.69.163.43/queuing/get_all_table_info.php?resname=sample");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                Log.e("Http connection2_http2","완료");
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder builder = new StringBuilder();

                String str;
                while ((str = reader.readLine()) != null) {
                    builder.append(str);
                }
                sResult = builder.toString();

            } catch (Exception e) {
                Log.e(e.toString()+"http1",sResult+"       "+e.toString());
            }


            return sResult;
        }
        @Override
        protected void onPostExecute(String result){

            String jsonall = result;
            JSONArray jArray = null;
            num_people = new ArrayList<String>();
            start_time = new ArrayList<String>();
            try{
                jArray = new JSONArray(jsonall);
                JSONObject json_data = null;
                Log.e("json",jArray.toString());
                for (int i = 0; i < jArray.length(); i++) {
                    json_data = jArray.getJSONObject(i);
                    num_people.add(json_data.getString("num_people"));
                    start_time.add(json_data.getString("start_time"));

                }
            }catch(Exception e){
                Log.e("error in receive",e.toString());
                e.printStackTrace();
            }
            try{
                    for (int i = 0; i < num_people.size(); i++) {
                        Log.e("num_people", i+ num_people.get(i));
                        if (!num_people.get(i).equals("0")) {
                            Log.e("why",num_people.get(i) + "\t" +i);
                            switch (i) {
                                case 0:
                                    tb1_1.setVisibility(View.VISIBLE);
                                    tb1_2.setVisibility(View.VISIBLE);
                                    table1_1.setText(num_people.get(i));
                                    tb1.setBackgroundResource(R.drawable.round_box_ing);
                                    table1_2.setText(getDate(String.valueOf(start_time.get(i))));
                                    state1 = true;
                                    break;
                                case 1:
                                    tb2_1.setVisibility(View.VISIBLE);
                                    tb2_2.setVisibility(View.VISIBLE);
                                    table2_1.setText(num_people.get(i));
                                    tb2.setBackgroundResource(R.drawable.round_box_ing);
                                    table2_2.setText(getDate(String.valueOf(start_time.get(i))));
                                    state2 = true;
                                    break;
                                case 2:
                                    tb3_1.setVisibility(View.VISIBLE);
                                    tb3_2.setVisibility(View.VISIBLE);
                                    table3_1.setText(num_people.get(i));
                                    tb3.setBackgroundResource(R.drawable.round_box_ing);
                                    table3_2.setText(getDate(String.valueOf(start_time.get(i))));
                                    state3 = true;
                                    break;
                                case 3:
                                    tb4_1.setVisibility(View.VISIBLE);
                                    tb4_2.setVisibility(View.VISIBLE);
                                    table4_1.setText(num_people.get(i));
                                    tb4.setBackgroundResource(R.drawable.round_box_ing);
                                    table4_2.setText(getDate(String.valueOf(start_time.get(i))));
                                    state4 = true;
                                    break;
                                case 4:
                                    tb5_1.setVisibility(View.VISIBLE);
                                    tb5_2.setVisibility(View.VISIBLE);
                                    table5_1.setText(num_people.get(i));
                                    tb5.setBackgroundResource(R.drawable.round_box_ing);
                                    table5_2.setText(getDate(String.valueOf(start_time.get(i))));
                                    state5 = true;
                                    break;
                                case 5:
                                    tb6_1.setVisibility(View.VISIBLE);
                                    tb6_2.setVisibility(View.VISIBLE);
                                    table6_1.setText(num_people.get(i));
                                    tb6.setBackgroundResource(R.drawable.round_box_ing);
                                    table6_2.setText(getDate(String.valueOf(start_time.get(i))));
                                    state6 = true;
                                    break;
                                case 6:
                                    tb7_1.setVisibility(View.VISIBLE);
                                    tb7_2.setVisibility(View.VISIBLE);
                                    table7_1.setText(num_people.get(i));
                                    tb7.setBackgroundResource(R.drawable.round_box_ing);
                                    table7_2.setText(getDate(String.valueOf(start_time.get(i))));
                                    state7 = true;
                                    break;
                                case 7:
                                    tb8_1.setVisibility(View.VISIBLE);
                                    tb8_2.setVisibility(View.VISIBLE);
                                    table8_1.setText(num_people.get(i));
                                    tb8.setBackgroundResource(R.drawable.round_box_ing);
                                    table8_2.setText(getDate(String.valueOf(start_time.get(i))));
                                    state8 = true;
                                    break;
                            }

                        }
                    }
                state1_2 = state1;
                state2_2 = state2;
                state3_2 = state3;
                state4_2 = state4;
                state5_2 = state5;
                state6_2 = state6;
                state7_2 = state7;
                state8_2 = state8;
            }
            catch (Exception e){
                Log.e("error_http1",e.toString());
            }
        }
    }
    public class HttpPostRequest2 extends AsyncTask<String,Void,String> {
        String sResult="error100";
        @Override
        protected String doInBackground(String... info) {
            URL url = null;
            HttpURLConnection conn = null;
            try {
                for (int i = 0; i < selected_table.size(); i++) {
                    String tb_manager = "http://52.69.163.43/queuing/all_table_management.php?";
                    Log.e("Http connection_http2", "완료");
                    String body = "resname=" + "sample&type=2&table_id="+String.valueOf(selected_tables.get(i))+"&end_time="+selected_table.get(i);
                    url = new URL(tb_manager+body);
                    conn = (HttpURLConnection) url.openConnection();
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuilder builder = new StringBuilder();
                    Log.e("InputStreamReader","완료");

                    String str;
                    while ((str = reader.readLine()) != null) {
                        builder.append(str);
                    }
                    sResult = builder.toString();
                    Log.e(sResult, "완료");
                    conn.disconnect();
                }
            }catch(Exception e){
                e.printStackTrace();
                Log.e(e.toString(), sResult);
            }


            return sResult;
        }
        @Override
        protected void onPostExecute(String result){
            Log.e("RESULT", result);



        }
    }
    public class HttpPostRequest3 extends AsyncTask<String,Void,String> {
        String sResult="error99";
        @Override
        protected String doInBackground(String... info) {
            URL url = null;
            try {
                for(int i = 0; i < mdialog.table_list.size(); i++) {
                    String tb_manager ="http://52.69.163.43/queuing/all_table_management.php?";
                    HttpURLConnection conn;
                    String post_value = "";
                    String body = "resname=sample&type=1&table_id=" + String.valueOf(mdialog.table_list.get(i)) + "&start_time="+ String.valueOf(mdialog.table_time_list.get(i))+"&num_people="+String.valueOf(mdialog.No);
                    url = new URL(tb_manager+body);
                    Log.e("url 검사",tb_manager+body);
                    conn = (HttpURLConnection) url.openConnection();
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuilder builder = new StringBuilder();

                    String str;
                    while ((str = reader.readLine()) != null) {
                        builder.append(str);
                    }
                    sResult = builder.toString();
                    Log.e(sResult, "완료");
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(e.toString(),sResult);
            }


            return sResult;
        }
        @Override
        protected void onPostExecute(String result){
            Log.e("RESULT", result);
            /*
            String jsonall = result;
            JSONArray jArray = null;

            try{
                jArray = new JSONArray(jsonall);
                JSONObject json_data = null;

                for (int i = 0; i < jArray.length(); i++) {
                    json_data = jArray.getJSONObject(i);
                    name = json_data.getString("food_name");
                    price = json_data.getInt("price");

                    items.add(new Res_menu_item(name,price));
                    Log.e("PROFILE",":"+i);

                }
            }catch(Exception e){
                e.printStackTrace();
            }
            mlist.setAdapter(adapter);

*/



        }
    }
    @Override protected void onStop(){
        super.onStop();

        finish();
    }
    void setEnabledOfTb(Boolean BN){
        tb1.setEnabled(BN);
        tb2.setEnabled(BN);
        tb3.setEnabled(BN);
        tb4.setEnabled(BN);
        tb5.setEnabled(BN);
        tb6.setEnabled(BN);
        tb7.setEnabled(BN);
        tb8.setEnabled(BN);
    }


}
