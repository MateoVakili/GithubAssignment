package com.mateo.anwbassignment.presentation.util.error

import android.content.Context
import com.mateo.anwbassignment.R
import com.mateo.anwbassignment.domain.core.EmptyResponse
import com.mateo.anwbassignment.domain.core.NetworkException
import com.mateo.anwbassignment.domain.core.ServerException

class UIExceptionMapper {
    fun getTitle(context: Context, exception: Throwable): String {
        return when (exception) {
            is NetworkException -> context.resources.getString(R.string.core_network_error_title)
            is ServerException -> context.resources.getString(R.string.core_error_title)
            is EmptyResponse -> context.resources.getString(R.string.core_empty_title)
            else -> context.resources.getString(R.string.core_error_title)
        }
    }

    fun getMessage(context: Context, exception: Throwable): String {
        return when (exception) {
            is NetworkException -> context.resources.getString(R.string.core_network_error_message)
            is ServerException -> context.resources.getString(R.string.core_error_message)
            is EmptyResponse -> context.resources.getString(R.string.core_empty_message)
            else -> context.resources.getString(R.string.core_error_message)
        }
    }

    fun isRetryEnabled(exception: Throwable): Boolean {
        return when (exception) {
            is NetworkException -> true
            is ServerException -> true
            is EmptyResponse -> true
            else -> true
        }
    }
}
