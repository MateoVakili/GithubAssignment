package com.mateo.anwbassignment.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mateo.anwbassignment.R
import com.mateo.anwbassignment.presentation.github.navigation.RepositoriesFlowDestinations
import com.mateo.anwbassignment.presentation.github.navigation.TopDestinations
import com.mateo.anwbassignment.presentation.util.view.decode
import com.mateo.anwbassignment.presentation.util.view.parcelable

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
): AppState {
    return remember(navController) {
        AppState(navController)
    }
}

@Stable
class AppState(
    val navController: NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val isTopLevelDestination : Boolean
        @Composable get() = TopDestinations.RepositoriesRoute.route == currentDestination?.route

    private val currentBackStackEntryArg: String?
        @Composable get() = navController.currentBackStackEntry
            ?.arguments?.parcelable<RepositoriesFlowDestinations.DetailRoute.Args>(
                RepositoriesFlowDestinations.DetailRoute.ARG_KEY_DETAILS)?.repo?.decode()

    val currentTitle: String
        @Composable get() = when (currentDestination?.route) {
            TopDestinations.RepositoriesRoute.route -> stringResource(id = R.string.app_name)
            RepositoriesFlowDestinations.DetailRoute.route -> currentBackStackEntryArg ?: stringResource(id = R.string.details_title)
            else -> stringResource(id = R.string.details_title)
        }

    var shouldShowSettings by mutableStateOf(false)
        private set

    fun setShowSettings(shouldShow: Boolean) {
        shouldShowSettings = shouldShow
    }
}
