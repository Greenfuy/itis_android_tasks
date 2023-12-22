package com.itis.itistasks.data.db.entities.relations

import androidx.room.Entity
import androidx.room.ForeignKey
import com.itis.itistasks.data.db.entities.FilmEntity
import com.itis.itistasks.data.db.entities.UserEntity

@Entity(
    tableName = "favorites",
    primaryKeys = ["userId", "filmId"],
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    ),
        ForeignKey(
        entity = FilmEntity::class,
        parentColumns = ["filmId"],
        childColumns = ["filmId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class UserFilmCrossRef(
    val userId: Int,
    val filmId: Int,
)