package com.dgomesdev.domain.repositories

import com.dgomesdev.domain.model.MatchDomain
import kotlinx.coroutines.flow.Flow

interface MatchesRepository {
    suspend fun getMatches(): Flow<List<MatchDomain>>
    suspend fun filterMatches(filterType: String, filter: String): Flow<List<MatchDomain>>
    suspend fun enableNotificationFor(id: String)
    suspend fun disableNotificationFor(id: String)
}