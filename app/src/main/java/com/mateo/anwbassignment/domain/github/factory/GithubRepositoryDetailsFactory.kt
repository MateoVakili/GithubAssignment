package com.mateo.anwbassignment.domain.github.factory

import com.mateo.anwbassignment.domain.github.model.GithubRepositoryDetails

object GithubRepositoryDetailsFactory {
    fun createInstance() = GithubRepositoryDetails(
        "owner",
        "ownerUrl",
        "repo",
        "repoUrl"
    )
}