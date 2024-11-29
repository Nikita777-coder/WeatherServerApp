package org.example.weatherapp.dto

import lombok.Getter
import lombok.Setter
import java.util.UUID

// чекнуть, что streetNumber > 0 при его заполнении

@Getter
@Setter
class Address(
    val city: String,
    val country: String,
    var streetName: String?,
    var streetNumber: Int? = -1,
    var state: String?,
    var postalcode: String?) {

    private lateinit var id: UUID
    fun getId(): UUID {
        return id
    }
}