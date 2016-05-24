package com.unist.db.geteat;

/**
 * Created by mark_mac on 2015. 7. 10..
 */
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService
{
    public static final int NOTIFICATION_ID = 1;

    private int front_num=1000;
    public GcmIntentService()
    {
        super("GcmIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent)
    {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);
        String u_name = extras.getString("user_name");
        String num_party = extras.getString("num_party");
        String user_regid = extras.getString("user_regid");
        String update = extras.getString("update");
        String exp_time = extras.getString("expected_time");
        Log.e("CHECK", "user name is " + extras.get("user_name"));
        if (!extras.isEmpty())
        { // has effect of unparcelling Bundle
      /*
       * Filter messages based on message type. Since it is likely that GCM will
       * be extended in the future with new message types, just ignore any
       * message types you're not interested in, or that you don't recognize.
       */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType))
            {

                sendNotification("Send error: " + extras.toString());
            }
            else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType))
            {
                sendNotification("Deleted messages on server: " + extras.toString());
                // If it's a regular GCM message, do some work.
            }
            else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType))
            {
                // Post notification of received message.
//             sendNotification("Received: " + extras.toString());

                if(num_party== null) {//Notification for CUSTOMER
                    intent.putExtra("update", update);

                    if(update == null) updateMyActivity_customer_delete(getApplicationContext());
                    else updateMyActivity_customer_update(getApplicationContext(),update);

                    //sendNotification("You are now 5th / expected time = 25"); //+ exp_time);
                    Log.i("IntentService onHandle", "Received: " + extras.toString());
                }else{
                    updateMyActivity_owner(getApplicationContext(), u_name, num_party, user_regid);

                }
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }


    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.

    private void updateMyActivity_owner(Context context, String name, String num,String user_regid){
        Intent intent = new Intent("key");
        intent.putExtra("name",name);
        intent.putExtra("num",num);
        intent.putExtra("regid",user_regid);
      //  intent.putExtra("msg",msg);
        context.sendBroadcast(intent);
    }
    private void updateMyActivity_customer_delete(Context context){
        Intent intent = new Intent("cus");
        context.sendBroadcast(intent);
    }
    private void updateMyActivity_customer_update(Context context,String update){
        Intent intent = new Intent("up");
        intent.putExtra("update", update);
        Log.e("CHECK", "front_num " + front_num );

        DBManager_update manager = new DBManager_update(getApplicationContext(), "update_info2.db", null, 1);
        front_num = Integer.parseInt(update)-1;
        if(Integer.parseInt(update)<1000) {
            manager.insert("insert into UPDATE_INFO values ('" + front_num + "')");
        }
        else{
            front_num = Integer.parseInt(manager.returnPid())-1;
            if(front_num<=4) sendNotification("Notification");
            manager.update("update UPDATE_INFO set priority='" + front_num + "'");
        }



        context.sendBroadcast(intent);
    }

    private void sendNotification(String msg)
    {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("LineUp").setStyle(
                new NotificationCompat.BigTextStyle().bigText(msg)).setContentText(msg).setAutoCancel(true).setVibrate(new long[]{0, 500});

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
