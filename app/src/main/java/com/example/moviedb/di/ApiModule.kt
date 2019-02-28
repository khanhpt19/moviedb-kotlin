package com.example.moviedb.di

import android.content.Context
import com.example.moviedb.BuildConfig
import com.example.moviedb.data.remote.api.MovieApi
import com.example.moviedb.di.Properties.TIME_OUT
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { createOkHttpCache(get()) }
    single(name = "logging") { createLoggingInterceptor() }
    single(name = "header") { createHeaderInterceptor() }
    single { createOkHttpClient(get(), get(name = "header"), get(name = "logging")) }
    single { createAppRetrofit(get()) }
    single { createAPIService(get()) }
}

object Properties {
    const val TIME_OUT = 10
}

fun createOkHttpCache(context: Context): Cache {
    val size = (10 * 1024 * 1024).toLong()
    return Cache(context.cacheDir, size)
}

fun createLoggingInterceptor(): Interceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
    else HttpLoggingInterceptor.Level.NONE
    return logging
}

fun createHeaderInterceptor(): Interceptor {
    return Interceptor { chain ->
        val request = chain.request()
        val newUrl = request.url().newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()
        val newRequest = request.newBuilder()
            .url(newUrl)
            .header("Content-Type", "application/json")
            .method(request.method(), request.body())
            .build()
        chain.proceed(newRequest)
    }
}

fun createOkHttpClient(
    cache: Cache,
    header: Interceptor,
    logging: Interceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .cache(cache)
        .connectTimeout(TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
        .readTimeout(TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
        .addInterceptor(header)
        .addInterceptor(logging)
        .build()
}

fun createAppRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.END_POINT_URL)
        .client(okHttpClient)
        .build()
}

fun createAPIService(retrofit: Retrofit): MovieApi {
    return retrofit.create(MovieApi::class.java)
}
