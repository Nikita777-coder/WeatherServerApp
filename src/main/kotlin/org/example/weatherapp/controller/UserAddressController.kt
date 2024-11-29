package org.example.weatherapp.controller

import jakarta.validation.Valid
import org.example.weatherapp.dto.Address
import org.example.weatherapp.service.UserAddressService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/user-addresses")
final class UserAddressController(
    private val userAddressService: UserAddressService
) {
    @GetMapping
    fun getAddresses(@RequestParam userId: UUID): List<Address> {
        throw NotImplementedError()
    }

    @PostMapping
    fun pushNewAddress(@RequestBody address: Address): UUID {
        throw NotImplementedError()
    }

    @GetMapping("default")
    fun getDefaultUserAddress(): Address {
        throw NotImplementedError()
    }

    @PatchMapping("default")
    fun updateDefaultUserAddress(): Address {
        throw NotImplementedError()
    }
}