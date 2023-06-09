package com.mateo.anwbassignment.presentation.github.detail

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.mateo.anwbassignment.presentation.util.view.parcelable
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailsPageArg(
    val owner: String,
    val ownerUrl: String,
    val ownerAvatar: String?,
    val repo: String,
    val repoUrl: String
) : Parcelable

class DetailsPageParamType : NavType<DetailsPageArg?>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): DetailsPageArg? {
        return bundle.parcelable(key)
    }
    override fun parseValue(value: String): DetailsPageArg? {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return moshi.adapter(DetailsPageArg::class.java).fromJson(value)
    }
    override fun put(bundle: Bundle, key: String, value: DetailsPageArg?) {
        bundle.putParcelable(key, value)
    }
}