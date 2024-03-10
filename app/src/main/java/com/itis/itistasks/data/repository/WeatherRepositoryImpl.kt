package com.itis.itistasks.data.repository

import com.itis.itistasks.R
import com.itis.itistasks.data.exceptions.EmptyWeatherResponseException
import com.itis.itistasks.data.mapper.WeatherDomainModelMapper
import com.itis.itistasks.data.remote.OpenWeatherApi
import com.itis.itistasks.domain.model.WeatherDomainModel
import com.itis.itistasks.domain.model.isEmptyResponse
import com.itis.itistasks.domain.repository.WeatherRepository
import com.itis.itistasks.utils.ResManager

class WeatherRepositoryImpl(
    private val api: OpenWeatherApi,
    private val domainModelMapper: WeatherDomainModelMapper,
    private val resManager: ResManager,
) : WeatherRepository {

    override suspend fun getCurrentWeatherByCityName(city: String): WeatherDomainModel {
        val domainModel = domainModelMapper.mapResponseToDomainModel(
            input = api.getCurrentWeatherByCity(city = city)
        )
        return if (domainModel != null && domainModel.isEmptyResponse().not()) {
            domainModel
        } else {
            throw EmptyWeatherResponseException(message = resManager.getString(R.string.empty_weather_response))
        }
    }
}