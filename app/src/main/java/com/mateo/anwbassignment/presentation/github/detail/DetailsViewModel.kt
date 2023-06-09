package com.mateo.anwbassignment.presentation.github.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateo.anwbassignment.domain.core.LoadingResult
import com.mateo.anwbassignment.domain.github.model.GithubEventDomainModel
import com.mateo.anwbassignment.domain.github.repository.GithubRepoInfoRepository
import com.mateo.anwbassignment.presentation.github.navigation.RepositoriesFlowDestinations.DetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: GithubRepoInfoRepository,
) : ViewModel() {

    val args: DetailRoute.Args = checkNotNull(savedStateHandle[DetailRoute.ARG_KEY_DETAILS])
    private val _uiState = MutableStateFlow<DetailsScreenUiState>(DetailsScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getLatestEvents()
    }

    private fun getLatestEvents() = viewModelScope.launch {
        _uiState.value = DetailsScreenUiState.Loading
        when(
            val res = repository.getLatestEvent(
                user = args.owner,
                repo = args.repo
            )
        ) {
            is LoadingResult.Success -> {
                _uiState.value = DetailsScreenUiState.Success(res.data)
            }
            is LoadingResult.Error -> {
                _uiState.value = DetailsScreenUiState.Error(res.exception)
            }
        }
    }

    fun onRetry() {
        if(_uiState.value != DetailsScreenUiState.Loading) {
            getLatestEvents()
        }
    }
}

sealed interface DetailsScreenUiState {
    object Loading : DetailsScreenUiState
    data class Success(val data: List<GithubEventDomainModel>) : DetailsScreenUiState
    data class Error(val throwable: Throwable) : DetailsScreenUiState
}

