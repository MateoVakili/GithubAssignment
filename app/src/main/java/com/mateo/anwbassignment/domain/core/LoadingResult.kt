package com.mateo.anwbassignment.domain.core

sealed class LoadingResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : LoadingResult<T>()
    data class Error(val exception: AssignmentExceptions) : LoadingResult<Nothing>()
}