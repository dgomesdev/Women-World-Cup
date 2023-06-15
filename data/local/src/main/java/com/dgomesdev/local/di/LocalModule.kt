package com.dgomesdev.local.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dgomesdev.data.source.MatchesDataSource
import com.dgomesdev.local.source.MatchDataSourceLocal
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

private const val PREFERENCES_NAME = "notifications_prefs"

@Module
@InstallIn(SingletonComponent::class)
interface LocalModule {
    @Binds
    fun providesMatchDataSourceLocal(impl: MatchDataSourceLocal): MatchesDataSource.Local

    companion object {
        private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

        @Provides
        fun providesDataStore(context: Context): DataStore<Preferences> =
            context.dataStore
    }
}