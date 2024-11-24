package org.example.weatherapp.dto.weatherperiodanalyzer.precepition

import lombok.Getter
import lombok.Setter

@Getter
@Setter
class Precipitation {
    private var precipitationSum: ULong? = null
    private var rainSum: ULong? = null
    private var snowfallSum: ULong? = null
}