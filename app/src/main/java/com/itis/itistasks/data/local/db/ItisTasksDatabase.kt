package com.itis.itistasks.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [],
    version = 1,
)

abstract class ItisTasksDatabase : RoomDatabase() {
}