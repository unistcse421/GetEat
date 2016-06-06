package com.unist.db.geteat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.auth.APIErrorResult;
import com.kakao.usermgmt.MeResponseCallback;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.UserProfile;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Mark on 2016-06-07.
 */
public class Addsetting extends Activity {

    String nickName;
    String profileImageURL;
    String thumbnailURL;
    String regid;
    private EditText name, bank, account, phone;
    TextView cus_name;
    ImageView cus_profile;
    TextView add_setting;
    DBManager_myinfo myinfo;
    ArrayList<String> myData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_setting);
        Intent intent = getIntent();
        regid = intent.getStringExtra("regid");
        Log.d("INTENT_REGID", regid);
        requestMe();
        myinfo = new DBManager_myinfo(this,"MY_INFO",null,1);
        myData = new ArrayList<String>();
        name = (EditText) findViewById(R.id.edit_name);
        bank = (EditText) findViewById(R.id.edit_bank);
        account = (EditText) findViewById(R.id.edit_account);
        phone = (EditText) findViewById(R.id.edit_phone);
        add_setting = (TextView) findViewById(R.id.confirm_add_setting);

        add_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myData.add(regid);
                myData.add(name.getText().toString());
                myData.add(bank.getText().toString());
                myData.add(account.getText().toString());
                myData.add(phone.getText().toString());
                myinfo.insertMyInfo(myData);
                Toast.makeText(getApplicationContext(),"저장되었습니다",Toast.LENGTH_SHORT).show();
                redirectMainActivity();
            }
        });
    }

    private void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {

            @Override
            public void onSuccess(final UserProfile userProfile) {
                Log.d("SUCCESS", "UserProfile : " + userProfile);
                userProfile.saveUserToCache();
                nickName = userProfile.getNickname();
                profileImageURL = userProfile.getProfileImagePath();
                thumbnailURL = userProfile.getThumbnailImagePath();
                cus_name = (TextView) findViewById(R.id.profile_name);
                cus_profile = (ImageView) findViewById(R.id.profile);
                cus_name.setText(nickName);
                //Picasso.with(MyPageActivity.this).load(thumbnailURL).centerCrop().into(cus_profile);
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
    protected void redirectMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
