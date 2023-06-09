package com.mateo.anwbassignment.presentation.github.repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mateo.anwbassignment.data.api.network.AssignmentDispatchers
import com.mateo.anwbassignment.data.api.network.Dispatcher
import com.mateo.anwbassignment.domain.github.model.AssignmentRequiredUsers
import com.mateo.anwbassignment.domain.github.model.GithubRepositoriesItemDomainModel
import com.mateo.anwbassignment.domain.github.model.SortingOptions
import com.mateo.anwbassignment.domain.github.repository.GithubRepoInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val repository: GithubRepoInfoRepository,
    @Dispatcher(AssignmentDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _sortOption = MutableStateFlow(SortingOptions.UPDATED)
    val sortOption = _sortOption.asStateFlow()

    var githubRepositories: Flow<PagingData<GithubRepositoriesItemDomainModel>> = getPagingSource()

    fun updateSortOption(option: SortingOptions) {
        _sortOption.value = option
        githubRepositories = getPagingSource()
    }

    private fun getPagingSource() = repository
        .searchRepositories(
            users = AssignmentRequiredUsers.values().map { it.value },
            sortOption = _sortOption.value
        )
        .flowOn(ioDispatcher)
        .cachedIn(viewModelScope)
}