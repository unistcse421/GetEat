package com.unist.am.lineup;

import android.app.Activity;
import android.app.ActivityManager;
import android.widget.Toast;

/**
 * Created by mintaewon on 2015. 10. 29..
 */
public class BackPressCloseHandler {

    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            programShutdown();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity,
                "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다", Toast.LENGTH_SHORT);
        toast.show();
    }


    private void programShutdown() {
        activity .moveTaskToBack(true);
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(activity.ACTIVITY_SERVICE);
        activityManager.killBackgroundProcesses("com.unist.npc.queuing");
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

    }

}