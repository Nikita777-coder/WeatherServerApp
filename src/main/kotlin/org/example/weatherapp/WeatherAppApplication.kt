package org.example.weatherapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WeatherAppApplication

fun main(args: Array<String>) {
    runApplication<WeatherAppApplication>(*args)
}
