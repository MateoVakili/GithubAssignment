package com.mateo.anwbassignment.domain.core.network

import com.mateo.anwbassignment.domain.core.AssignmentExceptions

sealed class LoadingResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : LoadingResult<T>()
    data class Error(val exception: AssignmentExceptions) : LoadingResult<Nothing>()
}