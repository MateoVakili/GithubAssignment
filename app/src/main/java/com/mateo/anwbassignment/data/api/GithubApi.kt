package com.mateo.anwbassignment.data.api

import com.mateo.anwbassignment.data.api.model.GithubEvent
import com.mateo.anwbassignment.data.api.model.GithubSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories")
    suspend fun getRepository(
        @Query(value = "page", encoded = true) page: Int? = null,
        @Query(value = "per_page", encoded = true) perPage: Int? = null,
        @Query(value = "sort", encoded = true) sort: String = "stars",
        @Query(value = "q", encoded = true) q: String
    ): GithubSearchResponse

    @GET("/repos/{user}/{repo}/events")
    suspend fun getLatestEvent(
        @Path(value = "user", encoded = true) user: String,
        @Path(value = "repo", encoded = true) repo: String,
        @Query(value = "page", encoded = true) page: Int = 1,
        @Query(value = "per_page", encoded = true) perPage: Int = 1,
    ): List<GithubEvent>
}