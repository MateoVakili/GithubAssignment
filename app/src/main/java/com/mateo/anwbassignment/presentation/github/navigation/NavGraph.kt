package com.mateo.anwbassignment.presentation.github.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.mateo.anwbassignment.presentation.AppState
import com.mateo.anwbassignment.presentation.github.detailpage.DetailsScreen
import com.mateo.anwbassignment.presentation.github.navigation.types.DetailsPageParamType
import com.mateo.anwbassignment.presentation.github.repospage.RepositoriesScreen

const val repositoryGraphRoute = "repositories_nav_graph"

fun NavGraphBuilder.repositoriesNavGraph(
    appState: AppState
) {
    navigation(
        route = repositoryGraphRoute,
        startDestination = RepositoriesFlowDestinations.RepositoriesRoute.route,
    ) {
        // Repositories Screen
        composable(
            route = RepositoriesFlowDestinations.RepositoriesRoute.route,
            content = {
                RepositoriesScreen(
                    navigateToDetailScreen = {
                        appState.navController.navigate(
                            RepositoriesFlowDestinations.DetailRoute.withArgs(it)
                        )
                    }
                )
            }
        )

        // Details Screen
        composable(
            route = RepositoriesFlowDestinations.DetailRoute.route,
            content =  { DetailsScreen() },
            arguments = listOf(
                navArgument(RepositoriesFlowDestinations.DetailRoute.ARG_KEY_DETAILS) {
                    type = DetailsPageParamType()
                    nullable = false
                }
            )
        )
    }
}