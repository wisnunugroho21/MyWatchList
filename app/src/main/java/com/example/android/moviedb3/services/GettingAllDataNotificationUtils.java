package com.example.android.moviedb3.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.activity.MovieListActivity;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;

/**
 * Created by nugroho on 08/09/17.
 */

public class GettingAllDataNotificationUtils
{
    private static PendingIntent createPendingIntent(Context context)
    {
        Intent intent = new Intent(context, MovieListActivity.class);
        return PendingIntent.getActivity(context, MovieDBKeyEntry.NotificationKey.GETTING_DATA_NOTIFICATION, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap createLargeIcon(Context context)
    {
        Resources resources = context.getResources();
        return BitmapFactory.decodeResource(resources, R.drawable.ic_file_download_white_24px);
    }

    public static Notification createNotificationWithProgress(Context context, int maxValues, int values)
    {
        double mValues = (double) values;
        double mMaxValues = (double) maxValues;

        double percent = mValues / mMaxValues * 100;
        String contentText = context.getString(R.string.getting_data_notification_still_progress) + " " + String.valueOf(percent) + "%";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_file_download_white_24px)
                .setLargeIcon(createLargeIcon(context))
                .setContentTitle(context.getString(R.string.getting_Data_notification_content_title))
                .setContentText(contentText)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(contentText))
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setAutoCancel(true)
                .setProgress(maxValues, values, false)
                .setTicker(contentText);

        builder.setPriority(Notification.PRIORITY_LOW);
        return  builder.build();
    }

    public static Notification createNotificationCompleted(Context context)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_file_download_white_24px)
                .setLargeIcon(createLargeIcon(context))
                .setContentTitle(context.getString(R.string.getting_Data_notification_content_title))
                .setContentText(context.getString(R.string.getting_data_notification_completed))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.getting_data_notification_completed)))
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setAutoCancel(true)
                .addAction(createSettingCallsNotificationAction(context))
                .setProgress(0, 0, false);

        builder.setPriority(Notification.PRIORITY_HIGH);
        return  builder.build();
    }

    public static void clearThisNotification(Context context)
    {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(MovieDBKeyEntry.NotificationKey.GETTING_DATA_NOTIFICATION);
    }

    private static NotificationCompat.Action createSettingCallsNotificationAction(Context context)
    {
        PendingIntent pendingIntent = createPendingIntent(context);

        return new NotificationCompat.Action
                (R.drawable.ic_movie_black_24px, context.getString(R.string.new_now_showing_notification_check_movies_action), pendingIntent);
    }

    public static void showNotificationOnProgress(Context context, int maxValues, int values)
    {
        Notification notification = createNotificationWithProgress(context, maxValues, values);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MovieDBKeyEntry.NotificationKey.GETTING_DATA_NOTIFICATION, notification);
    }

    public static void showNotificationCompleted(Context context)
    {
        Notification notification = createNotificationCompleted(context);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MovieDBKeyEntry.NotificationKey.GETTING_DATA_NOTIFICATION, notification);
    }
}