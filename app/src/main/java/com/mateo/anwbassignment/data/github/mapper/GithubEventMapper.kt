package com.mateo.anwbassignment.data.github.mapper

import com.mateo.anwbassignment.data.api.model.GithubEvent
import com.mateo.anwbassignment.domain.github.model.GithubEventDomainModel

internal fun GithubEvent.toDomainModels(): GithubEventDomainModel {
    return GithubEventDomainModel(
        createdAt = this.created_at,
        actor = actor.toDomainModels(),
        type = this.type
    )
}
