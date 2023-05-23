package com.dgomesdev.data.di

import com.dgomesdev.data.repository.MatchesRepositoryImpl
import com.dgomesdev.domain.repositories.MatchesRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun providesMatchesRepository(impl: MatchesRepositoryImpl): MatchesRepository
}