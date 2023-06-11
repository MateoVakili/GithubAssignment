package com.mateo.anwbassignment

import androidx.lifecycle.SavedStateHandle
import com.mateo.anwbassignment.domain.core.AssignmentExceptions
import com.mateo.anwbassignment.domain.core.LoadingResult
import com.mateo.anwbassignment.domain.github.factory.GithubEventDomainModelFactory
import com.mateo.anwbassignment.domain.github.factory.GithubRepositoryDetailsFactory
import com.mateo.anwbassignment.domain.github.repository.GithubRepoInfoRepository
import com.mateo.anwbassignment.presentation.github.detailpage.DetailsScreenUiState
import com.mateo.anwbassignment.presentation.github.detailpage.DetailsViewModel
import com.mateo.anwbassignment.presentation.github.navigation.RepositoriesFlowDestinations.DetailRoute
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private lateinit var viewModel: DetailsViewModel
    private val repository = mockk<GithubRepoInfoRepository>()
    private val fakeArgument = GithubRepositoryDetailsFactory.createInstance()
    private val fakeEvents = listOf(GithubEventDomainModelFactory.createInstance())

    @Test
    fun testSuccessCase() = runTest {
        coEvery {
            repository.getLatestEvent(
                fakeArgument.owner,
                fakeArgument.repo
            )
        } returns LoadingResult.Success(fakeEvents)
        viewModel = DetailsViewModel(SavedStateHandle().apply {
            set(DetailRoute.ARG_KEY_DETAILS, fakeArgument)
        }, repository)
        assertEquals(DetailsScreenUiState.Success(fakeEvents), viewModel.uiState.value)
    }

    @Test
    fun testErrorCase() = runTest {
        coEvery {
            repository.getLatestEvent(
                fakeArgument.owner,
                fakeArgument.repo
            )
        } returns LoadingResult.Error(AssignmentExceptions())
        viewModel = DetailsViewModel(SavedStateHandle().apply {
            set(DetailRoute.ARG_KEY_DETAILS, fakeArgument)
        }, repository)
        assert(viewModel.uiState.value is DetailsScreenUiState.Error)
    }
}