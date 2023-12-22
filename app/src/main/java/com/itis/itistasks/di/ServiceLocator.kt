package com.itis.itistasks.di

import android.content.Context
import androidx.room.Room
import com.itis.itistasks.data.db.FilmsAppDatabase

object ServiceLocator {

    private var dbInstance: FilmsAppDatabase? = null

    fun initData(ctx: Context) {
        dbInstance = Room.databaseBuilder(ctx, FilmsAppDatabase::class.java, "app.db")
            .build()
    }

    fun getDbInstance(): FilmsAppDatabase {
        return dbInstance ?: throw IllegalStateException("Db not initialized")
    }
}