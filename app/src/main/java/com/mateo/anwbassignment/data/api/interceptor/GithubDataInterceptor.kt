package com.mateo.anwbassignment.data.api.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class GithubDataInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request =
            chain
                .request()
                .newBuilder()
                // NOTE: to have a higher rate limit please put a token here and uncomment
                //.addHeader(
                //    "Authorization",
                //    "Bearer $GITHUB_TOKEN"
                //)
                .addHeader("X-GitHub-Api-Version", "2022-11-28")
                .build()
        return chain.proceed(request)
    }

    companion object {
        // I thought this would serve the purpose easier so you can replace token with yours !
        // normally there would be a login flow for this or in some other cases we
        // could maintain this in gradle and go further to hide using local properties
        private const val GITHUB_TOKEN = ""
    }
}
