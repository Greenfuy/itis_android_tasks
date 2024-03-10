package com.itis.itistasks.domain.usecase

import com.itis.itistasks.domain.mapper.WeatherUiModelMapper
import com.itis.itistasks.domain.repository.WeatherRepository
import com.itis.itistasks.presentation.model.WeatherUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetWeatherDataUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val repository: WeatherRepository,
    private val mapper: WeatherUiModelMapper,
) {

    suspend operator fun invoke(city: String): WeatherUiModel {
        return withContext(dispatcher) {
            val weatherData = repository.getCurrentWeatherByCityName(city = city)
            mapper.mapDomainToUiModel(weatherData)
        }
    }
}