package com.mateo.anwbassignment.core.di

import com.mateo.anwbassignment.data.api.GithubApi
import com.mateo.anwbassignment.data.api.interceptor.GithubMetaDataInterceptor
import com.mateo.anwbassignment.domain.core.network.MoshiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideApi(
        loggingInterceptor: HttpLoggingInterceptor,
        githubMetaDataInterceptor: GithubMetaDataInterceptor
    ): GithubApi {

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(githubMetaDataInterceptor)
            .build()

        val builder = Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(MoshiHelper()))

        // we don't have debug/test/staging/release builds so base url is the same
        // hence why its just hardcoded here for now instead of buildConf in gradle for example
        return builder
            .baseUrl("https://api.github.com")
            .build()
            .create(GithubApi::class.java)
    }

    @Provides
    fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        // Remove this on release version. on this assignment not needed.
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }
}
