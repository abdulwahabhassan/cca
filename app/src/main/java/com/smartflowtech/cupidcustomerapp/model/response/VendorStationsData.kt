package com.smartflowtech.cupidcustomerapp.model.response

import com.squareup.moshi.Json


typealias VendorStationsData = List<VendorStation>

data class VendorStation(
    @Json(name = "id")
    val id: Long,

    @Json(name = "name")
    val name: String?,

    @Json(name = "code")
    val code: String?,

    @Json(name = "address")
    val address: String?,

    @Json(name = "opening_time")
    val openingTime: String?,

    @Json(name = "manager_name")
    val managerName: String?,

    @Json(name = "manager_phone")
    val managerPhone: String?,

    @Json(name = "manager_email")
    val managerEmail: String?,

    @Json(name = "city")
    val city: String?,

    @Json(name = "state")
    val state: String?,

    @Json(name = "is_station_enabled")
    val isStationEnabled: Long?,

    @Json(name = "IsActive")
    val isActive: Long?,

    @Json(name = "IsOnline")
    val isOnline: Long?,

    @Json(name = "longitude")
    val longitude: String?,

    @Json(name = "latitude")
    val latitude: String?
)

