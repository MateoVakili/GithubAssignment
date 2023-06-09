package com.mateo.anwbassignment.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mateo.anwbassignment.R
import com.mateo.anwbassignment.presentation.github.navigation.RepositoriesFlowDestinations.DetailRoute
import com.mateo.anwbassignment.presentation.github.navigation.RepositoriesFlowDestinations.RepositoriesRoute
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
        @Composable get() = RepositoriesRoute.route == currentDestination?.route

    private val currentBackStackEntryArg: String?
        @Composable get() = navController.currentBackStackEntry
            ?.arguments?.parcelable<DetailRoute.Args>(
                DetailRoute.ARG_KEY_DETAILS)?.repo?.decode()

    val currentTitle: String
        @Composable get() = when (currentDestination?.route) {
            RepositoriesRoute.route -> stringResource(id = R.string.app_name)
            DetailRoute.route -> currentBackStackEntryArg ?: stringResource(id = R.string.details_title)
            else -> stringResource(id = R.string.details_title)
        }
}
