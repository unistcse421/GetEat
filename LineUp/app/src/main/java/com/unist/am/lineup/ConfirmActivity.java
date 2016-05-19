package com.unist.am.lineup;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.auth.APIErrorResult;
import com.kakao.usermgmt.MeResponseCallback;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.UserProfile;
import com.unist.am.lineup.DBManager_reserv;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by npc on 2015. 8. 6..
 */
public class ConfirmActivity extends Activity {

    private ImageView select1, select2, select3, select4, select5, select6,date,family,group;
    private RelativeLayout confirm_btn;
    private int party_num;
    String username;
    String resname;
    String dummy_name;

    String nickName;
    String profileImageURL ;
    String thumbnailURL ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_page);
        Intent intent = getIntent();
        //username=intent.getExtras().getString("username");
        requestMe();
        resname =intent.getExtras().getString("resname");
        dummy_name = intent.getExtras().getString("dummy_name");

        select1 = (ImageView) findViewById(R.id.selection_1);
        select2 = (ImageView) findViewById(R.id.selection_2);
        select3 = (ImageView) findViewById(R.id.selection_3);
        select4 = (ImageView) findViewById(R.id.selection_4);
        select5 = (ImageView) findViewById(R.id.selection_5);
        select6 = (ImageView) findViewById(R.id.selection_6);
        date    = (ImageView) findViewById(R.id.date);
        family  = (ImageView) findViewById(R.id.family);
        group   = (ImageView) findViewById(R.id.group);
        confirm_btn = (RelativeLayout) findViewById(R.id.confirm_btn);

        select1.setOnClickListener(mOnClick);
        select2.setOnClickListener(mOnClick);
        select3.setOnClickListener(mOnClick);
        select4.setOnClickListener(mOnClick);
        select5.setOnClickListener(mOnClick);
        select6.setOnClickListener(mOnClick);
        date.setOnClickListener(mOnClick);
        family.setOnClickListener(mOnClick);
        group.setOnClickListener(mOnClick);
        confirm_btn.setOnClickListener(mOnClick);



    }

    private View.OnClickListener mOnClick = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.selection_1: {
                    party_num = 1;
                    select1.setSelected(true);
                    select2.setSelected(false);
                    select3.setSelected(false);
                    select4.setSelected(false);
                    select5.setSelected(false);
                    select6.setSelected(false);

                    break;
                }
                case R.id.selection_2: {
                    select1.setSelected(false);
                    select2.setSelected(true);
                    select3.setSelected(false);
                    select4.setSelected(false);
                    select5.setSelected(false);
                    select6.setSelected(false);
                    party_num = 2;
                    break;
                }
                case R.id.selection_3: {
                    select1.setSelected(false);
                    select2.setSelected(false);
                    select3.setSelected(true);
                    select4.setSelected(false);
                    select5.setSelected(false);
                    select6.setSelected(false);
                    party_num = 3;
                    break;
                }
                case R.id.selection_4: {
                    select1.setSelected(false);
                    select2.setSelected(false);
                    select3.setSelected(false);
                    select4.setSelected(true);
                    select5.setSelected(false);
                    select6.setSelected(false);
                    party_num = 4;
                    break;
                }
                case R.id.selection_5: {
                    select1.setSelected(false);
                    select2.setSelected(false);
                    select3.setSelected(false);
                    select4.setSelected(false);
                    select5.setSelected(true);
                    select6.setSelected(false);
                    party_num = 5;
                    break;
                }
                case R.id.selection_6: {
                    select1.setSelected(false);
                    select2.setSelected(false);
                    select3.setSelected(false);
                    select4.setSelected(false);
                    select5.setSelected(false);
                    select6.setSelected(true);
                    party_num = 6;
                    break;
                }
                case R.id.date:{
                    date.setSelected(true);
                    family.setSelected(false);
                    group.setSelected(false);
                    break;
                }
                case R.id.family:{
                    date.setSelected(false);
                    family.setSelected(true);
                    group.setSelected(false);
                    break;
                }
                case R.id.group:{
                    date.setSelected(false);
                    family.setSelected(false);
                    group.setSelected(true);
                    break;
                }
                case R.id.confirm_btn: {
                    DBManager_reserv manager = new DBManager_reserv(getApplicationContext(), "reserv_info.db", null, 1);
                    DBManager_regid manager_regid = new DBManager_regid(getApplicationContext(),"regid_info.db",null,1);
                    if(manager.returnName().equals("nothing")) new HttpPostRequest().execute("in",nickName,String.valueOf(party_num),"App",dummy_name,manager_regid.returnRegid());
                    else Toast.makeText(getApplicationContext(),R.string.repetition,Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
    };



    private class HttpPostRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... info) {
            String sResult = "Error";

            try {
                URL url = new URL("http://52.69.163.43/queuing/line_manage.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                String body = "in_out=" + info[0] +"&"
                        +"name=" + info[1] + "&"
                        +"party=" + info[2] + "&"
                        +"method=" + info[3] + "&"
                        +"resname=" + info[4]+ "&"
                        +"regid=" + info[5];

                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                osw.write(body);
                osw.flush();

                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder builder = new StringBuilder();
                String str;

                while ((str = reader.readLine()) != null) {
                    builder.append(str);
                }
                sResult     = builder.toString();
                Log.e("CHECK", sResult);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sResult;
        }

        @Override
        protected void onPostExecute(String result){
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a");
            Toast.makeText(getApplicationContext(), R.string.complete, Toast.LENGTH_SHORT).show();
            DBManager_reserv manager = new DBManager_reserv(getApplicationContext(), "reserv_info.db", null, 1);
            manager.insert("insert into RESERV_INFO values (" + Integer.getInteger(result) + ",'" + resname + "','"+party_num+"','" +dummy_name+ "','"+sdf.format(dt).toString()+"')");
            Log.e("CONFIRM",":"+manager.returnPid()+" "+manager.returnName()+" "+manager.returnParty());
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    private void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {

            @Override
            public void onSuccess(final UserProfile userProfile) {
                Log.d("SUCCESS", "UserProfile : " + userProfile);
                userProfile.saveUserToCache();
                nickName=userProfile.getNickname();
                profileImageURL=userProfile.getProfileImagePath();
                thumbnailURL=userProfile.getThumbnailImagePath();
            }

            @Override
            public void onNotSignedUp() {

            }

            @Override
            public void onSessionClosedFailure(final APIErrorResult errorResult) {

                redirectLoginActivity();
            }

            @Override
            public void onFailure(final APIErrorResult errorResult) {
                if (errorResult.getErrorCodeInt() == -777) {
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }
        });
    }
    protected void redirectLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}