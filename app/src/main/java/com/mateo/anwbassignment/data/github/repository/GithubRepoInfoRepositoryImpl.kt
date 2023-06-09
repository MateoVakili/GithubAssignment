package com.mateo.anwbassignment.data.github.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mateo.anwbassignment.data.api.GithubApi
import com.mateo.anwbassignment.data.api.error.ExceptionMapper
import com.mateo.anwbassignment.data.api.network.AssignmentDispatchers
import com.mateo.anwbassignment.data.api.network.Dispatcher
import com.mateo.anwbassignment.data.github.mapper.toDomainModels
import com.mateo.anwbassignment.data.github.paging.GithubRepoPagingSource
import com.mateo.anwbassignment.domain.core.EmptyResponse
import com.mateo.anwbassignment.domain.core.LoadingResult
import com.mateo.anwbassignment.domain.github.model.GithubEventDomainModel
import com.mateo.anwbassignment.domain.github.model.GithubRepositoriesItemDomainModel
import com.mateo.anwbassignment.domain.github.model.SortingOptions
import com.mateo.anwbassignment.domain.github.repository.GithubRepoInfoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepoInfoRepositoryImpl @Inject constructor(
    private val api: GithubApi,
    @Dispatcher(AssignmentDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val exceptionMapper: ExceptionMapper,
) : GithubRepoInfoRepository {

    override fun searchRepositories(
        users: List<String>,
        sortOption: SortingOptions
    ): Flow<PagingData<GithubRepositoriesItemDomainModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = SEARCH_PER_PAGE,
                initialLoadSize = SEARCH_PER_PAGE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                GithubRepoPagingSource(
                    api,
                    users,
                    sortOption,
                    exceptionMapper
                )
            },
        ).flow.flowOn(ioDispatcher)
    }

    override suspend fun getLatestEvent(
        user: String,
        repo: String
    ): LoadingResult<List<GithubEventDomainModel>> = withContext(ioDispatcher){
        try {
            val res = api.getLatestEvent(user = user, repo = repo).map { it.toDomainModels() }
            if(res.isNotEmpty()) {
                LoadingResult.Success(res)
            } else {
                LoadingResult.Error(exceptionMapper.mapException(EmptyResponse()))
            }
        } catch (exception: Exception) {
            LoadingResult.Error(exceptionMapper.mapException(exception))
        }
    }

    companion object {
        const val SEARCH_PER_PAGE = 20
    }
}
