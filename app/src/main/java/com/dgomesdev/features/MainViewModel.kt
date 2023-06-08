package com.dgomesdev.features

import androidx.lifecycle.viewModelScope
import com.dgomesdev.core.BaseViewModel
import com.dgomesdev.domain.model.MatchDomain
import com.dgomesdev.domain.usecase.DisableNotificationUseCase
import com.dgomesdev.domain.usecase.EnableNotificationUseCase
import com.dgomesdev.domain.usecase.FilterMatchesUseCase
import com.dgomesdev.domain.usecase.GetMatchesUseCase
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
class MainViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase,
    private val disableNotificationUseCase: DisableNotificationUseCase,
    private val enableNotificationUseCase: EnableNotificationUseCase,
    private val filterMatchesUseCase: FilterMatchesUseCase
) : BaseViewModel<MainUiState, MainUiAction>(MainUiState()) {

    init {
        fetchMatches()
    }

    private fun fetchMatches() = viewModelScope.launch {
        filterMatchesUseCase("", "")
            .flowOn(Dispatchers.Main)
            .catch {
                when(it) {
                    is NotFoundException ->
                        sendAction(MainUiAction.MatchesNotFound(it.message ?: "Error without message"))
                    is UnexpectedException ->
                        sendAction(MainUiAction.Unexpected)
                }
            }.collect { matches ->
                setState {
                    copy(matches = matches)
                }
            }
    }

    fun filterMatches(filterType: String, filter: String) = viewModelScope.launch {
        filterMatchesUseCase(filterType, filter)
            .flowOn(Dispatchers.Main)
            .catch {
                when(it) {
                    is NotFoundException ->
                        sendAction(MainUiAction.MatchesNotFound(it.message ?: "Error without message"))
                    is UnexpectedException ->
                        sendAction(MainUiAction.Unexpected)
                }
            }.collect { matches ->
                setState {
                    copy(matches = matches)
                }
            }
    }

    fun toggleNotification(match: MatchDomain) {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.Main) {
                    val action = if (match.isNotificationEnabled) {
                        disableNotificationUseCase(match.id)
                        MainUiAction.DisableNotification(match)
                    } else {
                        enableNotificationUseCase(match.id)
                        MainUiAction.EnableNotification(match)
                    }

                    sendAction(action)
                }
            }
        }
    }
}

data class MainUiState(
    val matches: List<MatchDomain> = emptyList()
)

sealed interface MainUiAction {
    object Unexpected: MainUiAction
    data class MatchesNotFound(val message: String) : MainUiAction
    data class EnableNotification(val match: MatchDomain) : MainUiAction
    data class DisableNotification(val match: MatchDomain) : MainUiAction
}