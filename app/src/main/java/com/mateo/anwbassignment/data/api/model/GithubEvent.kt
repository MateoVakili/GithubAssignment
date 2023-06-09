package com.mateo.anwbassignment.data.api.model

import java.time.OffsetDateTime

data class GithubEvent(
    val actor: Actor,
    val created_at: OffsetDateTime,
    val type: String
)