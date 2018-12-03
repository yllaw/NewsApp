package com.marco.news.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Build;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;


import com.marco.news.R;
import com.marco.news.ui.MainActivity;

public class NotificationUtils {
    private static final String TAG = "NotificationUtils:";

    private static final int NEWS_REMINDER_ID = 1;
    private static final String NEWS_REMINDER_CHANNEL_ID = "news_reminder";


    public static void notifyUser(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    NEWS_REMINDER_CHANNEL_ID,
                    context.getString(R.string.news_reminder_notification_channel),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Log.d(TAG, "notify user");
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NEWS_REMINDER_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_subtitles_black_24dp)
                .setLargeIcon(largeIcon(context))
                .setContentTitle("News Reminder!")
                .setContentText("Check out some news!")
                .setStyle(new NotificationCompat.BigTextStyle())
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(NEWS_REMINDER_ID, notificationBuilder.build());
    }

    private static PendingIntent contentIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);

        return PendingIntent.getActivity(
                context,
                NEWS_REMINDER_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private static Bitmap largeIcon(Context context){
        Resources res  = context.getResources();
        return BitmapFactory.decodeResource(res, R.drawable.ic_subtitles_black_24dp);
    }
}