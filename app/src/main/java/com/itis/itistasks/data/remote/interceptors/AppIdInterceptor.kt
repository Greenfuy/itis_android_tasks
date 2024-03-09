package com.itis.itistasks.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AppIdInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url.newBuilder()
            //.addQueryParameter(Keys.APP_ID_KEY, BuildConfig.openWeatherApiKey)
            .build()

        val requestBuilder = chain.request().newBuilder().url(newUrl)

        return chain.proceed(requestBuilder.build())
    }
}