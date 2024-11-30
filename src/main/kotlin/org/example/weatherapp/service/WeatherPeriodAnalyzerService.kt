package org.example.weatherapp.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import org.example.weatherapp.RedisDefiner
import org.example.weatherapp.dto.Temp
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
    suspend fun getAverageTemp(avgTempBody: RequiredData): Double {
        val funcScope = CoroutineScope(Job() + Dispatchers.IO)
        var avgTemp: Double?

        val res = funcScope.async { return@async redisDefiner.getAvgTemp(avgTempBody) }
        avgTemp = res.await()

        if (avgTemp == null) {
            val temps: List<Temp> = getTempsForData(avgTempBody)

            avgTemp = 0.0
            temps.forEach {avgTemp += it.temp}
            avgTemp /= temps.size

            val res1 = funcScope.async {redisDefiner.saveAvgTemp(avgTempBody, avgTemp)}
            res1.await()
        }

        return avgTemp
    }

    suspend fun getPrecipitationAmount(precipitationBody: PrecipitationBody): List<Precipitation> {
        val funScope = CoroutineScope(Job() + Dispatchers.IO)
        var precipitationAmount: List<Precipitation>

        precipitationAmount = funScope.async { return@async redisDefiner.getPrecipitationAmount(precipitationBody) }.await()

        if (precipitationAmount.isEmpty()) {
            precipitationAmount = funScope.async { return@async weatherPeriodAnalyzerRepository.getPrecepitions(precipitationBody) }.await()

            if (precipitationAmount.isEmpty()) {
                precipitationAmount = funScope.async { return@async openMeteoApiComminicator.getPrecepitions(precipitationBody) }.await()
            }

            funScope.async {
                weatherPeriodAnalyzerRepository.savePrecerpitions(
                    precipitationAmount,
                    precipitationBody.requiredData.address
                )
                redisDefiner.savePrecerpitions(precipitationAmount, precipitationBody)
            }.await()
        }

        return precipitationAmount
    }

    suspend fun getYieldForecast(yieldForecastBody: YieldForecastBody): YieldForecastType {
        val funScope = CoroutineScope(Job() + Dispatchers.IO)
        var yieldForecast: YieldForecastType? = funScope.async { redisDefiner.getYieldForecast(yieldForecastBody) }.await()

        if (yieldForecast == null) {
            val avgTemp: Double = getAverageTemp(yieldForecastBody.requiredData)

            val percipitionAmount: ULong = getPrecipitationAmount(
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

            redisDefiner.saveYieldForecast(yieldForecastBody, yieldForecast)
        }

        return yieldForecast
    }

    private fun getTempsForData(requiredData: RequiredData): List<Temp> {
        val funScope = CoroutineScope(Job() + Dispatchers.IO)
        var temps: List<Temp>

        var res = funScope.async { return@async weatherPeriodAnalyzerRepository.getTempsForSeason(requiredData) }
        temps = res.await()

        if (temps.isEmpty()) {
            res = funScope.async { openMeteoApiComminicator.getTempsForAddress(requiredData) }
            temps = res.await()
            weatherPeriodAnalyzerRepository.saveTempForAddress(temps, requiredData)
        }

        return temps
    }
}