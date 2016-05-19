package com.unist.am.lineup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.kakao.auth.APIErrorResult;
import com.kakao.auth.Session;
import com.kakao.usermgmt.MeResponseCallback;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.UserProfile;
import com.kakao.util.helper.log.Logger;

import java.io.IOException;

/**
 * Created by mintaewon on 2015. 10. 30..
 */
public class SignUpActivity extends Activity {
    // For GCM Client variables
    public static final String PROPERTY_REG_ID = "registration_id";
    public static final String OWN_PREFS ="OWNER_PREFS";
    // SharedPreferences에 저장할 때 key 값으로 사용됨.
    // SharedPreferences에 저장할 때 key 값으로 사용됨.
    private static final String PROPERTY_APP_VERSION = "1.0";
    private static final String TAG = "NPC";
    private BackPressCloseHandler backPressCloseHandler;
    private String SENDER_ID = "855721226478";
    private String User_ID = "null";
    GoogleCloudMessaging gcm;
    private String regid;
    private Context context;

    String nickName;
    String profileImageURL ;
    String thumbnailURL ;
    String countryISO ;

    /**
     * Main으로 넘길지 가입 페이지를 그릴지 판단하기 위해 me를 호출한다.
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        SharedPreferences.Editor editor = getSharedPreferences(OWN_PREFS, MODE_PRIVATE).edit();
        SharedPreferences own_prefs = getSharedPreferences(OWN_PREFS, MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        Log.d("FIRST", "IT's THE FIRST");
        Session.initialize(this);
        // For GCM Client
        context = getApplicationContext();
        gcm = GoogleCloudMessaging.getInstance(this);
        regid = getRegistrationId(context);
        if (regid.isEmpty()) {
            Log.i(TAG,"PASS");
            registerInBackground();
        }
        if (own_prefs.contains("name") ==false) {
            requestMe();
        }
        else {
            Log.i("OWNER_INFO", " name : " + own_prefs.getString("name",null));
            final Intent intent = new Intent(this, Owner_mainActivity.class);
            startActivity(intent);
            finish();
        }
        //ONLY FOR TEST AND DEBUGGING, TO BE DELETED
        /*final Intent intent = new Intent(this, Owner_mainActivity.class);
        startActivity(intent);
        finish();*/

        requestMe();
       /* final Intent intent = new Intent(this, Owner_mainActivity.class);
        startActivity(intent);
        finish();
*/
//        requestMe();
    }

    /**
     * 자동가입앱인 경우는 가입안된 유저가 나오는 것은 에러 상황.
     */
    protected void showSignup() {
        Logger.d("KAKAO", "not registered user");
        redirectLoginActivity();
    }

    protected void redirectMainActivity() {
        final Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    protected void redirectLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    private void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {

            @Override
            public void onSuccess(final UserProfile userProfile) {
                Log.d("SUCCESS", "UserProfile : " + userProfile);
                userProfile.saveUserToCache();
                nickName=userProfile.getNickname();
                profileImageURL=userProfile.getProfileImagePath();
                thumbnailURL=userProfile.getThumbnailImagePath();
                Log.e("regId",":"+regid);
                redirectMainActivity();
            }

            @Override
            public void onNotSignedUp() {
                showSignup();
            }

            @Override
            public void onSessionClosedFailure(final APIErrorResult errorResult) {
                Log.d("SUCCESS", "onSessionClosedFailure");
                Log.e("regId", ":" + regid);

                redirectLoginActivity();
            }

            @Override
            public void onFailure(final APIErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Log.d("FAIL", message);
                Log.e("regId", ":" + regid);
                if (errorResult.getErrorCodeInt() == -777) {
                    Toast.makeText(getApplicationContext(), "SERVICE_UNAVAILABLE", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }
        });
    }


    // Methods for realizing GCM
    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        Log.i("RegID_Stored", registrationId);
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }

        // 앱이 업데이트 되었는지 확인하고, 업데이트 되었다면 기존 등록 아이디를 제거한다.
        // 새로운 버전에서도 기존 등록 아이디가 정상적으로 동작하는지를 보장할 수 없기 때문이다.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
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
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    Log.i(TAG,"sender_id =  " + SENDER_ID);
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // 서버에 발급받은 등록 아이디를 전송한다.
                    // 등록 아이디는 서버에서 앱에 푸쉬 메시지를 전송할 때 사용된다.
                    sendRegistrationIdToBackend();

                    // 등록 아이디를 저장해 등록 아이디를 매번 받지 않도록 한다.
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            //@Override
            //protected void onPostExecute(String msg) {
            //    mDisplay.append(msg + "\n");
            //}

        }.execute(null, null, null);
    }

    private void storeRegistrationId(Context context, String regid) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regid);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    private void sendRegistrationIdToBackend() {

        DBManager_regid manager = new DBManager_regid(getApplicationContext(), "regid_info.db", null, 1);
        manager.insert("insert into REGID_INFO values ('"+regid+"')");
    }

}