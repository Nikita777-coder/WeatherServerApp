package org.example.weatherapp.dto.weatherperiodanalyzer.precepition

import org.example.weatherapp.dto.weatherperiodanalyzer.RequiredData
import java.time.LocalDate
import java.util.*


class PrecipitationBody(
    val requiredData: RequiredData,
    val precipitationTags: List<PrecipitationType>
) {
}