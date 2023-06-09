package com.mateo.anwbassignment.data.github.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mateo.anwbassignment.data.api.GithubApi
import com.mateo.anwbassignment.data.api.error.ExceptionMapper
import com.mateo.anwbassignment.data.github.mapper.toDomainModels
import com.mateo.anwbassignment.domain.github.model.GithubRepositoriesItemDomainModel
import com.mateo.anwbassignment.domain.github.model.SortingOptions

internal class GithubRepoPagingSource(
    private val api: GithubApi,
    private val users: List<String>,
    private val sortOption: SortingOptions,
    private val exceptionMapper: ExceptionMapper,
) : PagingSource<Int, GithubRepositoriesItemDomainModel>() {

    override fun getRefreshKey(state: PagingState<Int, GithubRepositoriesItemDomainModel>): Int? {
        val key = state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
        return key
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepositoriesItemDomainModel> {
        return try {
            val pageNumber = params.key ?: INIT_PAGE_NO
            val data = api.getRepository(
                page = pageNumber,
                sort = sortOption.value,
                q = buildString { users.forEach { append(GITHUB_USER_QUALIFIER + it) } }
            ).items.map { it.toDomainModels() }
            LoadResult.Page(
                data = data,
                prevKey = if (pageNumber == INIT_PAGE_NO) null else pageNumber - 1,
                nextKey = if (data.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(exceptionMapper.mapException(e))
        }
    }

    companion object {
        private const val INIT_PAGE_NO = 1
        private const val GITHUB_USER_QUALIFIER = "+user:"
    }
}