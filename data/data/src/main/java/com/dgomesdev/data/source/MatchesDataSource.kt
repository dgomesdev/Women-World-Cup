package com.dgomesdev.data.source

import com.dgomesdev.domain.model.MatchDomain
import kotlinx.coroutines.flow.Flow

sealed interface MatchesDataSource {
    interface Remote : MatchesDataSource {
        suspend fun getMatches(): List<MatchDomain>
    }

    interface Local : MatchesDataSource {
        fun getActiveNotificationIds(): Flow<Set<String>>
        suspend fun enableNotificationFor(id: String)
        suspend fun disableNotificationFor(id: String)
    }
}