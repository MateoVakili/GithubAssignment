package com.mateo.anwbassignment.core.di

import com.mateo.anwbassignment.data.api.error.ExceptionMapper
import com.mateo.anwbassignment.data.api.error.ExceptionMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ExceptionMapperModule {

    @Provides
    fun exceptionMapper(): ExceptionMapper =
        ExceptionMapperImpl()
}