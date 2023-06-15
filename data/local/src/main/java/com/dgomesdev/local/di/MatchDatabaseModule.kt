package com.dgomesdev.local.di

import android.content.Context
import androidx.room.Room
import com.dgomesdev.local.dao.MatchDao
import com.dgomesdev.local.database.MatchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MatchDatabaseModule {

    @Provides
    fun provideMatchDatabase(@ApplicationContext context: Context): MatchDatabase {
        return Room.databaseBuilder(
            context,
            MatchDatabase::class.java,
            "match_database"
        ).build()
    }

    @Provides
    fun providesMatchDao(matchDatabase: MatchDatabase): MatchDao = matchDatabase.matchDao()

}