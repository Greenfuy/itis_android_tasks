package com.itis.itistasks.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.itis.itistasks.data.model.FilmModel

@Entity(tableName="films")
data class FilmEntity(
    @PrimaryKey(autoGenerate=true)
    val filmId : Int = 0,
    val name : String,
    val year : Int,
    val description : String
) {
    fun toModel() = FilmModel(
        name = name,
        year = year,
        description = description
    )
}