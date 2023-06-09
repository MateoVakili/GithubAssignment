package com.mateo.anwbassignment.domain.github.model

data class GithubRepositoriesItemDomainModel(
    val id: String,
    val name: String,
    val htmlUrl: String,
    val description: String? = null,
    val language: String? = null,
    val owner: OwnerDomainModel
)