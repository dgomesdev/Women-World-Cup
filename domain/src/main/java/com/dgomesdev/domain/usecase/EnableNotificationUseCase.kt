package com.dgomesdev.domain.usecase

import com.dgomesdev.domain.model.MatchDomain
import com.dgomesdev.domain.repositories.MatchesRepository
import javax.inject.Inject

class EnableNotificationUseCase @Inject constructor(
    private val repository: MatchesRepository
) {
    suspend operator fun invoke(id: String, match: MatchDomain) {
        repository.enableNotificationFor(id, match)
    }
}