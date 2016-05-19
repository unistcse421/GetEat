package com.unist.am.lineup;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.kakao.auth.APIErrorResult;
import com.kakao.usermgmt.LogoutResponseCallback;
import com.kakao.usermgmt.UserManagement;

/**
 * Created by 정현짱월드 on 2015-10-30.
 */
public class SettingActivity extends Activity implements View.OnClickListener{

    TextView pushAlarm;
    TextView couponAlarm;
    TextView clauseBtn;
    TextView inquireBtn;
    TextView logoutBtn;
    Switch switch1;
    Switch switch2;
    public static SharedPreferences pref1;
    public static SharedPreferences pref2;
    SharedPreferences.Editor editor1;
    SharedPreferences.Editor editor2;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.setting);

        pushAlarm = (TextView) findViewById(R.id.pushAlarm);
        couponAlarm =(TextView)findViewById(R.id.couponAlarm);
        clauseBtn = (TextView) findViewById(R.id.clause);
        inquireBtn= (TextView) findViewById(R.id.inquire);
        logoutBtn = (TextView) findViewById(R.id.logout);
        switch1 = (Switch) findViewById(R.id.switch_notification);
        switch2 = (Switch) findViewById(R.id.switch_notification2);

        pref1 = getApplicationContext().getSharedPreferences("Push_Notification_ONOFF", MODE_PRIVATE);
        pref2 = getApplicationContext().getSharedPreferences("Coupon_Notification_ONOFF", MODE_PRIVATE);
        editor1 = pref1.edit();
        editor2 = pref2.edit();

        switch1.setChecked(pref1.getBoolean("Notification",true));
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor1.putBoolean("Notification", true);
                    editor1.commit();
                    Log.d("NOTIFY", "TF : " + pref1.getBoolean("Notification", true));
                } else {
                    editor1.putBoolean("Notification", false);
                    editor1.commit();
                    Log.d("NOTIFY", "TF : " + pref1.getBoolean("Notification", true));

                }
            }
        });

        switch2.setChecked(pref1.getBoolean("Notification",true));
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor2.putBoolean("Notification", true);
                    editor2.commit();
                    Log.d("NOTIFY", "TF2 : " + pref2.getBoolean("Notification", true));
                } else {
                    editor2.putBoolean("Notification", false);
                    editor2.commit();
                    Log.d("NOTIFY", "TF2 : " + pref2.getBoolean("Notification", true));

                }
            }
        });


        clauseBtn.setOnClickListener(this);
        inquireBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent mintent = null;
        switch(v.getId()){
            case R.id.clause:
                mintent = new Intent(this, ClauseActivity.class);
                startActivity(mintent);
                break;
            case R.id.inquire:
                mintent = new Intent(this, InquireActivity.class);
                startActivity(mintent);
                break;
            case R.id.logout:
                UserManagement.requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onSuccess(final long userId) {
                        redirectLoginActivity();
                    }

                    @Override
                    public void onFailure(final APIErrorResult apiErrorResult) {
                        //redirectLoginActivity();
                        Log.e("LOGOUTFAIL", "ERROR : " + apiErrorResult);
                    }
                });
                //Dialog
                break;


        }

    }
    protected void redirectLoginActivity() {

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("com.unist.npc.queuing.", Context.MODE_PRIVATE);
        prefs.edit().remove("IsLogin").apply();
        prefs.edit().putBoolean("IsLogin",false).apply();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
