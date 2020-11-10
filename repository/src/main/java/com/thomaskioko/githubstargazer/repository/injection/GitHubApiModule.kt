package com.thomaskioko.githubstargazer.repository.injection

import com.squareup.moshi.Moshi
import com.thomaskioko.githubstargazer.repository.api.service.GitHubService
import dagger.Module
import dagger.Provides
import okhttp3.Call
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object GitHubApiModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideOkHttp(configurator: OkHttpConfigurator): Call.Factory =
        OkHttpClient.Builder().apply { configurator.configure(this) }
            .build()

    @Provides
    @JvmStatic
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @JvmStatic
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
    @JvmStatic
    @Singleton
    fun provideGitHubApi(retrofit: Retrofit): GitHubService = retrofit.create()

    @Provides
    fun provideOkHttpConfigurator() = object : OkHttpConfigurator {
        override fun configure(clientBuilder: OkHttpClient.Builder) {
            clientBuilder.callTimeout(30, TimeUnit.SECONDS)
        }
    }
}

interface OkHttpConfigurator {

    fun configure(clientBuilder: OkHttpClient.Builder)
}
