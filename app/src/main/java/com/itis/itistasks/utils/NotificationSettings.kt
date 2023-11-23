package com.itis.itistasks.utils

import android.app.Notification.VISIBILITY_PUBLIC
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.os.Build
import androidx.annotation.RequiresApi

object NotificationSettings {
    @RequiresApi(Build.VERSION_CODES.N)
    var importance = IMPORTANCE_HIGH
    var visibility = VISIBILITY_PUBLIC
    var hideText = false
    var showButtons = false
}