package com.itis.itistasks.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.itis.itistasks.data.db.entities.RateEntity

@Dao
interface RateDao {

    @Insert
    fun add(rate : RateEntity)

    @Query("SELECT * FROM rates WHERE filmId = :filmId AND userId = :userId")
    fun get(filmId : Int, userId : Int) : RateEntity?

    @Query("SELECT AVG(rating) FROM rates WHERE filmId = :filmId")
    fun getAverageRate(filmId : Int) : Float

    @Query("DELETE FROM rates WHERE filmId = :filmId")
    fun deleteAllMatches(filmId : Int)
}