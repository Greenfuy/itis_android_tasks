package com.itis.itistasks

import android.app.Application
import com.itis.itistasks.di.ServiceLocator

class ItisTasksApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.initDataDependencies(ctx = this)
        ServiceLocator.initDomainDependencies()
    }
}