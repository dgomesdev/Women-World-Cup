package com.dgomesdev.remote.di

import com.dgomesdev.remote.services.MatchesServices
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
class ServiceModule {

    @Provides
    fun provideAuthService(retrofit: Retrofit) = retrofit.create<MatchesServices>()

}