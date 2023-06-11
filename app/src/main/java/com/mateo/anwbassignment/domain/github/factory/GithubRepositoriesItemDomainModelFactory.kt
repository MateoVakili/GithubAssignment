package com.mateo.anwbassignment.domain.github.factory

import com.mateo.anwbassignment.domain.github.model.GithubRepositoriesItemDomainModel

object GithubRepositoriesItemDomainModelFactory {
    fun createInstance() = GithubRepositoriesItemDomainModel(
        "avatarUrl",
        "login",
        "htmlUrl",
        "description",
        "Kotlin",
        OwnerDomainModelFactory.createInstance()
    )
}