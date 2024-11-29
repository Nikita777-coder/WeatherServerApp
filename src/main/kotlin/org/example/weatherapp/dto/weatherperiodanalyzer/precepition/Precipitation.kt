package org.example.weatherapp.dto.weatherperiodanalyzer.precepition

data class Precipitation(
    var precipitationSum: ULong? = null,
    var rainSum: ULong? = null,
    var snowfallSum: ULong? = null
) {
}