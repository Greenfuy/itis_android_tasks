package com.itis.itistasks.presentation.model

data class WeatherUiModel(
    val icon: String,
    val mainData: WeatherMainUiModel,
)

data class WeatherMainUiModel(
    val temperature: Float,
)

