package com.mateo.anwbassignment.presentation.github.navigation

import com.mateo.anwbassignment.domain.github.model.GithubRepositoriesItemDomainModel
import com.mateo.anwbassignment.domain.github.model.GithubRepositoryDetails
import com.mateo.anwbassignment.domain.github.model.encode
import com.mateo.anwbassignment.presentation.util.navigation.Destinations
import com.mateo.anwbassignment.presentation.util.view.encode
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

// routes for repositories navGraph
sealed interface RepositoriesFlowDestinations : Destinations {
    object RepositoriesRoute : RepositoriesFlowDestinations {
        override val route: String = "repositories-screen"
    }

    object DetailRoute : RepositoriesFlowDestinations {
        const val ARG_KEY_DETAILS = "details"
        private const val routeWithoutArgs = "repositories-details-screen"
        override val route = "repositories-details-screen/{$ARG_KEY_DETAILS}"
        private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        fun withArgs(data: GithubRepositoriesItemDomainModel): String {
            val arg = moshi.adapter(GithubRepositoryDetails::class.java).toJson(
                GithubRepositoryDetails(
                    owner = data.owner.login,
                    ownerUrl = data.owner.htmlUrl,
                    repo = data.name,
                    repoUrl = data.htmlUrl,
                ).encode()
            )
            return "$routeWithoutArgs/$arg"
        }
    }
}

