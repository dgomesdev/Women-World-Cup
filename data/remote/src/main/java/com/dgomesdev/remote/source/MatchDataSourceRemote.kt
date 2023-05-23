package com.dgomesdev.remote.source

import com.dgomesdev.data.source.MatchesDataSource
import com.dgomesdev.domain.model.MatchDomain
import com.dgomesdev.remote.extensions.getOrThrowDomainError
import com.dgomesdev.remote.mapper.toDomain
import com.dgomesdev.remote.services.MatchesServices
import javax.inject.Inject

class MatchDataSourceRemote @Inject constructor(
    private val service: MatchesServices
) : MatchesDataSource.Remote {

    override suspend fun getMatches(): List<MatchDomain> {
        return runCatching {
            service.getMatches()
        }.getOrThrowDomainError().toDomain()
    }
}