package com.mateo.anwbassignment.data.api.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class GithubMetaDataInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request =
            chain
                .request()
                .newBuilder()
                // would normally handle this in a different interceptor along side an authenticator.
                // using the above and a session manager we could also have a login flow.
                // NOTE: to prevent errors from github on rate limit please put a token here and uncomment
//                .addHeader(
//                    "Authorization",
//                    "Bearer $GITHUB_TOKEN"
//                )
                .addHeader("X-GitHub-Api-Version", "2022-11-28")
                .build()
        return chain.proceed(request)
    }

    companion object {
        // Could also maintain this in gradle and go further to hide using local properties
        // or use github secrets and then access via fastlane for example
        // I thought this would serve the purpose in this case I have removed mine please replace with yours !
        private const val GITHUB_TOKEN = ""
    }
}
