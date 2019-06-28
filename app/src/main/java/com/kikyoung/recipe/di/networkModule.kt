package com.kikyoung.recipe.di

import android.content.Context
import com.google.gson.Gson
import com.kikyoung.recipe.BuildConfig
import com.kikyoung.recipe.R
import com.kikyoung.recipe.data.api.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { Gson() }
    single { api(get()) }
    single { retrofit(get(named("baseUrl")), get()) }
    single { okHttpClient(get()) }
    single { httpLoggingInterceptor() }
    single(named("baseUrl")) { baseUrl(androidContext()) }
}

private fun api(retrofit: Retrofit): Api =
    retrofit.create(Api::class.java)

private fun retrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

private fun okHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = if (BuildConfig.DEBUG)
        HttpLoggingInterceptor.Level.BODY
    else
        HttpLoggingInterceptor.Level.NONE
    return interceptor
}

private fun baseUrl(context: Context): String {
    return context.getString(R.string.base_url)
}