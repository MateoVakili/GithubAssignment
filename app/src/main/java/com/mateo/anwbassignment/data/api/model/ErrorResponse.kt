package com.mateo.anwbassignment.data.api.model

import com.squareup.moshi.Json

data class ErrorResponse (
    @Json(name = "message")
    val message: kotlin.String? = null
)

