package com.mateo.anwbassignment.domain.github.model

import java.time.OffsetDateTime

data class GithubEventDomainModel(
    val actor: ActorDomainModel,
    val createdAt: OffsetDateTime,
    val type: String
)