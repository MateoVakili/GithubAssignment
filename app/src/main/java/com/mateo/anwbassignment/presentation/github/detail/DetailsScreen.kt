package com.mateo.anwbassignment.presentation.github.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mateo.anwbassignment.R
import com.mateo.anwbassignment.presentation.util.view.AppTextLink
import com.mateo.anwbassignment.presentation.util.view.ErrorView
import com.mateo.anwbassignment.presentation.util.view.Loading
import com.mateo.anwbassignment.presentation.util.view.decode
import com.mateo.anwbassignment.presentation.util.view.format

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    Column(Modifier.verticalScroll(rememberScrollState())) {
        Card(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    viewModel.args.ownerAvatar?.let {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(it.decode())
                                .crossfade(true)
                                .build(),
                            placeholder = painterResource(R.drawable.avatar_placeholder),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        viewModel.args.owner,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    stringResource(id = R.string.details_repository, viewModel.args.repo),
                    style = MaterialTheme.typography.titleMedium,
                )
                AppTextLink(
                    text = viewModel.args.repoUrl,
                    url = viewModel.args.repoUrl
                )
                Divider(Modifier.padding(16.dp))
                Text(
                    stringResource(id = R.string.details_author, viewModel.args.owner),
                    style = MaterialTheme.typography.titleMedium,
                )
                AppTextLink(
                    text = viewModel.args.ownerUrl,
                    url = viewModel.args.ownerUrl
                )
                Divider(Modifier.padding(16.dp))
                when(uiState) {
                    is DetailsScreenUiState.Loading -> {
                        Loading(text = stringResource(id = R.string.loading_event_title))
                    }
                    is DetailsScreenUiState.Success -> {
                        // in repo we already check if we have an event, call to .first() should get the event
                        // if the item is not there DetailsScreenUiState.Error  will be sent instead which is handled below
                        val lastEvent = uiState.data.first()
                        Text(
                            stringResource(id = R.string.last_event_author, lastEvent.actor.displayLogin),
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            stringResource(id = R.string.last_event_type, lastEvent.type),
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            stringResource(id = R.string.last_event_date, lastEvent.createdAt.format()),
                            style = MaterialTheme.typography.titleMedium,
                        )
                        AppTextLink(
                            text = lastEvent.actor.url,
                            url = lastEvent.actor.url
                        )
                    }
                    is DetailsScreenUiState.Error -> {
                        ErrorView(error = uiState.throwable) {
                            viewModel.onRetry()
                        }
                    }
                }
            }
        }
    }
}