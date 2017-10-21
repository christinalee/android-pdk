package com.pinterest.android.pdk.base

import com.pinterest.android.pdk.PDKClient
import com.pinterest.android.pdk.api.AuthApi
import com.pinterest.android.pdk.api.BoardApi
import com.pinterest.android.pdk.api.UserApi
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    const val PROD_BASE_API_URL = "https://api.pinterest.com/"
    const val NORMAL_REQUEST_TIMEOUT_IN_MS: Long = 2000

    val boardApi: BoardApi by lazy { authed().create(BoardApi::class.java) }
    val userApi: UserApi by lazy { authed().create(UserApi::class.java) }
    private val authApi: AuthApi by lazy { unauthed().create(AuthApi::class.java) }

    private val authHeaderInterceptor by lazy {
        AuthTokenInterceptor(PDKClient.instance.accessToken!!) //TODO fix this after auth port
    }

    //Only create one OkHttp client and base Retrofit instance
    val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
    val retrofit: Retrofit

    init {
        val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(Unwrap.EnvelopeJsonAdapter.FACTORY)
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl(PROD_BASE_API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
    }

    private fun authed(timeoutMillis: Long = NORMAL_REQUEST_TIMEOUT_IN_MS): Retrofit {
        val authedOkHttpClient =
                okHttpClient.newBuilder()
                        .readTimeout(timeoutMillis, TimeUnit.MILLISECONDS)
                        .addInterceptor(authHeaderInterceptor)
                        .build()

        //todo: add error handling to lift server errors
        return retrofit.newBuilder()
                .client(authedOkHttpClient)
                .build()
    }


    private fun unauthed(timeoutMillis: Long = NORMAL_REQUEST_TIMEOUT_IN_MS): Retrofit {
        val unauthedOkHttpClient =
                okHttpClient.newBuilder()
                        .readTimeout(timeoutMillis, TimeUnit.MILLISECONDS)
                        .build()

        //todo: add error handling to lift server errors
        return retrofit.newBuilder()
                .client(unauthedOkHttpClient)
                .build()
    }
}
