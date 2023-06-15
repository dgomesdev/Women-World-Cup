package com.dgomesdev.data.source

import com.dgomesdev.domain.model.MatchDomain
import kotlinx.coroutines.flow.Flow

sealed interface MatchesDataSource {
    interface Remote : MatchesDataSource {
        suspend fun getMatchesFromRemoteDataSource(): List<MatchDomain>
    }

    interface Local : MatchesDataSource {
        fun filterMatchesDataSource(filterType: String, filter: String): List<MatchDomain>
        suspend fun getMatchesFromLocalDataSource(): List<MatchDomain>
        fun getActiveNotificationIds(): Flow<Set<String>>
        suspend fun enableNotificationFor(id: String, matchDomain: MatchDomain)
        suspend fun disableNotificationFor(id: String, matchDomain: MatchDomain)
    }
}