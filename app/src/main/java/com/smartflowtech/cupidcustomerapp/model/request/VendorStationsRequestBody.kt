package com.smartflowtech.cupidcustomerapp.model.request

import com.squareup.moshi.Json

data class VendorStationsRequestBody(
    @Json(name = "vendor_id")
    val vendorId: Long
)
