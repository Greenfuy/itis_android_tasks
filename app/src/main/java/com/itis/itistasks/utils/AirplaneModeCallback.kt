package com.itis.itistasks.utils

import android.content.Context
import android.provider.Settings

object AirplaneModeCallback {
    private var pos = 0
    private val callbacks = mutableMapOf<Int, (Boolean) -> Unit>()

    fun saveCallback(context: Context, callback: (Boolean) -> Unit): Int {
        callbacks[pos] = callback
        callback.invoke(isAirplaneModeOn(context))
        return pos++
    }

    fun removeCallback(pos: Int) {
        callbacks.remove(pos)
    }

    fun notify(context: Context) {
        callbacks.values.forEach {
            it.invoke(isAirplaneModeOn(context))
        }
    }

    private fun isAirplaneModeOn(context: Context) : Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON,
            0,
        ) != 0
    }
}