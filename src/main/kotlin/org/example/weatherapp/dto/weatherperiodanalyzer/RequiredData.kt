package org.example.weatherapp.dto.weatherperiodanalyzer

import org.example.weatherapp.dto.Address
import java.time.LocalDate
import java.util.*

data class RequiredData(
    val address: Address,
    val startDate: LocalDate,
    val endDate: LocalDate
) {
}