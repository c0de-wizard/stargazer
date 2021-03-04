package com.thomaskioko.stargazer.repository.injection

import com.squareup.moshi.Moshi
import com.thomaskioko.stargazer.core.executor.CoroutineExecutionThread
import com.thomaskioko.stargazer.repository.api.GithubRepository
import com.thomaskioko.stargazer.repository.api.service.GitHubService
import com.thomaskioko.stargazer.repository.db.GithubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
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
    @Singleton
    fun provideGitHubService(retrofit: Retrofit): GitHubService = retrofit.create()

    @Provides
    fun provideOkHttpConfigurator() = object : OkHttpConfigurator {
        override fun configure(clientBuilder: OkHttpClient.Builder) {
            clientBuilder.callTimeout(30, TimeUnit.SECONDS)
        }
    }

    @Provides
    fun provideGithubRepository(
        service: GitHubService,
        database: GithubDatabase,
        coroutineExecutionThread: CoroutineExecutionThread
    ) = GithubRepository(service, database, coroutineExecutionThread)
}

interface OkHttpConfigurator {

    fun configure(clientBuilder: OkHttpClient.Builder)
}
