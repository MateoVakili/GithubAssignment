package com.mateo.anwbassignment.presentation.github.repospage

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
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    repository: GithubRepoInfoRepository,
    @Dispatcher(AssignmentDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    var githubRepositories: Flow<PagingData<GithubRepositoriesItemDomainModel>> =
        repository.searchRepositories(
            users = AssignmentRequiredUsers.values().map { it.value },
            // e.g could add a settings option for this to be sorted by stars for example
            sortOption = SortingOptions.UPDATED
        )
        .flowOn(ioDispatcher)
        .cachedIn(viewModelScope)
}