package com.unist.db.geteat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kakao.auth.APIErrorResult;
import com.kakao.auth.Session;
import com.kakao.auth.SessionCallback;
import com.kakao.kakaotalk.KakaoTalkHttpResponseHandler;
import com.kakao.kakaotalk.KakaoTalkProfile;
import com.kakao.kakaotalk.KakaoTalkService;
import com.kakao.usermgmt.LoginButton;
import com.kakao.util.exception.KakaoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class LoginActivity extends AppCompatActivity {


    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "1.0";
    private LoginButton kakaoLogin;
    private final SessionCallback mySessionCallback = new MySessionStatusCallback();
    private Session session;
    private BackPressCloseHandler backPressCloseHandler;
    String nickName;
    String profileImageURL ;
    String thumbnailURL ;
    String countryISO ;
    ImageView owner_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("OnCreate:","LoginActivity");
        setContentView(R.layout.activity_login);
        backPressCloseHandler = new BackPressCloseHandler(this);
        kakaoLogin = (LoginButton) findViewById(R.id.kakao_login);
        Session.initialize(this);
        session =Session.getCurrentSession();
        session.addCallback(mySessionCallback);
        if (session.isClosed()){
            kakaoLogin.setVisibility(View.VISIBLE);
        } else {
            kakaoLogin.setVisibility(View.GONE);
            if (session.implicitOpen()) {
                kakaoLogin.setVisibility(View.GONE);
            } else {
                finish();
            }
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        session.removeCallback(mySessionCallback);
    }
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
        Session.initialize(this);
    }





    private class MySessionStatusCallback implements SessionCallback {
        @Override
        public void onSessionOpened() {
            // 프로그레스바 종료
            Log.d("OPEND", "OPEN");
            // 세션 오픈후 보일 페이지로 이동
            //readProfile();

            Log.d("OPEND", "onHttpSuccess " + nickName);
            //final DBManager_regid manager_regid = new DBManager_regid(getApplicationContext(),"regid_info.db",null,1);
            SharedPreferences prefs = getApplicationContext().getSharedPreferences("com.unist.npc.queuing.", Context.MODE_PRIVATE);
            if(!prefs.contains("IsLogin"))
                prefs.edit().putBoolean("IsLogin",true).apply();
            else{
                prefs.edit().remove("IsLogin").apply();
                prefs.edit().putBoolean("IsLogin",true).apply();
            }
            //if(nickName != null) new HttpPostRequest2().execute(manager_regid.returnRegid(), nickName);
            KakaoTalkService.requestProfile(new MyTalkHttpResponseHandler<KakaoTalkProfile>() {
                @Override
                public void onHttpSuccess(final KakaoTalkProfile talkProfile) {
                    nickName = talkProfile.getNickName();
                    profileImageURL = talkProfile.getProfileImageURL();
                    thumbnailURL = talkProfile.getThumbnailURL();
                    countryISO = talkProfile.getCountryISO();
                    DBManager_userinfo manager = new DBManager_userinfo(LoginActivity.this, "user_info.db", null, 1);
                    manager.insert("insert into USER_INFO values ('" + nickName + "')");
                    new enroll_user().execute(getRegistrationId(getApplicationContext()), nickName);

                }
            });
            //finish();

        }

        @Override
        public void onSessionClosed(final KakaoException exception) {
            // 프로그레스바 종료
            // 세션 오픈을 못했으니 다시 로그인 버튼 노출.
            Log.d("LOGINFAIL","FAIL " +exception);
            kakaoLogin.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSessionOpening() {
            //프로그레스바 시작
        }
    }
    private abstract class MyTalkHttpResponseHandler<T> extends KakaoTalkHttpResponseHandler<T> {
        @Override
        public void onHttpSessionClosedFailure(final APIErrorResult errorResult) {
            redirectLoginActivity();
        }

        @Override
        public void onNotKakaoTalkUser(){
            Toast.makeText(getApplicationContext(), "not a KakaoTalk user", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(final APIErrorResult errorResult) {
            Toast.makeText(getApplicationContext(), "failed : " + errorResult, Toast.LENGTH_SHORT).show();
        }
    }
    private void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void redirectMainActivity(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }


    private class enroll_user extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... info) {
            String sResult = "Error";

            try {
                URL url = new URL("http://52.69.163.43/queuing/user_enroll.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");

                String body = "regid=" + info[0] +"&"
                        +"name=" + info[1];

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
                Log.e("enroll", sResult);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sResult;
        }

        @Override
        protected void onPostExecute(String result){
            //finish();
            final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("nickName",nickName);
            intent.putExtra("profileImgURL",profileImageURL);
            intent.putExtra("thumbnailURL",thumbnailURL);
            intent.putExtra("countryISO",countryISO);
            startActivity(intent);
            finish();

        }

    }
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        Log.i("RegID_Stored", registrationId);
        if (registrationId.isEmpty()) {
            return "";
        }

        // 앱이 업데이트 되었는지 확인하고, 업데이트 되었다면 기존 등록 아이디를 제거한다.
        // 새로운 버전에서도 기존 등록 아이디가 정상적으로 동작하는지를 보장할 수 없기 때문이다.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            return "";
        }
        return registrationId;
    }
    private SharedPreferences getGCMPreferences(Context context) {
        return getSharedPreferences(LoginActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }
}

