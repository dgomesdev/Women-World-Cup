package com.dgomesdev.features

import androidx.lifecycle.viewModelScope
import com.dgomesdev.core.BaseViewModel
import com.dgomesdev.domain.model.MatchDomain
import com.dgomesdev.domain.usecase.DisableNotificationUseCase
import com.dgomesdev.domain.usecase.GetFavoriteMatchesUseCase
import com.dgomesdev.remote.NotFoundException
import com.dgomesdev.remote.UnexpectedException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteMatchesUseCase: GetFavoriteMatchesUseCase,
    private val disableNotificationUseCase: DisableNotificationUseCase
) : BaseViewModel<FavoritesUiState, FavoritesUiAction>(FavoritesUiState()) {

    private fun getFavoriteMatches() = viewModelScope.launch {
        getFavoriteMatchesUseCase()
            .flowOn(Dispatchers.Main)
            .catch {
                when(it) {
                    is NotFoundException ->
                        sendAction(FavoritesUiAction.MatchesNotFound(it.message ?: "Error without message"))
                    is UnexpectedException ->
                        sendAction(FavoritesUiAction.Unexpected)
                }
            }.collect { favoriteMatches ->
                setState {
                    copy(favoriteMatches = favoriteMatches)
                }
            }
    }

    fun refreshFavoritesScreen() = viewModelScope.launch { getFavoriteMatches() }

    fun removeFromFavorites(match: MatchDomain) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.Main) {
                    disableNotificationUseCase(match.id, match)
                    FavoritesUiAction.DisableNotification(match)
                }
            }
        }
    }

}

data class FavoritesUiState(
    val favoriteMatches: List<MatchDomain> = emptyList()
)

sealed interface FavoritesUiAction {
    object Unexpected: FavoritesUiAction
    data class MatchesNotFound(val message: String) : FavoritesUiAction

    data class DisableNotification(val match: MatchDomain) : FavoritesUiAction

}