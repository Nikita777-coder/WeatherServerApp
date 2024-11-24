package org.example.weatherapp.dto.weatherperiodanalyzer

import lombok.Getter
import java.time.LocalDate
import java.util.UUID

@Getter
class AverageTempBody(
    private val requiredData: RequiredData
) {
}