package com.shivam.solarcalculator.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.shivam.solarcalculator.R;
import com.shivam.solarcalculator.ui.dashboard.MapsActivity;

public class BroadcastManager extends BroadcastReceiver {

    private static final String TAG = "BroadcastManager";
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Intent intent1 = new Intent(context, MapsActivity.class);
            createNotification(context, intent1, "new mensage");
        } catch (Exception e) {
            Log.i(TAG, "error == " + e.getMessage());
        }
    }


    private void createNotification(Context context, Intent intent1, String new_mensage) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(new_mensage)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentText(new_mensage)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(new_mensage));
               /// .setContentIntent(new_mensage);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = notificationBuilder.build();
        notification.defaults = Notification.DEFAULT_ALL;
        notificationManager.notify(111, notification);
    }

}