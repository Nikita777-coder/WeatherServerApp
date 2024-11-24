package org.example.nominatimcommunicator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NominatimCommunicatorApplication

fun main(args: Array<String>) {
	runApplication<NominatimCommunicatorApplication>(*args)
}
