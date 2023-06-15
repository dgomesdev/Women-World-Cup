package com.dgomesdev.local.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.dgomesdev.data.source.MatchesDataSource
import com.dgomesdev.domain.model.MatchDomain
import com.dgomesdev.local.dao.MatchDao
import com.dgomesdev.local.mapper.toDomain
import com.dgomesdev.local.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MatchDataSourceLocal @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val matchDao: MatchDao
) : MatchesDataSource.Local {

    private val key = stringSetPreferencesKey("notification_ids")

    override fun filterMatchesDataSource(filterType: String, filter: String): List<MatchDomain> {
        TODO("Not yet implemented")
    }

    override suspend fun getMatchesFromLocalDataSource(): List<MatchDomain> {
        return matchDao.getFavoriteMatchesFromDB().toDomain()
    }

    override fun getActiveNotificationIds(): Flow<Set<String>> =
        dataStore.data
            .map { preferences ->
                preferences[key] ?: setOf()
            }

    override suspend fun enableNotificationFor(id: String, matchDomain: MatchDomain) {
        dataStore.edit { settings ->
            val currentIds = settings[key] ?: setOf()
            settings[key] = currentIds + id
        }
        matchDao.saveMatch(matchDomain.toEntity())
    }

    override suspend fun disableNotificationFor(id: String, matchDomain: MatchDomain) {
        dataStore.edit { settings ->
            val currentIds = settings[key] ?: return@edit
            settings[key] = currentIds - id
        }
        matchDao.deleteMatch(matchDomain.toEntity())
    }
}