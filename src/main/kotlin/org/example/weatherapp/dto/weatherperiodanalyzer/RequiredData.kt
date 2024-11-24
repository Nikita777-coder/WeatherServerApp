package org.example.weatherapp.dto.weatherperiodanalyzer

import java.time.LocalDate
import java.util.*

class RequiredData(
    private val addressId: UUID,
    private val startDate: LocalDate,
    private val endDate: LocalDate
) {
}