package com.mateo.anwbassignment.data.github.mapper

import com.mateo.anwbassignment.data.api.model.Owner
import com.mateo.anwbassignment.domain.github.model.OwnerDomainModel

internal fun Owner.toDomainModels(): OwnerDomainModel {
    return OwnerDomainModel(
        avatarUrl = this.avatar_url,
        login = this.login,
        htmlUrl = this.html_url
    )
}
