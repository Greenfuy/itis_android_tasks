package com.itis.itistasks

import android.app.Application
import com.itis.itistasks.di.ServiceLocator

class FilmsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.initData(this)
    }
}