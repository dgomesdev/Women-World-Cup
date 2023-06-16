package com.dgomesdev.features

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dgomesdev.domain.model.MatchDomain
import com.dgomesdev.ui.composables.FilterChoice
import com.dgomesdev.ui.composables.MatchesList

typealias NotificationOnClick = (match: MatchDomain) -> Unit
typealias RemoveFromFavorite = (match: MatchDomain) -> Unit
typealias ChooseFilter = (filterType: String, filter: String) -> Unit
typealias RefreshFavoritesScreen = () -> Unit

@Composable
fun MainScreen(
    matches: List<MatchDomain>,
    onNotificationClick: NotificationOnClick,
    onFilterChosen: ChooseFilter,
    onRefreshFavoritesScreen: RefreshFavoritesScreen,
    filterButtonText: String
) {
    var buttonText by remember {
        mutableStateOf(filterButtonText)
    }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.size(16.dp))
            FilterChoice(filterButtonText, onTextChange = { buttonText = it}, onFilterChosen = onFilterChosen)
            Spacer(modifier = Modifier.size(16.dp))
            MatchesList(
                Modifier.padding(16.dp),
                matches = matches,
                onNotificationClick = onNotificationClick,
                onRefreshFavoritesScreen = onRefreshFavoritesScreen
            )
        }

}

@Composable
fun FavoritesScreen(
    favoriteMatches: List<MatchDomain>,
    removeFavorite: RemoveFromFavorite,
    onFilterChosen: ChooseFilter,
    onRefreshFavoritesScreen: RefreshFavoritesScreen,
    filterButtonText: String
) {
    var buttonText by rememberSaveable {
        mutableStateOf(filterButtonText)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(16.dp))
        FilterChoice(filterButtonText, onTextChange = { buttonText = it}, onFilterChosen = onFilterChosen)
        Spacer(modifier = Modifier.size(16.dp))
        MatchesList(
            Modifier.padding(16.dp),
            matches = favoriteMatches,
            onNotificationClick = removeFavorite,
            onRefreshFavoritesScreen = onRefreshFavoritesScreen
        )
    }

}