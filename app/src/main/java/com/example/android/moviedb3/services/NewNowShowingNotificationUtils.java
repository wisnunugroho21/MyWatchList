package com.example.android.moviedb3.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.android.moviedb3.R;
import com.example.android.moviedb3.activity.MovieListActivity;
import com.example.android.moviedb3.movieDB.MovieDBKeyEntry;

/**
 * Created by nugroho on 07/09/17.
 */

public class NewNowShowingNotificationUtils
{
    private static PendingIntent createPendingIntent(Context context)
    {
        Intent intent = new Intent(context, MovieListActivity.class);
        return PendingIntent.getActivity(context, MovieDBKeyEntry.NEW_NOW_SHOWING_MOVIE_NOTIFICATION, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap createLargeIcon(Context context)
    {
        Resources resources = context.getResources();
        return BitmapFactory.decodeResource(resources, R.drawable.ic_movie_filter_black_24px);
    }

    private static Notification createNotification(Context context, int numberOfNewNewNowShowingMovie)
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
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setAutoCancel(true)
                .addAction(createSettingCallsNotificationAction(context));

        builder.setPriority(Notification.PRIORITY_HIGH);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            builder.setVisibility(Notification.VISIBILITY_PUBLIC);
        }

        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) //Jika target SDK nya dibawah 18 atau jellybean
        {
            builder.setPriority(Notification.PRIORITY_HIGH);
        }*/

        return builder.build();
    }

    public static void clearThisNotification(Context context)
    {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(MovieDBKeyEntry.NEW_NOW_SHOWING_MOVIE_NOTIFICATION);
    }

    private static NotificationCompat.Action createSettingCallsNotificationAction(Context context)
    {
        PendingIntent pendingIntent = createPendingIntent(context);

        NotificationCompat.Action action = new NotificationCompat.Action
                (R.drawable.ic_movie_black_24px, context.getString(R.string.new_now_showing_notification_check_movies_action), pendingIntent);
        return action;
    }

    public static void showNotification(Context context, int numberOfNewNewNowShowingMovie)
    {
        Notification notification = createNotification(context, numberOfNewNewNowShowingMovie);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MovieDBKeyEntry.NEW_NOW_SHOWING_MOVIE_NOTIFICATION, notification);
    }
}
