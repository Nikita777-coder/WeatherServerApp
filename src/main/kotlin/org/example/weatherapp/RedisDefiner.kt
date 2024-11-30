package org.example.weatherapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.example.weatherapp.dto.weatherperiodanalyzer.RequiredData
import org.example.weatherapp.dto.weatherperiodanalyzer.precepition.Precipitation
import org.example.weatherapp.dto.weatherperiodanalyzer.precepition.PrecipitationBody
import org.example.weatherapp.dto.weatherperiodanalyzer.yieldforecast.YieldForecastBody
import org.example.weatherapp.dto.weatherperiodanalyzer.yieldforecast.YieldForecastType
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import java.lang.reflect.Type


@Service
class RedisDefiner(
    private val redisTemplate: ReactiveRedisTemplate<String, String>,
    private val gson: Gson
) {
    suspend fun getAvgTemp(avgTempBody: RequiredData): Double? {
        val data = redisTemplate.opsForValue().get(avgTempBody.address).awaitFirstOrNull()
        return gson.fromJson(data, Double::class.java)
    }

    suspend fun saveAvgTemp(avgTempBody: RequiredData, avgTemp: Double) {
        val saved = redisTemplate.opsForValue().set(avgTempBody.address.toString(), gson.toJson(avgTemp)).awaitFirstOrNull()

        if (saved == null || !saved) {
            throw RuntimeException("average temp didn't save")
        }
    }

    suspend fun getPrecipitationAmount(precipitationBody: PrecipitationBody): List<Precipitation> {
        val data = redisTemplate.opsForValue().get(precipitationBody.requiredData.address).awaitFirstOrNull()
        val listType: Type = object : TypeToken<ArrayList<Precipitation?>?>() {}.type
        return gson.fromJson<List<Precipitation>>(data, listType::class.java)
    }

    suspend fun savePrecerpitions(precipitationAmount: List<Precipitation>, precipitationBody: PrecipitationBody) {
        val saved = redisTemplate.opsForValue().set(precipitationBody.requiredData.address.toString(), gson.toJson(precipitationAmount)).awaitFirstOrNull()

        if (saved == null || !saved) {
            throw RuntimeException("average temp didn't save")
        }
    }

    suspend fun getYieldForecast(yieldForecastBody: YieldForecastBody): YieldForecastType? {
        val data = redisTemplate.opsForValue().get(yieldForecastBody.requiredData.address).awaitFirstOrNull()
        return gson.fromJson(data, YieldForecastType::class.java)
    }

    suspend fun saveYieldForecast(yieldForecastBody: YieldForecastBody, yieldForecast: YieldForecastType) {
        val saved = redisTemplate.opsForValue().set(yieldForecastBody.requiredData.address.toString(), gson.toJson(yieldForecast)).awaitFirstOrNull()

        if (saved == null || !saved) {
            throw RuntimeException("average temp didn't save")
        }
    }
}