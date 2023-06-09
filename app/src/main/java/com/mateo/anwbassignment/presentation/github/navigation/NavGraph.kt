package com.mateo.anwbassignment.presentation.github.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.mateo.anwbassignment.presentation.AppState
import com.mateo.anwbassignment.presentation.github.detail.DetailsScreen
import com.mateo.anwbassignment.presentation.github.repos.RepositoriesScreen
import com.mateo.anwbassignment.presentation.util.view.encode

const val repositoryGraphRoute = "repositories_nav_graph"

fun NavGraphBuilder.repositoriesNavGraph(
    appState: AppState
) {
    navigation(
        route = repositoryGraphRoute,
        startDestination = TopDestinations.RepositoriesRoute.route,
    ) {
        composable(route = TopDestinations.RepositoriesRoute.route) {
            RepositoriesScreen(
                appState = appState,
                navigateToDetailScreen = { args ->
                    appState.navController.navigate(
                        RepositoriesFlowDestinations.DetailRoute.withArgs(
                            RepositoriesFlowDestinations.DetailRoute.Args(
                                owner = args.owner.login.encode(),
                                ownerUrl = args.owner.htmlUrl.encode(),
                                repo = args.name.encode(),
                                repoUrl = args.htmlUrl.encode(),
                                ownerAvatar = args.owner.avatarUrl?.encode(),
                            )
                        )
                    )
                },
            )
        }

        composable(
            route = RepositoriesFlowDestinations.DetailRoute.route,
            arguments = listOf(
                navArgument(RepositoriesFlowDestinations.DetailRoute.ARG_KEY_DETAILS) {
                    type = RepositoriesFlowDestinations.DetailRoute.DetailsPageParamType()
                    nullable = false
                }
            ),
        ) {
            DetailsScreen()
        }
    }
}