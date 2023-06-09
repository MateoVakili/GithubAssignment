package com.mateo.anwbassignment.data.api.model

data class GithubRepositoriesItem(
    val id: String,
    val name: String,
    val owner: Owner,
    val html_url: String,
    val description: String? = null,
    val language: String? = null
)