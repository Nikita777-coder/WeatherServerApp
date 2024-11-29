package org.example.weatherapp.controller

import org.example.weatherapp.dto.weatherperiodanalyzer.AverageTempBody
import org.example.weatherapp.dto.weatherperiodanalyzer.RequiredData
import org.example.weatherapp.dto.weatherperiodanalyzer.precepition.Precipitation
import org.example.weatherapp.dto.weatherperiodanalyzer.precepition.PrecipitationBody
import org.example.weatherapp.dto.weatherperiodanalyzer.yieldforecast.YieldForecastBody
import org.example.weatherapp.dto.weatherperiodanalyzer.yieldforecast.YieldForecastType
import org.example.weatherapp.service.WeatherPeriodAnalyzerService
import org.springframework.web.bind.annotation.*

// user address id is required and time period
@RestController
@RequestMapping("/weather-period-analyzer")
final class WeatherPeriodAnalyzerController(
    private val weatherPeriodAnalyzerService: WeatherPeriodAnalyzerService
) {
    @GetMapping("average-temp")
    fun getAverageTemp(@RequestBody averageTempBody: RequiredData): Double {
        return weatherPeriodAnalyzerService.getAverageTemp(averageTempBody);
    }

    @GetMapping("precipitation")
    fun getPrecipitationAmount(@RequestBody precipitationBody: PrecipitationBody): List<Precipitation> {
        return weatherPeriodAnalyzerService.getPrecipitationAmount(precipitationBody)
    }

    @GetMapping("yield-forecast")
    fun getYieldForecast(@RequestBody yieldForecast: YieldForecastBody): YieldForecastType {
        return weatherPeriodAnalyzerService.getYieldForecast(yieldForecast)
    }
}