package com.dgomesdev.domain.repositories

import com.dgomesdev.domain.model.Match
import kotlinx.coroutines.flow.Flow

interface MatchesRepository {
    suspend fun getMatches(): Flow<List<Match>>
    suspend fun enableNotificationFor(id: String)
    suspend fun disableNotificationFor(id: String)
}