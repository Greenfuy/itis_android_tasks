package com.itis.itistasks.utils

import android.util.DisplayMetrics
import com.itis.itistasks.base.Keys

fun insertIconIdInUrl(icon: String): String = Keys.OPEN_WEATHER_ICON_URL + icon + "@4x.png"

fun temperatureToPrettyFormat(temperature: String): String = "$temperature °C"