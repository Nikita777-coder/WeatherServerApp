package org.example.weatherapp

import com.google.gson.Gson
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisDefiner(
    private val redisTemplate: ReactiveRedisTemplate<String, String>,
    private val gson: Gson
) {
    pu
}