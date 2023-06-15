package com.dgomesdev.features

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dgomesdev.domain.model.MatchDomain

typealias RemoveFromFavorite = (match: MatchDomain) -> Unit

@Composable
fun FavoritesScreen(
favoriteMatches: List<MatchDomain>,
removeFavorite: RemoveFromFavorite,
onFilterChosen: ChooseFilter,
onRefreshFavoritesScreen: RefreshFavoritesScreen
) {
    var filterButtonText by rememberSaveable {
        mutableStateOf("All matches")
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(16.dp))
        FilterChoice(filterButtonText, onTextChange = { filterButtonText = it}, onFilterChosen = onFilterChosen)
        Spacer(modifier = Modifier.size(16.dp))
        MatchesList(
            Modifier.padding(16.dp),
            matches = favoriteMatches,
            onNotificationClick = removeFavorite,
            onRefreshFavoritesScreen = onRefreshFavoritesScreen
        )
    }

}