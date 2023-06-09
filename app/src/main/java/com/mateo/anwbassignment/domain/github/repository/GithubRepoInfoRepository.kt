package com.mateo.anwbassignment.domain.github.repository

import androidx.paging.PagingData
import com.mateo.anwbassignment.domain.core.LoadingResult
import com.mateo.anwbassignment.domain.github.model.GithubEventDomainModel
import com.mateo.anwbassignment.domain.github.model.GithubRepositoriesItemDomainModel
import com.mateo.anwbassignment.domain.github.model.SortingOptions
import kotlinx.coroutines.flow.Flow

interface GithubRepoInfoRepository {
    fun searchRepositories(users: List<String>, sortOption: SortingOptions): Flow<PagingData<GithubRepositoriesItemDomainModel>>
    suspend fun getLatestEvent(user: String, repo: String): LoadingResult<List<GithubEventDomainModel>>
}


