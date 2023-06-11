package com.mateo.anwbassignment.core.di

import com.mateo.anwbassignment.BuildConfig
import com.mateo.anwbassignment.data.api.GithubApi
import com.mateo.anwbassignment.data.api.interceptor.GithubDataInterceptor
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
        githubDataInterceptor: GithubDataInterceptor
    ): GithubApi {

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(githubDataInterceptor)
            .build()

        val builder = Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(MoshiHelper()))

        return builder
            .baseUrl(BuildConfig.SERVER_URL)
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
