package com.itis.itistasks.data.mapper

import com.itis.itistasks.data.remote.pojo.response.WeatherResponse
import com.itis.itistasks.domain.model.WeatherDomainModel
import com.itis.itistasks.domain.model.WeatherMainDomainModel

class WeatherDomainModelMapper {

    fun mapResponseToDomainModel(input: WeatherResponse?): WeatherDomainModel? {
        return input?.let {
            WeatherDomainModel(
                icon = it.weatherData?.get(0)?.icon ?: "01d",
                mainData = WeatherMainDomainModel(
                    temperature = it.mainData?.temperature ?: 0f,
                ),
            )
        }
    }
}