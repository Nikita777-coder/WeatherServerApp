package org.example.weatherapp.dto

import lombok.Getter
import lombok.Setter
import java.util.UUID

// чекнуть, что streetNumber > 0 при его заполнении

@Getter
@Setter
class Address(
    private val city: String,
    private val country: String,
    private var streetName: String?,
    private var streetNumber: Int? = -1,
    private var state: String?,
    private var postalcode: String?) {

    private lateinit var id: UUID
}