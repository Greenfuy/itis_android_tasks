package com.itis.itistasks.data.db.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.itis.itistasks.data.db.entities.FilmEntity
import com.itis.itistasks.data.db.entities.UserEntity

data class UserWithFavoriteFilms(
    @Embedded
    val user: UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "filmId",
        associateBy = Junction(UserFilmCrossRef::class)
    )
    val films: List<FilmEntity>
)