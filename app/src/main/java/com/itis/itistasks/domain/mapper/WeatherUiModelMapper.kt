package com.itis.itistasks.domain.mapper

import com.itis.itistasks.domain.model.WeatherDomainModel
import com.itis.itistasks.presentation.model.WeatherMainUiModel
import com.itis.itistasks.presentation.model.WeatherUiModel

class WeatherUiModelMapper {

    fun mapDomainToUiModel(input: WeatherDomainModel): WeatherUiModel {
        with(input) {
            return WeatherUiModel(
                icon = icon,
                mainData = WeatherMainUiModel(
                    temperature = mainData.temperature
                )
            )
        }
    }
}