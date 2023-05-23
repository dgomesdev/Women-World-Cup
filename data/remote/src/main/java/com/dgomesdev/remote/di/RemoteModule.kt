package com.dgomesdev.remote.di

import dagger.Binds
import dagger.Module
import com.dgomesdev.data.source.MatchesDataSource
import com.dgomesdev.remote.source.MatchDataSourceRemote

@Module
interface RemoteModule {

    @Binds
    fun providesMatchDataSourceRemote(impl: MatchDataSourceRemote): MatchesDataSource.Remote
}