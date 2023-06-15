package com.dgomesdev.domain.usecase

import com.dgomesdev.domain.model.MatchDomain
import com.dgomesdev.domain.repositories.MatchesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMatchesUseCase @Inject constructor(
    private val repository: MatchesRepository
) {
    suspend operator fun invoke(): Flow<List<MatchDomain>> {
        return repository.getRemoteMatchesFromRepository()
    }
}