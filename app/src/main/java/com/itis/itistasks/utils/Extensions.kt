package com.itis.itistasks.utils

import android.util.DisplayMetrics

fun calculateRealItemCount(itemCount: Int) : Int {
    return if (itemCount % 8 == 0) {
        1 + itemCount + itemCount / 8
    } else {
        1 + itemCount + itemCount / 8 + 1
    }
}

fun Int.getValueInPx(dm: DisplayMetrics): Int {
    return (this * dm.density).toInt()
}

fun getSteps(): MutableList<Int> = mutableListOf(1, 10, 19, 28, 37)
