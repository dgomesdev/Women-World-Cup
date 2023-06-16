package com.dgomesdev.remote.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val url = "https://dgomesdev.github.io/Women-World-Cup/docs/JSONapi/"

    @Provides
    @Singleton
    fun providesOkHttpBuilder(
        interceptor: Interceptor
    ) = OkHttpClient.Builder().addInterceptor(interceptor)

    @Provides
    @Singleton
    fun providesGsonBuilder(): Gson =
        GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(gson: Gson): Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(url)

    @Provides
    @Singleton
    fun provideRetrofit(
        builder: Retrofit.Builder,
        okHttpClient: OkHttpClient.Builder,
    ): Retrofit = builder
        .client(okHttpClient.build())
        .build()

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}