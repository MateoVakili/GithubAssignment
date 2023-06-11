package com.mateo.anwbassignment.presentation.github.navigation.types

import android.os.Bundle
import androidx.navigation.NavType
import com.mateo.anwbassignment.domain.github.model.GithubRepositoryDetails
import com.mateo.anwbassignment.presentation.util.view.parcelable
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class DetailsPageParamType : NavType<GithubRepositoryDetails?>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): GithubRepositoryDetails? {
        return bundle.parcelable(key)
    }

    override fun parseValue(value: String): GithubRepositoryDetails? {
        return try {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            moshi.adapter(GithubRepositoryDetails::class.java).fromJson(value)
        } catch (e: JsonDataException) {
            null
        }
    }

    override fun put(bundle: Bundle, key: String, value: GithubRepositoryDetails?) {
        bundle.putParcelable(key, value)
    }
}
