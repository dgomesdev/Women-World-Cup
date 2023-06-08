package com.dgomesdev.domain.usecase

import com.dgomesdev.domain.model.Match
import com.dgomesdev.domain.repositories.MatchesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilterMatchesUseCase @Inject constructor(
    private val repository: MatchesRepository
) {
    suspend operator fun invoke(filterType: String, filter: String): Flow<List<Match>> {
        return repository.filterMatches(filterType, filter)
    }
}