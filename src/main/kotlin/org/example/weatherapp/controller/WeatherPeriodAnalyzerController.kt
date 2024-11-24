package org.example.weatherapp.controller

import org.example.weatherapp.dto.weatherperiodanalyzer.AverageTempBody
import org.example.weatherapp.dto.weatherperiodanalyzer.precepition.Precipitation
import org.example.weatherapp.dto.weatherperiodanalyzer.precepition.PrecipitationBody
import org.example.weatherapp.dto.weatherperiodanalyzer.yieldforecast.YieldForecastBody
import org.example.weatherapp.dto.weatherperiodanalyzer.yieldforecast.YieldForecastType
import org.springframework.web.bind.annotation.*

// user address id is required and time period
@RestController
@RequestMapping("/weather-period-analyzer")
class WeatherPeriodAnalyzerController {
    @GetMapping("average-temp")
    fun getAverageTemp(@RequestBody averageTempBody: AverageTempBody): Double {
        throw NotImplementedError()
    }

    @GetMapping("precipitation")
    fun getPrecipitationAmount(@RequestBody precipitationBody: PrecipitationBody): List<Precipitation> {
        throw NotImplementedError()
    }

    @GetMapping("yield-forecast")
    fun getYieldForecast(@RequestBody yieldForecast: YieldForecastBody): YieldForecastType {
        throw NotImplementedError()
    }
}