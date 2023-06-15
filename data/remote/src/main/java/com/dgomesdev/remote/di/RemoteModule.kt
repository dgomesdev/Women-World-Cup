package com.dgomesdev.remote.di

import dagger.Binds
import dagger.Module
import com.dgomesdev.data.source.MatchesDataSource
import com.dgomesdev.remote.source.MatchDataSourceRemote
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RemoteModule {

    @Binds
    fun providesMatchDataSourceRemote(impl: MatchDataSourceRemote): MatchesDataSource.Remote
}