package com.unist.am.lineup;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.kakao.auth.APIErrorResult;
import com.kakao.kakaotalk.KakaoTalkHttpResponseHandler;


/**
 * Created by mintaewon on 2015. 10. 29..
 */
public class KakaoTalkMainActivity extends Activity {

    /*private void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private abstract class KakaoTalkResponseCallback<T> extends TalkResponseCallback<T> {
        @Override
        public void onNotKakaoTalkUser() {
            Logger.w("not a KakaoTalk user");
        }

        @Override
        public void onFailure(ErrorResult errorResult) {
            Logger.e("failure : " + errorResult);
        }

        @Override
        public void onSessionClosed(ErrorResult errorResult) {
            redirectLoginActivity();
        }
    }*/
    private void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
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

}
