package com.mateo.anwbassignment.domain.github.factory

import com.mateo.anwbassignment.domain.github.model.ActorDomainModel
import com.mateo.anwbassignment.domain.github.model.GithubEventDomainModel
import java.time.OffsetDateTime

object GithubEventDomainModelFactory {
    fun createInstance() = GithubEventDomainModel(
        ActorDomainModel(
            "avatarUrl",
            "displayLogin",
            "url"
        ),
        OffsetDateTime.now(),
        "htmlUrl"
    )
}