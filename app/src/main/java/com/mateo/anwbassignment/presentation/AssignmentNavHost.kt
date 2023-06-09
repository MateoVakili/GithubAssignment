package com.mateo.anwbassignment.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.mateo.anwbassignment.presentation.github.navigation.repositoriesNavGraph
import com.mateo.anwbassignment.presentation.github.navigation.repositoryGraphRoute

@Composable
fun AssignmentNavHost(
    appState: AppState,
    startDestination: String = repositoryGraphRoute,
) {
    NavHost(
        navController = appState.navController,
        startDestination = startDestination,
    ) {
        repositoriesNavGraph(appState = appState)
    }
}