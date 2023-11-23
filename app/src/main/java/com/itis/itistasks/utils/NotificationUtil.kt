package com.itis.itistasks.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.itis.itistasks.MainActivity
import com.itis.itistasks.MainActivity.Companion.ACTION
import com.itis.itistasks.MainActivity.Companion.ACTION_OPEN_APP
import com.itis.itistasks.MainActivity.Companion.ACTION_OPEN_SETTINGS
import com.itis.itistasks.R

object NotificationUtil {

    private const val TEXT_NOTIFICATION_ID = 0
    private const val COROUTINES_NOTIFICATION_ID = 1

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannels(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager

        notificationManager.createNotificationChannel(
            NotificationChannel(
                context.getString(R.string.notification_channel_urgent),
                context.getString(R.string.urgent),
                IMPORTANCE_HIGH
            )
        )

        notificationManager.createNotificationChannel(
            NotificationChannel(
                context.getString(R.string.notification_channel_high),
                context.getString(R.string.high),
                IMPORTANCE_DEFAULT
            )
        )

        notificationManager.createNotificationChannel(
            NotificationChannel(
                context.getString(R.string.notification_channel_medium),
                context.getString(R.string.medium),
                IMPORTANCE_LOW
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun sendNotification(context: Context) {
        val builder = NotificationCompat.Builder(
            context,
            getNotificationChannelId(NotificationSettings.importance, context),
        )
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle(TextNotificationSettings.title)
            .setContentText(TextNotificationSettings.message)
            .setVisibility(NotificationSettings.visibility)
            .setAutoCancel(true)
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        if (NotificationSettings.hideText) {
            builder.setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(TextNotificationSettings.message)
            )
        }

        if (NotificationSettings.showButtons) {
            intent.putExtra(ACTION, ACTION_OPEN_APP)
            val pendingOpenAppIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
            builder.addAction(
                NotificationCompat.Action(
                    null,
                    context.getString(R.string.open_app),
                    pendingOpenAppIntent
                )
            )

            intent.putExtra(ACTION, ACTION_OPEN_SETTINGS)
            val pendingOpenSettingsIntent = PendingIntent.getActivity(
                context,
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
            builder.addAction(
                NotificationCompat.Action(
                    null,
                    context.getString(R.string.open_settings),
                    pendingOpenSettingsIntent
                )
            )
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            2,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        builder.setContentIntent(pendingIntent)

        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(TEXT_NOTIFICATION_ID, builder.build())
    }



    fun sendCoroutinesFinishedNotification(context: Context) {
        val builder = NotificationCompat.Builder(
            context,
            context.getString(R.string.notification_channel_urgent),
        )
            .setSmallIcon(R.drawable.baseline_notifications_24)
            .setContentTitle(context.getString(R.string.coroutines_finished))
            .setAutoCancel(true)

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            context,
            3,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        builder.setContentIntent(pendingIntent)
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .notify(COROUTINES_NOTIFICATION_ID, builder.build())
    }

    private fun getNotificationChannelId(importance: Int, context: Context): String =
        when(importance) {
            IMPORTANCE_DEFAULT -> context.getString(R.string.notification_channel_high)
            IMPORTANCE_LOW -> context.getString(R.string.notification_channel_medium)
            else -> context.getString(R.string.notification_channel_urgent)
    }
}