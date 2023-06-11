package com.mateo.anwbassignment.presentation.util.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mateo.anwbassignment.R
import com.mateo.anwbassignment.domain.core.NetworkException
import com.mateo.anwbassignment.presentation.util.error.UIExceptionMapper

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    error: Throwable,
    onReload: () -> Unit,
) {
    val mapper = UIExceptionMapper()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onPrimaryContainer)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = mapper.getTitle(LocalContext.current, error),
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = mapper.getMessage(LocalContext.current, error),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            if (mapper.isRetryEnabled(error)) {
                Button(onClick = onReload) {
                    Text(text = stringResource(id = R.string.core_button_retry), color = Color.Black)
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewError() {
    PreviewBackground {
        ErrorView(
            error = NetworkException(),
            onReload = {},
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        )
    }
}