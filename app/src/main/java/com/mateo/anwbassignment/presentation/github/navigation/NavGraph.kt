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
        startDestination = RepositoriesFlowDestinations.RepositoriesRoute.route,
    ) {
        composable(route = RepositoriesFlowDestinations.RepositoriesRoute.route) {
            RepositoriesScreen(
                navigateToDetailScreen = { args ->
                    appState.navController.navigate(
                        // Note the repo details is very simple and we are going to be using 5 properties
                        // If data is more complex we should prevent passing it through to avoid anti patterns
                        // alternatively we can make new call in the viewModel of the details page (could also add offline data source)
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