package com.dgomesdev.features

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dgomesdev.domain.model.MatchDomain

@Composable
fun MatchesNavHost (
    navController: NavHostController,
    modifier: Modifier = Modifier,
    matches: List<MatchDomain>,
    favoriteMatches: List<MatchDomain>,
    onNotificationClick: NotificationOnClick,
    onFilterChosen: ChooseFilter,
    onRefreshFavoritesScreen: RefreshFavoritesScreen,
    removeFavorite: RemoveFromFavorite,
    filterButtonText: String
) {
    NavHost(
        navController = navController,
        startDestination = "mainScreen",
        modifier = modifier
    ) {
        composable(route = "MainScreen") {
            MainScreen(matches = matches, onNotificationClick = onNotificationClick, onFilterChosen = onFilterChosen, onRefreshFavoritesScreen = onRefreshFavoritesScreen, filterButtonText = filterButtonText)
        }
        composable(route = "FavoritesScreen") {
            FavoritesScreen(favoriteMatches = favoriteMatches, removeFavorite = removeFavorite, onFilterChosen = onFilterChosen, onRefreshFavoritesScreen = onRefreshFavoritesScreen)
        }
    }
}