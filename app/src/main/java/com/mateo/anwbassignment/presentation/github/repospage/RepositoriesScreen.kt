package com.mateo.anwbassignment.presentation.github.repospage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.mateo.anwbassignment.R
import com.mateo.anwbassignment.domain.core.EmptyResponse
import com.mateo.anwbassignment.domain.github.factory.GithubRepositoriesItemDomainModelFactory
import com.mateo.anwbassignment.domain.github.model.GithubRepositoriesItemDomainModel
import com.mateo.anwbassignment.presentation.util.view.AvatarView
import com.mateo.anwbassignment.presentation.util.view.ErrorView
import com.mateo.anwbassignment.presentation.util.view.Loading
import com.mateo.anwbassignment.presentation.util.view.PreviewBackground
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun RepositoriesScreen(
    navigateToDetailScreen: (GithubRepositoriesItemDomainModel) -> Unit,
    viewModel: RepositoriesViewModel = hiltViewModel()
) {
    viewModel.githubRepositories.collectAsLazyPagingItems().run {
        RepositoriesScreenStateless(
            githubRepositories = this,
            loadState = this.loadState,
            onRepoClickedAction = { navigateToDetailScreen(it) },
            onRetryAction = { retry() }
        )
    }
}

@Composable
private fun RepositoriesScreenStateless(
    loadState: CombinedLoadStates,
    githubRepositories: LazyPagingItems<GithubRepositoriesItemDomainModel>,
    onRetryAction: () -> Unit,
    onRepoClickedAction: (GithubRepositoriesItemDomainModel) -> Unit,
) {
    Column {
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            when (val state = loadState.refresh) {
                is LoadState.NotLoading -> {
                    if (githubRepositories.itemCount == 0) {
                        item {
                            ErrorView(
                                error = EmptyResponse(), // empty message with mapper
                                onReload = onRetryAction,
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                    } else {
                        items(githubRepositories.itemCount) {
                            githubRepositories[it]?.let { item ->
                                if (it == 0) {
                                    Spacer(modifier = Modifier.height(24.dp))
                                }
                                RepositoryItem(
                                    item = item,
                                    onRepoClickedAction = onRepoClickedAction
                                )
                            }
                        }
                    }
                }

                is LoadState.Loading -> loadingContent(
                    modifier = Modifier.padding(top = 24.dp, bottom = 32.dp),
                    text = R.string.loading_title
                )

                is LoadState.Error -> errorContent(
                    modifier = Modifier.padding(top = 24.dp, bottom = 32.dp),
                    throwable = state.error,
                    onReload = onRetryAction,
                )
            }
            when (val state = loadState.append) {
                is LoadState.NotLoading -> Unit
                is LoadState.Loading -> loadingContent(
                    modifier = Modifier.padding(bottom = 32.dp),
                    text = R.string.loading_more_title
                )

                is LoadState.Error -> errorContent(
                    modifier = Modifier.padding(bottom = 32.dp),
                    throwable = state.error,
                    onReload = onRetryAction,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RepositoryItem(
    item: GithubRepositoriesItemDomainModel,
    onRepoClickedAction: (GithubRepositoriesItemDomainModel) -> Unit,
) {
    Card(
        onClick = { onRepoClickedAction(item) },
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onPrimaryContainer)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                item.owner.let { owner ->
                    AvatarView(
                        size = 32.dp,
                        url = owner.avatarUrl
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    item.name,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                item.language?.let { language ->
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        imageVector = Icons.Default.Build,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(end = 4.dp),
                        contentDescription = null
                    )
                    Text(language)
                }
            }
            item.description?.let { description ->
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = description
                )
            }
        }
    }
}

private fun LazyListScope.loadingContent(modifier: Modifier, text: Int) {
    item {
        Loading(
            modifier = modifier,
            text = stringResource(id = text)
        )
    }
}

private fun LazyListScope.errorContent(
    modifier: Modifier,
    throwable: Throwable,
    onReload: () -> Unit,
) {
    item {
        ErrorView(
            error = throwable,
            onReload = onReload,
            modifier = modifier
        )
    }
}

@Preview
@Composable
fun RepositoryItemPreview() {
    PreviewBackground {
        RepositoryItem(
            item = GithubRepositoriesItemDomainModelFactory.createInstance(),
            onRepoClickedAction = {}
        )
    }
}