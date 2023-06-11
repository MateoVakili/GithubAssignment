package com.mateo.anwbassignment.domain.github.factory

import com.mateo.anwbassignment.domain.github.model.OwnerDomainModel

object OwnerDomainModelFactory {
    fun createInstance() = OwnerDomainModel(
        "avatarUrl",
        "login",
        "htmlUrl"
    )
}