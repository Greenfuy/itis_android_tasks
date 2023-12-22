package com.itis.itistasks.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "rates",
    foreignKeys = [ForeignKey(
        entity = FilmEntity::class,
        parentColumns = ["filmId"],
        childColumns = ["filmId"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["filmId", "userId"], unique = true)]
)
data class RateEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rating_id")
    val ratingId: Int = 0,
    val filmId: Int,
    val userId: Int,
    val rating: Int
)