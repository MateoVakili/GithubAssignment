package com.mateo.anwbassignment.presentation.github.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.mateo.anwbassignment.presentation.util.navigation.Destinations
import com.mateo.anwbassignment.presentation.util.view.parcelable
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.parcelize.Parcelize

// routes for repositories navGraph
sealed interface RepositoriesFlowDestinations : Destinations {
    object RepositoriesRoute : RepositoriesFlowDestinations {
        override val route: String = "repositories-screen"
    }

    // Note for the moment the repo details is very simple and we are going to be using 5 properties
    // If data is more complex we should prevent passing it through to avoid anti patterns
    // alternatively we can make new call in the viewModel of the details page (could also add offline data source)
    object DetailRoute: RepositoriesFlowDestinations {
        const val ARG_KEY_DETAILS = "details"
        private const val detailsPageRoute = "repositories-details-screen"
        private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        override val route = "repositories-details-screen/{$ARG_KEY_DETAILS}"

        @Parcelize
        data class Args(
            val owner: String,
            val ownerUrl: String,
            val ownerAvatar: String?,
            val repo: String,
            val repoUrl: String
        ) : Parcelable

        fun withArgs(args: Args): String {
            val arg = moshi.adapter(Args::class.java).toJson(args)
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
    }
}



