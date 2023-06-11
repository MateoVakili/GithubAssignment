package com.mateo.anwbassignment.presentation.github.detailpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateo.anwbassignment.R
import com.mateo.anwbassignment.domain.core.AssignmentExceptions
import com.mateo.anwbassignment.domain.github.factory.GithubEventDomainModelFactory
import com.mateo.anwbassignment.domain.github.factory.GithubRepositoryDetailsFactory
import com.mateo.anwbassignment.domain.github.model.GithubRepositoryDetails
import com.mateo.anwbassignment.presentation.util.view.AppTextLink
import com.mateo.anwbassignment.presentation.util.view.ErrorView
import com.mateo.anwbassignment.presentation.util.view.Loading
import com.mateo.anwbassignment.presentation.util.view.PreviewBackground
import com.mateo.anwbassignment.presentation.util.view.format

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel()
) {
    DetailsScreenStateless(
        viewModel.args,
        viewModel.uiState.collectAsState().value,
        onRetryAction = { viewModel.onRetry() }
    )
}

@Composable
fun DetailsScreenStateless(
    arguments: GithubRepositoryDetails,
    uiState: DetailsScreenUiState,
    onRetryAction: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Card(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    arguments.owner,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    stringResource(id = R.string.details_repository, arguments.repo),
                    style = MaterialTheme.typography.titleMedium,
                )
                AppTextLink(
                    text = arguments.repoUrl,
                    url = arguments.repoUrl
                )
                Divider(Modifier.padding(16.dp))
                Text(
                    stringResource(id = R.string.details_author, arguments.owner),
                    style = MaterialTheme.typography.titleMedium,
                )
                AppTextLink(
                    text = arguments.ownerUrl,
                    url = arguments.ownerUrl
                )
                Divider(Modifier.padding(16.dp))
                GithubRepoEventView(
                    uiState = uiState,
                    onRetry = { onRetryAction() }
                )
            }
        }
    }
}

@Composable
fun GithubRepoEventView(
    uiState: DetailsScreenUiState,
    onRetry: () -> Unit
) {
    when (uiState) {
        is DetailsScreenUiState.Loading -> Loading(text = stringResource(id = R.string.loading_event_title))
        is DetailsScreenUiState.Error -> ErrorView(error = uiState.throwable, onReload = onRetry)
        is DetailsScreenUiState.Success -> {
            // in GithubRepoInfoRepository we already check if we have an event, call to .first() should get the event
            // if the item is not there DetailsScreenUiState.Error  will be sent instead which is handled below
            uiState.data.first().run {
                Text(
                    stringResource(
                        id = R.string.last_event_author,
                        actor.displayLogin
                    ),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    stringResource(id = R.string.last_event_type, type),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    stringResource(
                        id = R.string.last_event_date,
                        createdAt.format()
                    ),
                    style = MaterialTheme.typography.titleMedium,
                )
                AppTextLink(
                    text = actor.url,
                    url = actor.url
                )
            }
        }
    }
}

@Preview
@Composable
fun DetailsScreenPreview() {
    PreviewBackground {
        DetailsScreenStateless(
            arguments = GithubRepositoryDetailsFactory.createInstance(),
            uiState = DetailsScreenUiState.Success(listOf(GithubEventDomainModelFactory.createInstance())),
            onRetryAction = {}
        )
    }
}

@Preview
@Composable
fun DetailsScreenErrorPreview() {
    PreviewBackground {
        DetailsScreenStateless(
            arguments = GithubRepositoryDetailsFactory.createInstance(),
            uiState = DetailsScreenUiState.Error(AssignmentExceptions()),
            onRetryAction = {}
        )
    }
}

@Preview
@Composable
fun DetailsScreenLoadingPreview() {
    PreviewBackground {
        DetailsScreenStateless(
            arguments = GithubRepositoryDetailsFactory.createInstance(),
            uiState = DetailsScreenUiState.Loading,
            onRetryAction = {}
        )
    }
}