package com.mateo.anwbassignment.core.di

import com.mateo.anwbassignment.data.api.adapter.OffsetDateTimeAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun moshiBuilder(): Moshi.Builder {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(OffsetDateTimeAdapter())
    }
}