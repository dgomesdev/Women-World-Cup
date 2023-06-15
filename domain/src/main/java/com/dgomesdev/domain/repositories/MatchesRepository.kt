package com.dgomesdev.domain.repositories

import com.dgomesdev.domain.model.MatchDomain
import kotlinx.coroutines.flow.Flow

interface MatchesRepository {
    suspend fun getRemoteMatchesFromRepository(): Flow<List<MatchDomain>>

    suspend fun getLocalMatchesFromRepository(): Flow<List<MatchDomain>>
    suspend fun filterMatches(filterType: String, filter: String): Flow<List<MatchDomain>>
    suspend fun enableNotificationFor(id: String, match: MatchDomain)
    suspend fun disableNotificationFor(id: String, match: MatchDomain)
}