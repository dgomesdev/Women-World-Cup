package com.dgomesdev.domain.usecase

import com.dgomesdev.domain.model.MatchDomain
import com.dgomesdev.domain.repositories.MatchesRepository
import javax.inject.Inject

class DisableNotificationUseCase @Inject constructor(
    private val repository: MatchesRepository
) {
    suspend operator fun invoke(id: String, match: MatchDomain){
        return repository.disableNotificationFor(id, match)
    }
}