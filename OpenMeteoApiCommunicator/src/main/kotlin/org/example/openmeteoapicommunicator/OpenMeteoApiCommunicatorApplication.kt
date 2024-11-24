package org.example.openmeteoapicommunicator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OpenMeteoApiCommunicatorApplication

fun main(args: Array<String>) {
	runApplication<OpenMeteoApiCommunicatorApplication>(*args)
}
