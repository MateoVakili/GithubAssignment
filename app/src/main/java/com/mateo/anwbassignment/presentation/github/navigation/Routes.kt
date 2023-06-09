package com.mateo.anwbassignment.presentation.github.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.mateo.anwbassignment.domain.github.model.GithubRepositoriesItemDomainModel
import com.mateo.anwbassignment.presentation.util.navigation.Destinations
import com.mateo.anwbassignment.presentation.util.view.encode
import com.mateo.anwbassignment.presentation.util.view.parcelable
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.parcelize.Parcelize

// routes for repositories navGraph
sealed interface RepositoriesFlowDestinations : Destinations {
    object RepositoriesRoute : RepositoriesFlowDestinations {
        override val route: String = "repositories-screen"
    }

    // Note ianhanniballake explanation -> https://stackoverflow.com/questions/69059149/how-pass-parcelable-argument-with-new-version-of-compose-navigation
    // for the moment the repo details is very simple and we are going to be using a very few properties
    // all of these properties are Ids on their own such as owner or repo names which is used to get more info if needed
    //
    // In this case the detail page is mostly showing the names and urls only which is why Im just passing the data
    // If data is more complex we might then decide not passing them through to avoid anti patterns
    // alternatively we can make a new call in the viewModel of the details page as single source of truth
    object DetailRoute : RepositoriesFlowDestinations {
        const val ARG_KEY_DETAILS = "details"
        private const val detailsPageRoute = "repositories-details-screen"
        override val route = "repositories-details-screen/{$ARG_KEY_DETAILS}"
        private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        fun withArgs(data: GithubRepositoriesItemDomainModel): String {
            val arg = moshi.adapter(Args::class.java).toJson(
                Args(
                    owner = data.owner.login.encode(),
                    ownerUrl = data.owner.htmlUrl.encode(),
                    repo = data.name.encode(),
                    repoUrl = data.htmlUrl.encode(),
                    ownerAvatar = data.owner.avatarUrl?.encode(),
                )
            )
            return "$detailsPageRoute/${arg}"
        }

        class DetailsPageParamType : NavType<Args?>(isNullableAllowed = false) {
            override fun get(bundle: Bundle, key: String): Args? {
                return bundle.parcelable(key)
            }

            override fun parseValue(value: String): Args? {
                return moshi.adapter(Args::class.java).fromJson(value)
            }

            override fun put(bundle: Bundle, key: String, value: Args?) {
                bundle.putParcelable(key, value)
            }
        }
        @Parcelize
        data class Args(
            val owner: String,
            val ownerUrl: String,
            val ownerAvatar: String?,
            val repo: String,
            val repoUrl: String
        ) : Parcelable
    }
}

