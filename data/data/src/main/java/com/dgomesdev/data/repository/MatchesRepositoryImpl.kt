package com.dgomesdev.data.repository

import com.dgomesdev.data.source.MatchesDataSource
import com.dgomesdev.domain.model.Match
import com.dgomesdev.domain.model.MatchDomain
import com.dgomesdev.domain.repositories.MatchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MatchesRepositoryImpl @Inject constructor(
    private val localDataSource: MatchesDataSource.Local,
    private val remoteDataSource: MatchesDataSource.Remote,
) : MatchesRepository {

    override suspend fun getRemoteMatchesFromRepository(): Flow<List<MatchDomain>> {
        return flowOf(remoteDataSource.getMatchesFromRemoteDataSource())
            .combine(localDataSource.getActiveNotificationIds()) { matches: List<MatchDomain>, ids: Set<String> ->
                matches.map { match ->
                    match.copy(isNotificationEnabled = ids.contains(match.id))
                }
            }
    }

    override suspend fun getLocalMatchesFromRepository(): Flow<List<MatchDomain>> {
        return flowOf(localDataSource.getMatchesFromLocalDataSource())
            .combine(localDataSource.getActiveNotificationIds()) { matches: List<MatchDomain>, _: Set<String> ->
                matches.map { match ->
                    match.copy(isNotificationEnabled = true)
                }
            }
    }

    override suspend fun filterMatches(
        filterType: String,
        filter: String
    ): Flow<List<Match>> {
        val filteredList = when (filterType) {
            "stage" -> {
                flowOf(remoteDataSource.getMatchesFromRemoteDataSource().filter {
                    it.name == filter
                })
            }

            "stadium" -> {
                flowOf(remoteDataSource.getMatchesFromRemoteDataSource().filter {
                    it.stadium.name == filter
                })
            }

            "team" -> {
                flowOf(remoteDataSource.getMatchesFromRemoteDataSource().filter {
                    it.homeTeam.displayName == filter || it.awayTeam.displayName == filter
                })
            }

            else -> getRemoteMatchesFromRepository()
        }
        return filteredList.combine(localDataSource.getActiveNotificationIds()) { matches: List<MatchDomain>, ids: Set<String> ->
            matches.map { match ->
                match.copy(isNotificationEnabled = ids.contains(match.id))
            }
        }
    }


    override suspend fun enableNotificationFor(id: String, match: MatchDomain) {
        localDataSource.enableNotificationFor(id, match)
    }

    override suspend fun disableNotificationFor(id: String, match: MatchDomain) {
        localDataSource.disableNotificationFor(id, match)
    }
}