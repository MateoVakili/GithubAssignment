package com.mateo.anwbassignment.data.github.mapper

import com.mateo.anwbassignment.data.api.model.Actor
import com.mateo.anwbassignment.domain.github.model.ActorDomainModel

internal fun Actor.toDomainModels(): ActorDomainModel {
    return ActorDomainModel(
        avatarUrl = this.avatar_url,
        displayLogin = this.display_login,
        url = this.url
    )
}
