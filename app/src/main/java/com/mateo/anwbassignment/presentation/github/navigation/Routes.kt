package com.mateo.anwbassignment.presentation.github.navigation

import com.mateo.anwbassignment.domain.core.network.MoshiHelper
import com.mateo.anwbassignment.domain.github.model.GithubRepositoryDetails
import com.mateo.anwbassignment.presentation.util.navigation.Destinations

// routes for repositories navGraph
sealed interface RepositoriesFlowDestinations : Destinations {
    object RepositoriesRoute : RepositoriesFlowDestinations {
        override val route: String = "repositories-screen"
    }

    object DetailRoute : RepositoriesFlowDestinations {
        const val ARG_KEY_DETAILS = "details"
        private const val routeWithoutArgs = "repositories-details-screen"
        override val route = "repositories-details-screen/{$ARG_KEY_DETAILS}"

        fun withArgs(data: GithubRepositoryDetails): String {
            val arg = MoshiHelper().adapter(GithubRepositoryDetails::class.java).toJson(data.encode())
            return "$routeWithoutArgs/$arg"
        }
    }
}

