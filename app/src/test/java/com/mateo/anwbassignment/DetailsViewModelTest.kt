package com.mateo.anwbassignment

import androidx.lifecycle.SavedStateHandle
import com.mateo.anwbassignment.domain.core.AssignmentExceptions
import com.mateo.anwbassignment.domain.core.LoadingResult
import com.mateo.anwbassignment.domain.github.model.ActorDomainModel
import com.mateo.anwbassignment.domain.github.model.GithubEventDomainModel
import com.mateo.anwbassignment.domain.github.repository.GithubRepoInfoRepository
import com.mateo.anwbassignment.presentation.github.detail.DetailsPageArg
import com.mateo.anwbassignment.presentation.github.detail.DetailsScreenUiState
import com.mateo.anwbassignment.presentation.github.detail.DetailsViewModel
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
import java.time.OffsetDateTime

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class DetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: DetailsViewModel
    private val repository = mockk<GithubRepoInfoRepository>()

    @Test
    fun testSuccessCase() = runTest {
        coEvery {
            repository.getLatestEvent(
                fakeArgs.owner,
                fakeArgs.repo
            )
        } returns LoadingResult.Success(
            listOf(fakeEvent)
        )
        viewModel = DetailsViewModel(SavedStateHandle().apply {
            set(DetailRoute.ARG_KEY_DETAILS, fakeArgs)
        }, repository)
        assertEquals(DetailsScreenUiState.Success(listOf(fakeEvent)), viewModel.uiState.value)
    }

    @Test
    fun testErrorCase() = runTest {
        coEvery {
            repository.getLatestEvent(
                fakeArgs.owner,
                fakeArgs.repo
            )
        } returns LoadingResult.Error(AssignmentExceptions())
        viewModel = DetailsViewModel(SavedStateHandle().apply {
            set(DetailRoute.ARG_KEY_DETAILS, fakeArgs)
        }, repository)
        assert(viewModel.uiState.value is DetailsScreenUiState.Error)
    }


    private val fakeArgs = DetailsPageArg(
        "owner",
        "ownerUrl",
        "ownerAvatar",
        "repo",
        "repoUrl"
    )

    private val fakeEvent = GithubEventDomainModel(
        ActorDomainModel(
            "avatar_url",
            "display_login",
            "url"
        ),
        OffsetDateTime.now(),
        "type"
    )
}