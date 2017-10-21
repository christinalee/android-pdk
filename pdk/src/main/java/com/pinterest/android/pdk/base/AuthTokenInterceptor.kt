package com.pinterest.android.pdk.base

import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor(val accessToken: String): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authdUrl = originalRequest.url()
                .newBuilder()
                .addQueryParameter(AUTHORIZATION_QUERY_NAME, accessToken)
                .build()
        val authdRequest = originalRequest.newBuilder().url(authdUrl).build()

        return chain.proceed(authdRequest)
    }

    companion object {
        private const val AUTHORIZATION_QUERY_NAME = "access_token"
    }
}
