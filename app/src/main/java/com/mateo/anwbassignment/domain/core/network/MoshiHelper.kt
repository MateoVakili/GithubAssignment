package com.mateo.anwbassignment.domain.core.network

import com.mateo.anwbassignment.data.api.adapter.OffsetDateTimeAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object MoshiHelper {

    operator fun invoke(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(OffsetDateTimeAdapter())
        .build()
}