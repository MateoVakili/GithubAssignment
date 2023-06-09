package com.mateo.anwbassignment.presentation.github.repos

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mateo.anwbassignment.R
import com.mateo.anwbassignment.domain.core.EmptyResponse
import com.mateo.anwbassignment.domain.github.model.GithubRepositoriesItemDomainModel
import com.mateo.anwbassignment.presentation.AppState
import com.mateo.anwbassignment.presentation.util.view.ErrorView
import com.mateo.anwbassignment.presentation.util.view.Loading
import com.mateo.anwbassignment.presentation.util.view.SettingsDialog

@Composable
fun RepositoriesScreen(
    appState: AppState,
    navigateToDetailScreen: (GithubRepositoriesItemDomainModel) -> Unit,
    viewModel: RepositoriesViewModel = hiltViewModel()
) {
    val githubRepositories = viewModel.githubRepositories.collectAsLazyPagingItems()
    val isShowingDialog = rememberSaveable { mutableStateOf(false) }
    if (isShowingDialog.value || appState.shouldShowSettings) {
        isShowingDialog.value = true
        appState.setShowSettings(true)
        SettingsDialog(
            currentSortOption = viewModel.sortOption.collectAsState().value,
            onDismissAction = {
                isShowingDialog.value = false
                appState.setShowSettings(false)
            },
            onSortOptionAction = {
                viewModel.updateSortOption(it)
            }
        )
    }

    RepositoriesScreenStateless(
        githubRepositories = githubRepositories,
        loadState = githubRepositories.loadState,
        onRepoClickedAction = { args ->
            navigateToDetailScreen(args)
        },
        onRetryAction = {
            githubRepositories.retry()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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
                                error = EmptyResponse(),
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
                                Card(
                                    onClick = {
                                        onRepoClickedAction(item)
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .background(MaterialTheme.colorScheme.onPrimaryContainer)
                                            .fillMaxSize()
                                            .padding(16.dp),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            item.owner.let { owner ->
                                                AsyncImage(
                                                    model = ImageRequest.Builder(LocalContext.current)
                                                        .data(owner.avatarUrl)
                                                        .crossfade(true)
                                                        .build(),
                                                    placeholder = painterResource(R.drawable.avatar_placeholder),
                                                    contentDescription = null,
                                                    contentScale = ContentScale.Crop,
                                                    modifier = Modifier
                                                        .clip(CircleShape)
                                                        .size(32.dp)
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
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(description)
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.size(20.dp))
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
