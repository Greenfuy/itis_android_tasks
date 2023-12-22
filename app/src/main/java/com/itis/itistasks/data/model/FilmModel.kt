package com.itis.itistasks.data.model

data class FilmModel(
    val name : String,
    val year : Int,
    val description : String
) {
    fun toRvModel() = RvFilmModel(name, year, false)
}