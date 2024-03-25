package com.itis.itistasks.domain.model

data class WeatherDomainModel(
    val icon: String,
    val mainData: WeatherMainDomainModel,
)

fun WeatherDomainModel.isEmptyResponse(): Boolean {
    val isMainDataEmpty = with(this.mainData) {
        temperature == 0f
    }
    return isMainDataEmpty
}

