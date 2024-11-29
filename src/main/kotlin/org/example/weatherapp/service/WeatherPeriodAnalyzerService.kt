package org.example.weatherapp.service

import org.example.weatherapp.RedisDefiner
import org.example.weatherapp.dto.weatherperiodanalyzer.RequiredData
import org.example.weatherapp.dto.weatherperiodanalyzer.precepition.Precipitation
import org.example.weatherapp.dto.weatherperiodanalyzer.precepition.PrecipitationBody
import org.example.weatherapp.dto.weatherperiodanalyzer.precepition.PrecipitationType
import org.example.weatherapp.dto.weatherperiodanalyzer.yieldforecast.YieldForecastBody
import org.example.weatherapp.dto.weatherperiodanalyzer.yieldforecast.YieldForecastType
import org.springframework.stereotype.Service

@Service
final class WeatherPeriodAnalyzerService(
    private val redisDefiner: RedisDefiner,
    private val weatherPeriodAnalyzerRepository: WeatherPeriodAnalyzerRepository,
    private val openMeteoApiComminicator: OpenMeteoApiComminicator,
//    private val dataManager: DataManager,
) {
    fun getAverageTemp(avgTempBody: RequiredData): Double {
        var avgTemp: Double? = redisDefiner.getAvgTemp(avgTempBody)

        if (avgTemp == null) {
            val temps: List<Temp> = getTempsForData(avgTempBody)

            avgTemp = 0.0
            temps.forEach {avgTemp += it.temp}
            avgTemp /= temps.size

            redisDefiner.saveAvgTemp(avgTemp)
        }

        return avgTemp
    }

    fun getPrecipitationAmount(precipitationBody: PrecipitationBody): List<Precipitation> {
        var precipitationAmount: List<Precipitation> = redisDefiner.getPrecipitationAmount(precipitationBody)

        if (precipitationAmount.isEmpty()) {
            precipitationAmount = weatherPeriodAnalyzerRepository.getPrecepitions(precipitationBody)

            if (precipitationAmount.isEmpty()) {
                precipitationAmount = openMeteoApiComminicator.getPrecepitions(precipitationBody)
            }

            weatherPeriodAnalyzerRepository.savePrecerpitions(precipitationAmount, precipitationBody.requiredData.address)
            redisDefiner.savePrecerpitions(precipitationAmount, precipitationBody.requiredData.address)
        }

        return precipitationAmount
    }

    fun getYieldForecast(yieldForecastBody: YieldForecastBody): YieldForecastType {
        var yieldForecast: YieldForecastType? = redisDefiner.getYieldForecast(yieldForecastBody)

        if (yieldForecast == null) {
            var avgTemp: Double = getAverageTemp(yieldForecastBody.requiredData)

            var percipitionAmount: ULong = getPrecipitationAmount(
                PrecipitationBody(yieldForecastBody.requiredData,
                    listOf(PrecipitationType.ALL)
                )
            )[0].precipitationSum ?: 0u

            yieldForecast = when {
                avgTemp > 20 && percipitionAmount > 50u -> YieldForecastType.HIGH
                avgTemp in 10.0..20.0 -> YieldForecastType.MIDDLE
                avgTemp < 10 -> YieldForecastType.LOW
                else -> YieldForecastType.UNDEFINED
            }
        }

        return yieldForecast
    }

    fun getTempsForData(requiredData: RequiredData): List<Temp> {
        var temps: List<Temp> = weatherPeriodAnalyzerRepository.getTempsForSeason(requiredData)

        if (temps.isEmpty()) {
            temps = openMeteoApiComminicator.getTempsForAddress(requiredData)
            weatherPeriodAnalyzerRepository.saveTempForAddress(temps, requiredData)
        }

        return temps
    }
}