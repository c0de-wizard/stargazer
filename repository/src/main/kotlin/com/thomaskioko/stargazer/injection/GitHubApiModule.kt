package com.thomaskioko.stargazer.injection

import com.squareup.moshi.Moshi
import com.thomaskioko.stargazer.api.service.GitHubService
import com.thomaskioko.stargazer.repository.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GitHubApiModule {

    @Provides
    @Singleton
    fun provideOkHttp(configurator: OkHttpConfigurator): Call.Factory =
        OkHttpClient.Builder().apply { configurator.configure(this) }
            .build()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi,
        callFactory: Call.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .callFactory(callFactory)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) BASIC else NONE }

    @Provides
    @Singleton
    fun provideGitHubService(retrofit: Retrofit): GitHubService = retrofit.create()

    @Provides
    fun provideOkHttpConfigurator(httpLoggingInterceptor: HttpLoggingInterceptor) =
        object : OkHttpConfigurator {
            override fun configure(clientBuilder: OkHttpClient.Builder) {
                clientBuilder.callTimeout(30, TimeUnit.SECONDS)
                clientBuilder.connectTimeout(120, TimeUnit.SECONDS)
                clientBuilder.readTimeout(120, TimeUnit.SECONDS)
                clientBuilder.addInterceptor(httpLoggingInterceptor)
            }
        }
}

interface OkHttpConfigurator {

    fun configure(clientBuilder: OkHttpClient.Builder)
}
