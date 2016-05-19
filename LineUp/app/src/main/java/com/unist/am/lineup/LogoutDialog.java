package com.unist.am.lineup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 정현짱월드 on 2015-10-30.
 */
public class LogoutDialog extends Dialog implements View.OnTouchListener{

    public LogoutDialog(Context context) {
        super(context);
    }

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_logout);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
