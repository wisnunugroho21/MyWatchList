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
 * Created by nugroho on 07/09/17.
 */

public class PeriodUpdateNotificationUtils
{

    public static final int LIGHT_NOTIFICATION = 1;
    public static final int VIBRATE_NOTIFICATION = 2;
    public static final int SOUND_NOTIFICATION = 3;
    public static final int ALL_SET_NOTIFICATION = 4;

    private static PendingIntent createPendingIntent(Context context)
    {
        Intent intent = new Intent(context, MovieListActivity.class);
        return PendingIntent.getActivity(context, MovieDBKeyEntry.NotificationKey.NEW_NOW_SHOWING_MOVIE_NOTIFICATION, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap createLargeIcon(Context context)
    {
        Resources resources = context.getResources();
        return BitmapFactory.decodeResource(resources, R.drawable.ic_movie_filter_black_24px);
    }

    public static Notification createFinishPeriodUpdateNotification(Context context, int numberOfNewNewNowShowingMovie, int typeOfNotification)
    {
        String contentText = context.getString(R.string.new_now_showing_notification_start_content_text)
                + " " + String.valueOf(numberOfNewNewNowShowingMovie)
                + " " + context.getString(R.string.new_now_showing_notification_final_content_text);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_movie_white_24px)
                .setLargeIcon(createLargeIcon(context))
                .setContentTitle(context.getString(R.string.new_now_showing_notification_content_title))
                .setContentText(contentText)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(contentText))
                .setAutoCancel(true)
                .addAction(createSettingCallsNotificationAction(context));

        switch (typeOfNotification)
        {
            case LIGHT_NOTIFICATION : builder.setDefaults(Notification.DEFAULT_LIGHTS); break;
            case VIBRATE_NOTIFICATION : builder.setDefaults(Notification.DEFAULT_VIBRATE); break;
            case SOUND_NOTIFICATION : builder.setDefaults(Notification.DEFAULT_SOUND); break;
            case ALL_SET_NOTIFICATION : builder.setDefaults(Notification.DEFAULT_ALL); break;
        }

        builder.setPriority(Notification.PRIORITY_HIGH);
        return  builder.build();
    }

    public static Notification createStartPeriodUpdateNotification(Context context)
    {
        String contentText = context.getString(R.string.new_now_showing_notification_updating_movie_list);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_file_download_white_24px)
                .setLargeIcon(createLargeIcon(context))
                .setContentTitle(context.getString(R.string.getting_Data_notification_content_title))
                .setContentText(contentText)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(contentText))
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setAutoCancel(true)
                .setTicker(contentText);

        builder.setPriority(Notification.PRIORITY_LOW);
        return  builder.build();
    }

    public static void clearThisNotification(Context context)
    {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(MovieDBKeyEntry.NotificationKey.NEW_NOW_SHOWING_MOVIE_NOTIFICATION);
    }

    private static NotificationCompat.Action createSettingCallsNotificationAction(Context context)
    {
        PendingIntent pendingIntent = createPendingIntent(context);

        return new NotificationCompat.Action
                (R.drawable.ic_movie_black_24px, context.getString(R.string.new_now_showing_notification_check_movies_action), pendingIntent);
    }

    public static void showNotification(Context context, int numberOfNewNewNowShowingMovie, int typeOfNotification)
    {
        Notification notification = createFinishPeriodUpdateNotification(context, numberOfNewNewNowShowingMovie, typeOfNotification);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MovieDBKeyEntry.NotificationKey.NEW_NOW_SHOWING_MOVIE_NOTIFICATION, notification);
    }
}
