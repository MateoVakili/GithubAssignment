package com.mateo.anwbassignment.data.github.mapper

import com.mateo.anwbassignment.data.api.model.GithubRepositoriesItem
import com.mateo.anwbassignment.domain.github.model.GithubRepositoriesItemDomainModel

internal fun GithubRepositoriesItem.toDomainModels(): GithubRepositoriesItemDomainModel {
    return GithubRepositoriesItemDomainModel(
        id = this.id,
        name = this.name,
        description = this.description,
        language = this.language,
        owner = this.owner.toDomainModels(),
        htmlUrl = this.html_url
    )
}
