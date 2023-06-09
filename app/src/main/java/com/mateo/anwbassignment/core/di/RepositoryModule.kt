package com.mateo.anwbassignment.core.di

import com.mateo.anwbassignment.data.github.repository.GithubRepoInfoRepositoryImpl
import com.mateo.anwbassignment.domain.github.repository.GithubRepoInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun githubRepoInfoRepository(
        githubRepoInfoRepository: GithubRepoInfoRepositoryImpl
    ): GithubRepoInfoRepository
}