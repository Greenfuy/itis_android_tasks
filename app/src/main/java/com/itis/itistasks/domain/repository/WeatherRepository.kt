package com.itis.itistasks.domain.repository

import com.itis.itistasks.domain.model.WeatherDomainModel

interface WeatherRepository {
    suspend fun getCurrentWeatherByCityName(city: String): WeatherDomainModel
}