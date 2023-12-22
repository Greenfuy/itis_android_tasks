package com.itis.itistasks.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.itis.itistasks.data.db.dao.FilmDao
import com.itis.itistasks.data.db.dao.RateDao
import com.itis.itistasks.data.db.dao.UserDao
import com.itis.itistasks.data.db.entities.FilmEntity
import com.itis.itistasks.data.db.entities.RateEntity
import com.itis.itistasks.data.db.entities.UserEntity
import com.itis.itistasks.data.db.entities.relations.UserFilmCrossRef

@Database(entities=[
    UserEntity::class,
    FilmEntity::class,
    RateEntity::class,
    UserFilmCrossRef::class
                   ], version=1)
abstract class FilmsAppDatabase : RoomDatabase() {
    abstract fun getUserDao() : UserDao
    abstract fun getFilmDao() : FilmDao
    abstract fun getRateDao() : RateDao
}