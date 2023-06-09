package com.mateo.anwbassignment.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mateo.anwbassignment.presentation.util.view.Toolbar

@Composable
fun AssignmentApp(
    appState: AppState = rememberAppState()
) {
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxSize()
    ) {
        Toolbar(
            title = appState.currentTitle,
            navigationIcon = {
                if (!appState.isTopLevelDestination) {
                    IconButton(onClick = { appState.navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                        )
                    }
                }
            }
        )
        AssignmentNavHost(appState)
    }
}
