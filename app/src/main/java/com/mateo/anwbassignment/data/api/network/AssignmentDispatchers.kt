package com.mateo.anwbassignment.data.api.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: AssignmentDispatchers)

enum class AssignmentDispatchers {
    Default,
    IO,
}
